package cc.sika.wallet.service.impl;

import cc.sika.wallet.bo.ConsumerBO;
import cc.sika.wallet.bo.OrderBO;
import cc.sika.wallet.bo.UserBalanceBO;
import cc.sika.wallet.exception.InsufficientBalanceException;
import cc.sika.wallet.exception.NoSuchUserException;
import cc.sika.wallet.exception.ServerException;
import cc.sika.wallet.mapper.BalanceMapper;
import cc.sika.wallet.mapper.OrderMapper;
import cc.sika.wallet.mapper.UserMapper;
import cc.sika.wallet.po.Order;
import cc.sika.wallet.po.User;
import cc.sika.wallet.service.BalanceService;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:55
 */
@Service
public class DefaultBalanceService implements BalanceService {

    private static final long WORKER_ID = 1L;
    private static final long DATACENTER_ID = 1L;

    private static final Snowflake snowflake = IdUtil.getSnowflake(WORKER_ID, DATACENTER_ID);

    @Resource
    private BalanceMapper balanceMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public BigDecimal getAvailableAmount(int userId) throws NoSuchUserException {
        // 确保用户id无误
        if (userMapper.getUserById(userId) == null) {
            throw new NoSuchUserException();
        }
        return balanceMapper.getAvailableAmountByUserId(userId);
    }

    @Override
    @Transactional
    public String consume(UserBalanceBO userBalance) throws NoSuchUserException, InsufficientBalanceException, ServerException {
        int userId = userBalance.getUserId();
        User user = assertUser(userId);

        // 订单信息首先创建, 确保金额表不受影响, 订单流水号又雪花生成
        int orderAddResult = orderMapper.addOrder(new Order(String.valueOf(snowflake.nextId()), userBalance.getUserId(), userBalance.getAmount(), "", false));
        if (orderAddResult != 1) {
            throw new ServerException("服务器内部异常, 订单创建失败!");
        }

        Integer balanceId = user.getBalanceId();
        BigDecimal availableAmount = balanceMapper.getAvailableAmount(balanceId);
        BigDecimal consumeAmount = userBalance.getAmount();
        // 确保余额充足
        return doConsume(balanceId, availableAmount, consumeAmount);
    }

    @Override
    public String consume(int userId, ConsumerBO consumerBO) throws NoSuchUserException, InsufficientBalanceException {
        // 确保用户id无误
        User user = assertUser(userId);
        Order order = new Order();
        // 处理商品信息
        if (consumerBO.getOrderAmount().compareTo(new BigDecimal(0)) < 0) {
            throw new RuntimeException("订单金额有误!");
        }
        else {
            order.setOrderAmount(consumerBO.getOrderAmount());
        }
        if (StringUtils.hasText(consumerBO.getOrderCommodity())) {
            order.setOrderCommodity(consumerBO.getOrderCommodity());
        }
        else {
            order.setOrderCommodity("虚拟商品!");
        }
        order.setOrderSerial(snowflake.nextIdStr());
        order.setOrderUser(userId);
        order.setRefund(consumerBO.getRefund());
        order.setOrderTime(new Date(System.currentTimeMillis()));
        int orderAddResult = orderMapper.addOrder(order);
        if (orderAddResult != 1) {
            throw new ServerException("服务器内部异常, 订单创建失败!");
        }

        Integer balanceId = user.getBalanceId();
        BigDecimal availableAmount = balanceMapper.getAvailableAmount(balanceId);
        BigDecimal consumeAmount = consumerBO.getOrderAmount();
        // 确保余额充足
        return doConsume(balanceId, availableAmount, consumeAmount);
    }




    @Override
    @Transactional
    public String refundWithNum(UserBalanceBO userBalance) {
        User user = assertUser(userBalance.getUserId());
        int orderAddResult = orderMapper.addOrder(new Order(String.valueOf(snowflake.nextId()), user.getUserId(), userBalance.getAmount(), "退款订单", true));
        if (orderAddResult != 1) {
            throw new ServerException("服务器内部异常, 订单创建失败!");
        }
        return doRefund(user, userBalance.getAmount());
    }

    @Override
    @Transactional
    public String refundWithOrder(UserBalanceBO userBalance) {
        User user = assertUser(userBalance.getUserId());
        int orderAddResult = orderMapper.addOrder(new Order(String.valueOf(snowflake.nextId()), user.getUserId(), userBalance.getAmount(), "退款订单", true));
        if (orderAddResult != 1) {
            throw new ServerException("服务器内部异常, 订单创建失败!");
        }
        int orderId = userBalance.getOrderId();
        Order order = orderMapper.getOrderByOrderId(orderId);
        return doRefund(user, order.getOrderAmount());
    }


    private String doRefund(User user, BigDecimal refundAmount) {
        BigDecimal frozenAmount = balanceMapper.getFrozenAmount(user.getBalanceId());
        BigDecimal frozen = frozenAmount.subtract(refundAmount);
        int frozenResult = balanceMapper.frozenAmountByUserId(user.getUserId(), frozen);
        if (frozenResult != 1) {
            throw new ServerException("服务器异常, 退款失败!");
        }
        BigDecimal balance = balanceMapper.getAvailableAmount(user.getBalanceId());
        return "成功退款金额: " + refundAmount + "元 , 余额剩余: " + balance + "元。";
    }

    private String doConsume(Integer balanceId, BigDecimal availableAmount, BigDecimal consumeAmount) {
        if (availableAmount.compareTo(consumeAmount) < 0) {
            throw new InsufficientBalanceException();
        }
        int frozenMount = balanceMapper.frozenAmount(balanceId, consumeAmount);
        if (frozenMount != 1) {
            throw new ServerException("服务器内部异常, 支付失败!");
        }
        return "成功支付金额: " + consumeAmount.toString() + "元 , 余额剩余: " + availableAmount.subtract(consumeAmount) + "元。";
    }

    @Override
    public List<OrderBO> getOrderDetails(int userId) throws NoSuchUserException {
        assertUser(userId);
        List<Order> allOrders = orderMapper.getAllOrders(userId);
        BigDecimal availableAmount = balanceMapper.getAvailableAmountByUserId(userId);
        List<BigDecimal> amountList = new ArrayList<>();
        List<OrderBO> orderBOList = new ArrayList<>();
        amountList.add(availableAmount);
        for (Order order : allOrders) {
            if (order.getOrderAmount() == null) {
                order.setOrderAmount(new BigDecimal(0));
            }
            amountList.add(amountList.get(amountList.size() - 1).subtract(order.getOrderAmount()));
        }
        for (int i = 0; i < allOrders.size(); i++) {
            orderBOList.add(new OrderBO(allOrders.get(i), amountList.get(i)));
        }
        return orderBOList;
    }

    /**
     * 确保用户id无误
     * @param userId 断言的用户id
     * @return 如果用户id无误则放回用户信息
     */
    private User assertUser(int userId) throws NoSuchUserException{
        User user = userMapper.getUserById(userId);
        // 确保用户id无误
        if (user == null) {
            throw new NoSuchUserException();
        }
        return user;
    }

}
