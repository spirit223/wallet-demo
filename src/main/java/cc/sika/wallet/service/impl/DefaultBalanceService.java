package cc.sika.order.service.impl;

import cc.sika.order.exception.NoSuchUserException;
import cc.sika.order.mapper.BalanceMapper;
import cc.sika.order.po.User;
import cc.sika.order.service.BalanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:55
 */
@Service
public class DefaultBalanceService implements BalanceService {

    @Resource
    private BalanceMapper balanceMapper;

    @Override
    public BigDecimal getAvailableAmount(int userId) throws NoSuchUserException {
        return balanceMapper.getAvailableMount(userId);
    }
}
