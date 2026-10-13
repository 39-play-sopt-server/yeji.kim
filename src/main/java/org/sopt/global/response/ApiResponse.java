package org.sopt.global.response;

import org.sopt.global.code.BaseCode;
import org.sopt.global.code.SuccessBaseCode;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> of(BaseCode baseCode, T data) {
        boolean success = baseCode instanceof SuccessBaseCode;

        return new ApiResponse<>(success, baseCode.getMessage(), data);
    }
}
