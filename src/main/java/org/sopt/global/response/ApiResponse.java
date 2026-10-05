package org.sopt.global.response;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청이 성공했습니다.", data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
