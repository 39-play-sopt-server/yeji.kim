package org.sopt.global.response;

import org.sopt.global.code.BaseCode;
import org.sopt.global.code.SuccessBaseCode;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> of(BaseCode baseCode, T data) {
        boolean success = baseCode instanceof SuccessBaseCode;

        return new ApiResponse<>(success, baseCode.getMessage(), data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(
            BaseCode baseCode,
            T data
    ) {
        return ResponseEntity
                .status(baseCode.getHttpStatus())
                .body(ApiResponse.of(baseCode, data));
    }
}
