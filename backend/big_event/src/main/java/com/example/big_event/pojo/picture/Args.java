package com.example.big_event.pojo.picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//指定一个 ControlNet 的所有参数：
public class Args {
    private boolean enabled;
    /**
     * PreProcessor 例如："module": "lineart_coarse"
     */
    private String module;
    private String model;
    /**
     * defaults to 1
     */
    private double weight = 1.0;
    private String image;
    private String mask;
    private String control_mode = "Balanced";
    /**
     * enable pixel-perfect preprocessor. defaults to false
     */
    private boolean pixel_perfect;
    /**
     * whether to compensate low GPU memory with processing time. defaults to false
     */
    private boolean low_vram;
    private int processor_res;
    private int threshold_a;
    private int threshold_b;
    private double guidance_start;
    private double guidance_end = 1.0;
}


