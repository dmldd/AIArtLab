package com.example.big_event.exception;

import lombok.Getter;

@Getter
public class ServiceException  extends RuntimeException{
    private Integer code;
    public ServiceException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
}
