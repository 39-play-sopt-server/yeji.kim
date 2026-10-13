package org.sopt.global.response;

import org.sopt.global.code.BaseCode;
import org.sopt.global.code.SuccessBaseCode;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> of(BaseCode baseCode) {
        boolean success = baseCode instanceof SuccessBaseCode;

        return new ApiResponse<>(success, baseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청이 성공했습니다.", data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
