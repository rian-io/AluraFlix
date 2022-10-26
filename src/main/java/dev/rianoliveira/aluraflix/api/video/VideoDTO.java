package dev.rianoliveira.aluraflix.api.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    private String id;

    private String titulo;

    private String descricao;

    private String url;

    private String erro;

}
