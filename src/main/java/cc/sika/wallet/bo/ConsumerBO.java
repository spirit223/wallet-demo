package cc.sika.wallet.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/3 - 10:55
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsumerBO {
    private BigDecimal orderAmount;
    private String orderCommodity;
    private Boolean refund;
}
