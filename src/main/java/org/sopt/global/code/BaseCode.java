package org.sopt.global.code;

import org.springframework.http.HttpStatus;

public interface BaseCode {

    String getMessage();

    HttpStatus getHttpStatus();
}
