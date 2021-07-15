package com.vote.errors;


import com.vote.basic.response.ResponseCode;
import com.vote.basic.response.ResponseWrap;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller advice 用于转化对客户端友好的异常
 */
@ControllerAdvice
public class ExceptionTranslator {

    private static final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseWrap processConcurencyError(ConcurrencyFailureException ex) {
        return new ResponseWrap(ResponseCode.CODE_900);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrap processRequestParameterError(MissingServletRequestParameterException ex) {
        log.error("请求参数错误：", ex);
        return new ResponseWrap(ResponseCode.CODE_1001, "请求参数错误：" + ex.getParameterName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrap<List<FieldErrorVM>> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        log.error("请求参数错误：", ex);
        return processFieldErrors(fieldErrors);
    }

    private ResponseWrap<List<FieldErrorVM>> processFieldErrors(List<FieldError> fieldErrors) {
        List<FieldErrorVM> resultList = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            resultList.add(new FieldErrorVM(null, fieldError.getField(), fieldError.getDefaultMessage()));
        }

        FieldErrorVM errorVM = resultList.get(0);
        return ResponseWrap.failure(ResponseCode.CODE_1000, resultList, errorVM.getField(), errorVM.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseWrap processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error("异常信息：", exception);
        return new ResponseWrap(ResponseCode.CODE_901);
    }

    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrap processMethodNotFoundException(NoHandlerFoundException exception) {
        log.error("异常信息：", exception);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = requestAttributes.getResponse();
//        ResponseJsonUtils.sendMsgWithServletResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ResponseWrap<>(ResponseCode.CODE_1019));
        return new ResponseWrap(ResponseCode.CODE_1004);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseWrap<String> processRuntimeException(Exception ex) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        log.error("请求接口 ：" + request.getRequestURI() + " ,异常信息：", ex);
        return ResponseWrap.failure(ResponseCode.CODE_400, ex.getMessage());
    }

}
