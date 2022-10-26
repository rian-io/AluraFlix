package dev.rianoliveira.aluraflix.api.video;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import dev.rianoliveira.aluraflix.exception.InvalidEntityException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoService {

    private VideoRepository repository;

    public List<Video> findAll() {
        return repository.findAll();
    }

    public Video findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public Video create(Video video) throws InvalidEntityException {
        if (isValid(video))
            return repository.insert(video);

        throw new InvalidEntityException("Invalid video");
    }

    public Video update(Video video) throws InvalidEntityException {
        if (isValid(video) && hasId(video))
            return repository.save(video);
        
        throw new InvalidEntityException("Invalid video");
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private boolean isValid(Video video) {
        if (video.getDescricao() == null || video.getDescricao().trim().isEmpty()) return false;
        if (video.getTitulo() == null || video.getTitulo().trim().isEmpty()) return false;
        if (video.getUrl() == null || video.getUrl().trim().isEmpty()) return false;

        Pattern urlRegex = Pattern.compile("(https?:\\/\\/)[\\w/\\-?=%.]+\\.[\\w/\\-&?=%.]+");
        Matcher matcher = urlRegex.matcher(video.getUrl());
        return matcher.matches();
    }

    private boolean hasId(Video video) {
        return !(video.getId() == null || video.getId().trim().isEmpty());
    }

}
