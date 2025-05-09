package com.lovelytoaster94.Controller;

import com.lovelytoaster94.Until.Code;
import com.lovelytoaster94.Until.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Result> handleException(Exception e) {
        Result result = new Result(Code.SERVICE_FAILED, "发生错误，请联系管理员", e.toString());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
