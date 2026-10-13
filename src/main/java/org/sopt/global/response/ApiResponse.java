package org.sopt.global.response;

import org.sopt.global.code.BaseCode;
import org.sopt.global.code.ErrorCode;
import org.sopt.global.code.SuccessBaseCode;
import org.sopt.global.code.SuccessCode;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> of(BaseCode baseCode, T data) {
        boolean success = baseCode instanceof SuccessBaseCode;

        return new ApiResponse<>(success, baseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, SuccessCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> failure(T data) {
        return new ApiResponse<>(false, ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), null);
    }
}
