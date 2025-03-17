package com.example.big_event.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private Object data;  //0成功  1失败
    private String message;
    public static Result success(){
        return new Result(0,null,"成功");
    }

    public static Result success(Object data){
        return new Result(0,data,"成功");
    }

     public static Result fail(){
        return new Result(1,null,"失败");
    }
    public static Result fail(String message){
        return new Result(1,null, message);
    }

    public static Result fail(String message,Integer code) {
        return new Result(code,null,message);
    }

}
