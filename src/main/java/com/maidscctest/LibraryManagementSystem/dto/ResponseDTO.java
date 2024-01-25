package com.maidscctest.LibraryManagementSystem.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO {
    public HttpStatus status;
    public String message;

    public Object data;

    public ResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
