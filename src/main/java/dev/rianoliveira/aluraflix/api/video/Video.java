package dev.rianoliveira.aluraflix.api.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    
    @Id
    private String id;

    private String titulo;

    private String descricao;

    private String url;
    
    private Boolean deleted;

}
