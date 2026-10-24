package org.sopt.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.global.code.ErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ApiResponse.toResponseEntity(e.getErrorCode(), null);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                ex,
                ApiResponse.of(ErrorCode.INVALID_REQUEST, null),
                headers,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return handleExceptionInternal(
                ex,
                ApiResponse.of(ErrorCode.INVALID_REQUEST, null),
                headers,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return handleExceptionInternal(
                ex,
                new ApiResponse<Void>(false, message, null),
                headers,
                status,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception e) {
        log.error("예상하지 못한 서버 오류가 발생했습니다.", e);

        return ApiResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler({
            OptimisticLockingFailureException.class,
            PessimisticLockingFailureException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleConcurrencyConflict() {
        return ApiResponse.toResponseEntity(ErrorCode.CONCURRENT_REQUEST_CONFLICT, null);
    }
}
