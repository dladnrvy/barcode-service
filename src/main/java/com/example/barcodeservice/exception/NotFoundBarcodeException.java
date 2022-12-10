package com.example.barcodeservice.exception;

public class NotFoundBarcodeException extends RuntimeException{
    public NotFoundBarcodeException() {
        super();
    }

    public NotFoundBarcodeException(String message) {
        super(message);
    }

    public NotFoundBarcodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundBarcodeException(Throwable cause) {
        super(cause);
    }

    protected NotFoundBarcodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
