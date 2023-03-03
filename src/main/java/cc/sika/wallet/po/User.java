package cc.sika.order.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User 表实体类
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 余额id
     */
    private Integer balanceId;
    /**
     * 支付密码, 为简单起见使用明文传输
     */
    private String payPassword;
}
