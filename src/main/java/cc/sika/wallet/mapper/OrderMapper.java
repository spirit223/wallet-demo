package cc.sika.wallet.mapper;

import cc.sika.wallet.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Order> getAllOrders(@Param("userId") int userId);
}
