package dev.rianoliveira.aluraflix.api.video;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.rianoliveira.aluraflix.exception.InvalidEntityException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class VideoController {

    private ModelMapper mapper;

    private VideoService service;

    @GetMapping("/videos")
    public List<Video> findAll() {
        return service.findAll();
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> findById(@PathVariable String id) {
        try {
            Video foundVideo = service.findById(id);

            return new ResponseEntity<>(mapToDTO(foundVideo), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            VideoDTO responseDTO = new VideoDTO();
            responseDTO.setErro("There is no Video with given ID");

            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/videos")
    public ResponseEntity<VideoDTO> create(@RequestBody VideoDTO videoDTO) {
        try {
            Video video = mapToEntity(videoDTO);
            video = service.create(video);

            return new ResponseEntity<>(mapToDTO(video), HttpStatus.CREATED);
        } catch (InvalidEntityException ex) {
            VideoDTO responseDTO = new VideoDTO();
            responseDTO.setErro(ex.getLocalizedMessage());

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/patch")
    public ResponseEntity<VideoDTO> update(@RequestBody VideoDTO videoDTO) {
        try {
            Video video = mapToEntity(videoDTO);
            video = service.update(video);
            
            return new ResponseEntity<>(mapToDTO(video), HttpStatus.OK);
        } catch (InvalidEntityException ex) {
            VideoDTO responseDTO = new VideoDTO();
            responseDTO.setErro(ex.getLocalizedMessage());

            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> delete(@PathVariable String id) {
        Video deletedVideo = service.delete(id);

        return new ResponseEntity<>(mapToDTO(deletedVideo), HttpStatus.OK);
    }

    private VideoDTO mapToDTO(Video video) {
        return mapper.map(video, VideoDTO.class);
    }

    private Video mapToEntity(VideoDTO dto) {
        return mapper.map(dto, Video.class);
    }

}
