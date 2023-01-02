package com.example.barcodeservice.exception;

import com.example.barcodeservice.dto.basic.BasicResponse;
import com.example.barcodeservice.dto.basic.RtnCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public BasicResponse errorHandler(RuntimeException e) {
        BasicResponse rtn = new BasicResponse<>();
        rtn.setCode(RtnCode.FAIL);
        rtn.setData(e.getMessage());
        return rtn;
    }

}
