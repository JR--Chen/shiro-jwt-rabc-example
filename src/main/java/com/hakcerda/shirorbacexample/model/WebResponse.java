package com.hakcerda.shirorbacexample.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author JR Chan
 */
@Data
@Accessors(chain = true)
public class WebResponse<T> {
    private int code;

    private String message;

    private T data;

    public static <T> WebResponse<T> success(T data){
        return new WebResponse<T>().setCode(200).setData(data);
    }

    public static <T> WebResponse<T> failUnauthorized(String message){
        return new WebResponse<T>().setCode(401).setMessage(message);
    }

    public static <T> WebResponse<T> failWithForbidden(String message){
        return new WebResponse<T>().setCode(403).setMessage(message);
    }

}
