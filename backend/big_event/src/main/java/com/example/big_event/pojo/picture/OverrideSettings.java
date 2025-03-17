package com.example.big_event.pojo.picture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//   用于指定基础模型和 VAE
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverrideSettings {
    private String sd_model_checkpoint;
    private String sd_vae;

}
