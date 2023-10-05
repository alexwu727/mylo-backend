package io.github.alexwu727.myloauthenticationservice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex) {
        System.out.println("RuntimeException is handled");
    }
}
