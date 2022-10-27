package dev.rianoliveira.aluraflix.api.video;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {

    List<Video> findAllByDeleted(Boolean deleted);

    Optional<Video> findByIdAndDeleted(String id, Boolean deleted);
    
}
