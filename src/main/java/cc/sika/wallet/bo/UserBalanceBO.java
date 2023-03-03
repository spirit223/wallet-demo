package cc.sika.wallet.bo;

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
public class UserBalanceBO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private int userId;
    private BigDecimal amount;
    private int orderId;

    public UserBalanceBO(int userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public UserBalanceBO(int userId, int orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }
}
