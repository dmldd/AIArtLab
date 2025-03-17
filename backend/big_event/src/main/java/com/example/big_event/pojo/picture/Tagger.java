package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tagger {
    private String image;
    private  String model;
    private double threshold;
    private String queue;
    private String name_in_queue;
}
