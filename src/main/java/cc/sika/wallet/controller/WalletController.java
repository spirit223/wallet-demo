package cc.sika.order.controller;

import cc.sika.order.exception.HeaderNoUserInfoException;
import cc.sika.order.exception.NoSuchUserException;
import cc.sika.order.service.BalanceService;
import cc.sika.order.vo.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:54
 */
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController{

    @Resource
    private BalanceService balanceService;

    /**
     * 查询用户的钱包余额
     * @return 余额数
     */
    @GetMapping("/balance")
    public BaseResponse<BigDecimal> checkBalance(@RequestHeader("user-id") int userId) throws NoSuchUserException, HeaderNoUserInfoException {
        if (userId <= 0) {
            throw new HeaderNoUserInfoException();
        }
        // todo api定参, 服务层接口定参, 业务逻辑组织
        BigDecimal availableAmount = balanceService.getAvailableAmount(userId);
        return responseSuccess(availableAmount);
    }
}
