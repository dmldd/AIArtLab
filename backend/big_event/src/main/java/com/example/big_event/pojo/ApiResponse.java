package com.example.big_event.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse implements Serializable {
    private String[] images;
    private String info;
//    private Picture parameters;
}
