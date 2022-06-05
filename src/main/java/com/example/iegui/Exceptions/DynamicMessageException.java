package com.example.iegui.Exceptions;

public class DynamicMessageException extends Exception {
    String message;

    public DynamicMessageException(String message){
        this.message=message;
    }


    @Override
    public String getMessage() {
       return message;
    }
}
