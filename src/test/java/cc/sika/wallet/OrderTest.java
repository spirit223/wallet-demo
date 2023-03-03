package cc.sika.order;

import cc.sika.order.mapper.OrderMapper;
import cc.sika.order.po.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 15:16
 */
@SpringBootTest
public class OrderTest {

    @Resource
    private OrderMapper orderMapper;
    @Test
    void testAdd() {
        Order order = new Order("aaaaaaa", 0, new BigDecimal(20), "虚拟商品", false);
        orderMapper.addOrder(order);
    }

    @Test
    void testQuery() {
        Order orderByOrderId = orderMapper.getOrderByOrderId(4);
        System.out.println();
        System.out.println(orderByOrderId.getOrderTime());
        System.out.println();
        System.out.println(orderByOrderId);
    }

    @Test
    void testDecimal() {
        BigDecimal bigDecimal = new BigDecimal(-10);
        int i = bigDecimal.compareTo(new BigDecimal(0));
        System.out.println(i);
    }

    @Test
    void testUpdate() {
        Order order = new Order(4,"aaaaaaa", 0, new BigDecimal(5), "虚拟商品", false, new Date(System.currentTimeMillis()));
        orderMapper.updateOrder(order);
    }
}
