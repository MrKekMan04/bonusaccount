package ru.vitaliyefimov.bonusaccount.dto.http;

import java.util.List;

public record Response<T>(
    T data,
    List<String> errors
) {

    public static <T> Response<T> success(T data) {
        return new Response<>(data, List.of());
    }

    public static Response<Void> success() {
        return new Response<>(null, List.of());
    }

    public static <T> Response<T> fail(List<String> errors) {
        return new Response<>(null, errors);
    }
}
