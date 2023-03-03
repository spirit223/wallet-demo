package cc.sika.wallet.controller;

import cc.sika.wallet.bo.ConsumerBO;
import cc.sika.wallet.bo.OrderBO;
import cc.sika.wallet.bo.UserBalanceBO;
import cc.sika.wallet.config.BigDecimalEditor;
import cc.sika.wallet.exception.HeaderNoUserInfoException;
import cc.sika.wallet.exception.NoSuchUserException;
import cc.sika.wallet.service.BalanceService;
import cc.sika.wallet.vo.BaseResponse;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包操作相关接口, 用户的信息全部统一在请求头中进行传递, 键为 "user-id"
 * @author 吴畅
 * @创建时间 2023/3/2 - 17:54
 */
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController{

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());
    }

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

        BigDecimal availableAmount = balanceService.getAvailableAmount(userId);
        return responseSuccess(availableAmount);
    }

    /**
     * 用户消费接口
     * @param userId 通过请求头中传递 userId, 确认用户身份
     * @param amount 使用post请求在请求体中传递消费金额
     */
    @PostMapping("/consume-with-num")
    public BaseResponse<String> consume(@RequestHeader("user-id") int userId, @RequestParam @NumberFormat(pattern = "#.####") BigDecimal amount) {
        String consumeMessage = balanceService.consume(new UserBalanceBO(userId, amount));
        return responseSuccess(consumeMessage);
    }

    @PostMapping("/consume-with-order")
    public BaseResponse<String> consumeWithOrder(@RequestHeader("user-id") int userId, @RequestBody ConsumerBO consumerBO) {
        String consumeMessage = balanceService.consume(userId, consumerBO);
        return responseSuccess(consumeMessage);
    }

    /**
     * 直接指定金额退款接口
     */
    @PostMapping("/refund-with-num")
    public BaseResponse<String> refundWithNum(@RequestHeader("user-id") int userId, @RequestParam @NumberFormat(pattern = "#.####") BigDecimal amount) {
        String refundMessage = balanceService.refundWithNum(new UserBalanceBO(userId, amount));
        return responseSuccess(refundMessage);
    }

    /**
     * 根据订单退款接口
     */
    @PostMapping("/refund-with-order")
    public BaseResponse<String> refundWithOrder(@RequestHeader("user-id") int userId, @RequestParam int orderId) {
        String refundMessage = balanceService.refundWithOrder(new UserBalanceBO(userId, orderId));
        return responseSuccess(refundMessage);
    }

    /**
     * 查看明细接口
     */
    @GetMapping("/balances")
    public BaseResponse<List<OrderBO>> balances(@RequestHeader("user-id") int userId) {
        List<OrderBO> orderDetails = balanceService.getOrderDetails(userId);
        return responseSuccess(orderDetails);
    }
}
