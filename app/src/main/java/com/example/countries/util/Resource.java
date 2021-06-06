package com.example.countries.util;

public abstract class Resource<T> {

    public Resource(T data, String message) {

    }

    public static class Success<T> extends Resource<T> {

        T data;

        public Success(T data) {
            super(data, null);

            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static class Loading<T> extends Resource<T> {

        T data;

        public Loading(T data) {
            super(null, null);

            this.data = data;
        }
    }

    public static class Error<T> extends Resource<T> {

        T data;
        String message;

        public Error(T data, String message) {
            super(null, message);

            this.data = data;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}