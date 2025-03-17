package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//指定用多少个contronet
public class ControlNet {

    private List<Args> args;
    //非函数里无法进行添加操作

}
