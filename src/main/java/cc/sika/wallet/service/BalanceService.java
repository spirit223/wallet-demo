package cc.sika.order.service;

import cc.sika.order.exception.NoSuchUserException;
import cc.sika.order.po.User;

import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:55
 */
public interface BalanceService {
    BigDecimal getAvailableAmount(int userId) throws NoSuchUserException;
}
