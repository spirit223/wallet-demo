package cc.sika.wallet.service;

import cc.sika.wallet.bo.ConsumerBO;
import cc.sika.wallet.bo.OrderBO;
import cc.sika.wallet.bo.UserBalanceBO;
import cc.sika.wallet.exception.InsufficientBalanceException;
import cc.sika.wallet.exception.NoSuchUserException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:55
 */
public interface BalanceService {
    /**
     * 根据用户id获取可用余额
     * @return 如果正常查询返回用户可用余额
     * @throws NoSuchUserException 用户不存在时抛出
     */
    BigDecimal getAvailableAmount(int userId) throws NoSuchUserException;

    /**
     * 消费接口, 根据用户id扣减对应的可用余额
     * @param userBalance 封装用户id, 扣减金额 的业务bean, 详见{@link  UserBalanceBO}
     * @return 返回扣减金额以及余额的信息字符串
     * @throws NoSuchUserException 用户不存在时抛出
     * @throws InsufficientBalanceException 余额不足时抛出
     */
    String consume(UserBalanceBO userBalance) throws NoSuchUserException, InsufficientBalanceException;

    String consume(int userId, ConsumerBO consumerBO) throws NoSuchUserException, InsufficientBalanceException;

    /**
     * 直接指定金额的退款接口, 出于安全考虑, 该接口不应当存在, 考虑到题目指定的退款20元, 故写明
     * @param userBalance 封装用户id, 退款金额 的业务bean, 详见{@link  UserBalanceBO}
     * @return 返回退款状态信息以及余额的信息字符串
     */
    String refundWithNum(UserBalanceBO userBalance);

    /**
     * 根据已存在订单进行退款操作的接口
     * @param userBalance 封装用户id, 问题订单id 的业务bean, 详见{@link  UserBalanceBO}
     * @return 返回退款金额以及余额信息的字符串
     */
    String refundWithOrder(UserBalanceBO userBalance);

    /**
     * 查看账户下的订单明细, 以及金额变动的接口
     * @param userId 用户id
     * @return 放回封装 Order 信息的业务实体Bean
     * @throws NoSuchUserException 用户不存在时抛出
     */
    List<OrderBO> getOrderDetails(int userId) throws NoSuchUserException;
}
