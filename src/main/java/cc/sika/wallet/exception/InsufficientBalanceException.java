package cc.sika.wallet.exception;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 19:10
 */
public class InsufficientBalanceException extends RuntimeException{
    private final String message;

    public InsufficientBalanceException() {
        message = "用户余额不足, 请进行充值";
    }

    public InsufficientBalanceException(String message) {
        this.message = message;
    }
}
