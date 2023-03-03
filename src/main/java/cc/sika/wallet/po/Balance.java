package cc.sika.order.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 余额表实体类
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Balance {
    /**
     * 余额id
     */
    private Integer balanceId;
    /**
     * 余额对应用户
     */
    private Integer userId;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    /**
     * 可用余额
     */
    private BigDecimal availableAmount;

}
