package cc.sika.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 19:03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBalanceVO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private int userId;
    private BigDecimal amount;
}
