package cc.sika.wallet.exception;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:38
 */
public class NoSuchUserException extends RuntimeException{

    private final String message;

    public NoSuchUserException() {
        message = "未找到该用户信息";
    }

    public NoSuchUserException(String message) {
        this.message = message;
    }
}
