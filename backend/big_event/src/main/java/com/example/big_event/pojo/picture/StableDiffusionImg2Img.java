package com.example.big_event.pojo.picture;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StableDiffusionImg2Img implements Serializable {
    private List<String> init_images;
    private String prompt;
    private  String negative_prompt;
    private String mask;
    private Integer steps;
    private Integer cfg_scale;
    private Integer width;
    private Integer height;
    private double denoising_strength;
    private OverrideSettings override_settings;
    private String sampler_index;
    private String sampler_name;
    private AlwaysonScripts alwayson_scripts;
    private Integer seed;
    private Integer subseed;

}
