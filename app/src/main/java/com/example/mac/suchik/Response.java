package com.example.mac.suchik;

public class Response<T> {
    public int type;
    public T response;

    public Response(int type, T response) {
        this.type = type;
        this.response = response;
    }
}
