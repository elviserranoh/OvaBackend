package com.university.discretas.controller;

import com.university.discretas.entity.ContentTopic;
import com.university.discretas.entity.Ova;
import com.university.discretas.entity.Topic;
import com.university.discretas.service.ContentTopicService;
import com.university.discretas.service.IUploadFileService;
import com.university.discretas.service.OvaService;
import com.university.discretas.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Log
@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {

    private final IUploadFileService fileService;
    private final TopicService service;
    private final ContentTopicService contentTopicService;
    private final OvaService ovaService;

    @GetMapping( headers = {"Authorization"})
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String title) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Topic> entities;

        if (Objects.isNull(title)) {
            entities = service.findAll(pageable);
        } else {
            entities = service.findAllByTitleContainingIgnoreCase(title, pageable);
        }

        return ResponseEntity.ok(entities);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Topic entity = service.findById(id).orElse(null);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Topic current = service.findById(id).orElse(null);

        if (Objects.nonNull(current)) {
            service.deleteById(id);
            if (Objects.nonNull(current.getImage())) {
                fileService.delete(current.getImage());
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestParam("ovaId") Long ovaId,
            @RequestParam("index") Integer index,
            @RequestParam("title") String title,
            @RequestParam("objetive") String objetive,
            @RequestParam("contentTopic") List<String> contentTopic,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        String fileName = "";

        try {

            Ova ova = ovaService.findById(ovaId).orElse(null);

            if (Objects.isNull(ova)) {
                throw new Exception("El OVA no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileName = fileService.copy(imageFile);
            }

            Topic entity = Topic.builder()
                    .index(index)
                    .title(title)
                    .objetive(objetive)
                    .ova(ova)
                    .image(fileName)
                    .build();

            List<ContentTopic> contentTopics = contentTopic.stream().map(item -> new ContentTopic(item, entity)).collect(Collectors.toList());

            entity.setContents(contentTopics);

            Topic currentEntity = service.save(entity);

            URI uri = URI.create(String.format("api/topic/%d", currentEntity.getId()));

            return ResponseEntity.created(uri).body(currentEntity);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam("ovaId") Long ovaId,
            @RequestParam("index") Integer index,
            @RequestParam("title") String title,
            @RequestParam("objetive") String objetive,
            @RequestParam("contentTopic") List<String> contentTopic,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {


        Map<String, Object> response = new HashMap<>();

        try {

            String fileName = "";
            Topic current = service.findById(id).orElse(null);
            Ova ova = ovaService.findById(ovaId).orElse(null);

            if (Objects.isNull(current)) {
                throw new Exception("El Topic no existe");
            }

            if (Objects.isNull(ova)) {
                throw new Exception("El OVA no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileService.delete(current.getImage());
                fileName = fileService.copy(imageFile);
                current.setImage(fileName);
            } else {
                fileName = current.getImage();
            }

            current.setId(id);
            current.setIndex(index);
            current.setTitle(title);
            current.setObjetive(objetive);
            current.setOva(ova);

            contentTopicService.deleteAllByTopic(current);

            List<ContentTopic> contentTopics = contentTopic.stream().map(item -> new ContentTopic(item, current)).collect(Collectors.toList());

            current.setContents(contentTopics);

            service.edit(current);

            response.put("content", current);
            response.put("status", HttpStatus.OK.value());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }

    }

}
