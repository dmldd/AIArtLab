package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreList implements Serializable {
    private Integer id;
    private  Integer userId;
    private  Integer picId;
}
