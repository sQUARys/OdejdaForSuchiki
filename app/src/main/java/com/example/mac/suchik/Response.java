package com.example.mac.suchik;

public class Response<T> {
    int type;
    T response;

    public Response(int type, T response) {
        this.type = type;
        this.response = response;
    }
}
