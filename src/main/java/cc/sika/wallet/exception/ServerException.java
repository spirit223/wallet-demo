package cc.sika.wallet.exception;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 19:42
 */
public class ServerException extends RuntimeException{
    private final String message;

    public ServerException(String message) {
        this.message = message;
    }
}
