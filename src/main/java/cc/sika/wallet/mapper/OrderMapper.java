package cc.sika.order.mapper;

import cc.sika.order.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:30
 */
@Mapper
public interface OrderMapper {
    int addOrder(Order order);

    int deleteOrder(@Param("orderId") Integer orderId);

    int updateOrder(Order order);

    Order getOrderByOrderId(@Param("orderId") int orderId);
}
