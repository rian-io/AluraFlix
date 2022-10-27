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
        return repository.findAllByDeleted(false);
    }

    public Video findById(String id) {
        return repository.findByIdAndDeleted(id, false).orElseThrow();
    }

    public Video create(Video video) throws InvalidEntityException {
        if (isInvalid(video))
            throw new InvalidEntityException("Invalid video");
            
        video.setDeleted(false);
        return repository.insert(video);
    }

    public Video update(Video video) throws InvalidEntityException {
        if (isInvalid(video) && hasNoId(video))
            throw new InvalidEntityException("Invalid video");
        
        video.setDeleted(false);
        return repository.save(video);        
    }

    public Video delete(String id) {
        Video video = findById(id);
        video.setDeleted(true);
        return repository.save(video);
    }

    private boolean isInvalid(Video video) {
        if (video.getDescricao() == null || video.getDescricao().trim().isEmpty()) return true;
        if (video.getTitulo() == null || video.getTitulo().trim().isEmpty()) return true;
        if (video.getUrl() == null || video.getUrl().trim().isEmpty()) return true;

        Pattern urlRegex = Pattern.compile("(https?:\\/\\/)[\\w/\\-?=%.]+\\.[\\w/\\-&?=%.]+");
        Matcher matcher = urlRegex.matcher(video.getUrl());
        return !matcher.matches();
    }

    private boolean hasNoId(Video video) {
        return (video.getId() == null || video.getId().trim().isEmpty());
    }

}
