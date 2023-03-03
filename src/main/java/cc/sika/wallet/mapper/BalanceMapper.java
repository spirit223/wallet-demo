package cc.sika.wallet.mapper;

import cc.sika.wallet.po.Balance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:35
 */
@Mapper
public interface BalanceMapper {
    /**
     * 添加账户
     * @param balance
     * @return
     */
    int addBalance(Balance balance);

    /**
     * 添加余额
     * @param balanceId
     * @param bigDecimal
     * @return
     */
    int addMount(@Param("balanceId") int balanceId, BigDecimal bigDecimal);

    /**
     * 冻结金额
     * @param balanceId
     * @param bigDecimal
     * @return
     */
    int frozenAmount(@Param("balanceId") int balanceId, BigDecimal bigDecimal);

    int frozenAmountByUserId(@Param("userId") int userId, BigDecimal frozenAmount);

    /**
     * 获取可用余额
     * @param balanceId
     * @return
     */
    BigDecimal getAvailableAmount(@Param("balanceId") int balanceId);

    BigDecimal getAvailableAmountByUserId(@Param("userId") int userId);

    /**
     * 获取冻结金额
     * @param balanceId
     * @return
     */
    BigDecimal getFrozenAmount(@Param("balanceId") int balanceId);

    /**
     * 获取总金额(可用+冻结)
     * @param balanceId
     * @return
     */
    BigDecimal getTotalMount(@Param("balanceId") int balanceId);

    /**
     * 删除账户
     * @param balanceId
     * @return
     */
    int deleteBalance(@Param("balanceId") int balanceId);

    /**
     * 根据整个Balance的信息修改账户, 可用余额会根据总金额和冻结金额进行结算
     * @param account
     * @return
     */
    int modifyAccount(Balance account);
}
