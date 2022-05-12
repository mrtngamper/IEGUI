package com.example.iegui.Exceptions;

/**
 * Exception is thrown, when yaml attribute has wrong type
 */
public class YAMLTypeNotValidException extends Exception{

    private String expected;
    private String real;
    private String name;

    private String filename;
    public YAMLTypeNotValidException(String expected, String real, String name, String filename){
        this.expected=expected;
        this.real=real;
        this.name=name;
        this.filename=filename;
    }

    @Override
    public String getMessage() {
        return String.format("Yaml type not valid! Expected type: %s. Real type: %s. Attribute Name: %s. File name: %s\n",expected,real,name,filename);
    }
}
