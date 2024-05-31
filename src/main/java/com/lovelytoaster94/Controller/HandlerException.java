package com.lovelytoaster94.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result handleException(Exception e) {
        return new Result(Code.SERVICE_FAILED,"发生错误" , e.toString());
    }
}
