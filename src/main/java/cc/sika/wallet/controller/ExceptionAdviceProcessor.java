package cc.sika.wallet.controller;

import cc.sika.wallet.exception.HeaderNoUserInfoException;
import cc.sika.wallet.exception.NoSuchUserException;
import cc.sika.wallet.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 18:40
 */
@RestController
@SuppressWarnings("rawtypes")
@RestControllerAdvice
@Slf4j
public class ExceptionAdviceProcessor extends BaseController {

    @ExceptionHandler(HeaderNoUserInfoException.class)
    protected BaseResponse noSuchUserException(HeaderNoUserInfoException exception) {
        processErrorLog(exception);
        return responseFail(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected BaseResponse noSuchUserException(NoSuchUserException exception) {
        processErrorLog(exception);
        return responseFail(HttpStatus.BAD_REQUEST);
    }
}
