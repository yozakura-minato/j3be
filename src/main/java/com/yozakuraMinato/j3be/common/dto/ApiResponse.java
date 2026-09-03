package com.yozakuraMinato.j3be.common.dto;

public record ApiResponse<T>(
        T data,
        String error
) {
    public static <T> ApiResponse<T> data(T data) {
        return new ApiResponse<>(data, null);
    }

    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(null, error);
    }
}
