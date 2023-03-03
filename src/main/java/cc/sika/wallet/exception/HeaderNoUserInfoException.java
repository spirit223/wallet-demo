package cc.sika.wallet.exception;

/**
 * 请求头中未携带用户id信息, 无法确定钱包操作对象时抛出
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:45
 */
public class HeaderNoUserInfoException extends RuntimeException{
    private final String message;

    public HeaderNoUserInfoException() {
        this.message = "请求头中未携带用户id";
    }

    public HeaderNoUserInfoException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
