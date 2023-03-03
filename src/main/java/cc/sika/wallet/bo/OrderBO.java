package cc.sika.wallet.bo;

import cc.sika.wallet.po.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 处理查看订单详情以及用于余额的业务层 Bean
 * @author 吴畅
 * @创建时间 2023/3/2 - 20:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBO {
    private Integer orderId;
    /**
     * 订单流水号
     */
    private String orderSerial;
    /**
     * 订单商品
     */
    private String orderCommodity;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 用户余额
     */
    private BigDecimal userBalance;
    /**
     * 是否为退款订单
     * 0为消费订单
     * 1为退款订单
     */
    private Boolean refund;

    private Date orderTime;

    public OrderBO(Order order, BigDecimal userBalance) {
        this.orderId = order.getOrderId();
        this.orderSerial = order.getOrderSerial();
        this.orderCommodity = order.getOrderCommodity();
        this.orderAmount = order.getOrderAmount();
        this.userBalance = userBalance;
        this.refund = order.getRefund();
        this.orderTime = order.getOrderTime();
    }
}
