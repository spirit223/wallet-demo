package cc.sika.order.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表实体类
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 订单流水号
     */
    private String orderSerial;
    /**
     * 订单用户
     */
    private Integer orderUser;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单商品
     */
    private String orderCommodity;
    /**
     * 是否为退款订单
     * 0为消费订单
     * 1为退款订单
     */
    private Boolean refund;


    private Date orderTime;

    public Order(String orderSerial, Integer orderUser, BigDecimal orderAmount, String orderCommodity, boolean refund) {
        this.orderSerial = orderSerial;
        this.orderUser = orderUser;
        this.orderAmount = orderAmount;
        this.orderCommodity = orderCommodity;
        this.refund = refund;
        orderTime = new Date(System.currentTimeMillis());
    }
}
