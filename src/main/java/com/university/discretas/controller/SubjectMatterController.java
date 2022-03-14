package com.university.discretas.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.discretas.dto.AnswerCorrectRequest;
import com.university.discretas.entity.*;
import com.university.discretas.service.*;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("/api/subject-matter")
@RequiredArgsConstructor
public class SubjectMatterController {

    private final IUploadFileService fileService;
    private final TopicService topicService;
    private final SubjectMatterService subjectMatterService;
    private final SubjectMatterRowService subjectMatterRowService;
    private final SubjectMatterColumnService subjectMatterColumnService;
    private final SubjectMatterAnswerCorrectService subjectMatterAnswerCorrectService;

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String title) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SubjectMatter> entities;

        if (Objects.isNull(title)) {
            entities = subjectMatterService.findAll(pageable);
        } else {
            entities = subjectMatterService.findAllByTopic_TitleContainingIgnoreCase(title, pageable);
        }

        return ResponseEntity.ok(entities);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SubjectMatter entity = subjectMatterService.findById(id).orElse(null);
        return ResponseEntity.ok(entity);
    }

    @GetMapping(value = "/{id}/topic", headers = {"Authorization"})
    public ResponseEntity<?> findAllByTopic(@PathVariable Long id) {

        Topic topic = topicService.findById(id).orElse(null);

        if(topic == null) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("error", "No existe el topico");
            errors.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }

        List<SubjectMatter> entities = subjectMatterService.findAllByTopic(topic);
        return ResponseEntity.ok(entities);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        SubjectMatter current = subjectMatterService.findById(id).orElse(null);

        if (Objects.nonNull(current)) {
            subjectMatterService.deleteById(current.getId());
            if (Objects.nonNull(current.getImage())) {
                fileService.delete(current.getImage());
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestParam("topicId") Long topicId,
            @RequestParam("title") String title,
            @RequestParam("index") Integer index,
            @RequestParam("type") String type,
            @RequestParam("question") String question,
            @RequestParam("rows") List<String> rows,
            @RequestParam("columns") List<String> columns,
            @RequestParam("answerCorrects") String corrects,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            String fileName = "";
            Topic topic = topicService.findById(topicId).orElse(null);

            ObjectMapper objectMapper = new ObjectMapper();
            List<AnswerCorrectRequest> answerCorrects = objectMapper.readValue(corrects, new TypeReference<List<AnswerCorrectRequest>>() {
            });

            if (Objects.isNull(topic)) {
                throw new Exception("El Tema no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileName = fileService.copy(imageFile);
            }

            SubjectMatter entity = SubjectMatter.builder()
                    .index(index)
                    .title(title)
                    .topic(topic)
                    .type(type)
                    .question(question)
                    .image(fileName)
                    .build();

            List<SubjectMatterRow> rowList = rows.stream().map(item -> new SubjectMatterRow(item, entity)).collect(Collectors.toList());
            List<SubjectMatterColumn> columnList = columns.stream().map(item -> new SubjectMatterColumn(item, entity)).collect(Collectors.toList());

            List<SubjectMatterAnswerCorrect> answerCorrectList = answerCorrects.stream().map(item ->
                            new SubjectMatterAnswerCorrect(entity,
                                    getColumn(columnList, item.getColumn()),
                                    getRow(rowList, item.getRow())
                            ))
                    .collect(Collectors.toList());


            entity.setRows(rowList);
            entity.setColumns(columnList);
            entity.setAnswerCorrects(answerCorrectList);

            SubjectMatter currentEntity = subjectMatterService.save(entity);

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
            @RequestParam("topicId") Long topicId,
            @RequestParam("index") Integer index,
            @RequestParam("type") String type,
            @RequestParam("title") String title,
            @RequestParam("question") String question,
            @RequestParam("rows") List<String> rows,
            @RequestParam("columns") List<String> columns,
            @RequestParam("answerCorrects") String corrects,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {


        Map<String, Object> response = new HashMap<>();

        try {

            String fileName = "";
            SubjectMatter entity = subjectMatterService.findById(id).orElse(null);
            Topic topic = topicService.findById(topicId).orElse(null);

            ObjectMapper objectMapper = new ObjectMapper();
            List<AnswerCorrectRequest> answerCorrects = objectMapper.readValue(corrects, new TypeReference<List<AnswerCorrectRequest>>() {
            });

            if (Objects.isNull(entity)) {
                throw new Exception("El Contenido no existe");
            }

            if (Objects.isNull(topic)) {
                throw new Exception("El Tema no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileService.delete(entity.getImage());
                fileName = fileService.copy(imageFile);
                entity.setImage(fileName);
            } else {
                fileName = entity.getImage();
            }

            if (!entity.getColumns().isEmpty()) {
                subjectMatterColumnService.deleteAllBySubjectMatter(entity);
            }

            if (!entity.getRows().isEmpty()) {
                subjectMatterRowService.deleteAllBySubjectMatter(entity);
            }

            if (!entity.getAnswerCorrects().isEmpty()) {
                subjectMatterAnswerCorrectService.deleteAllBySubjectMatter(entity);
            }

            List<SubjectMatterRow> rowList = rows.stream().map(item -> new SubjectMatterRow(item, entity)).collect(Collectors.toList());
            List<SubjectMatterColumn> columnList = columns.stream().map(item -> new SubjectMatterColumn(item, entity)).collect(Collectors.toList());

            List<SubjectMatterAnswerCorrect> answerCorrectList = answerCorrects.stream().map(item ->
                            new SubjectMatterAnswerCorrect(entity,
                                    getColumn(columnList, item.getColumn()),
                                    getRow(rowList, item.getRow())
                            ))
                    .collect(Collectors.toList());

            entity.setColumns(columnList);
            entity.setRows(rowList);
            entity.setAnswerCorrects(answerCorrectList);
            entity.setQuestion(question);
            entity.setType(type);
            entity.setIndex(index);
            entity.setTitle(title);

            subjectMatterService.edit(entity);

            response.put("content", entity);
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


    public SubjectMatterRow getRow(List<SubjectMatterRow> rows, String content) {
        SubjectMatterRow row = rows.stream().filter(item -> {
            if (item.getContent().equalsIgnoreCase(content)) {
                return true;
            }
            return false;
        }).findFirst().orElse(null);
        return row;
    }

    public SubjectMatterColumn getColumn(List<SubjectMatterColumn> columns, String content) {
        SubjectMatterColumn column = columns.stream().filter(item -> {
            if (item.getContent().equalsIgnoreCase(content)) {
                return true;
            }
            return false;
        }).findFirst().orElse(null);
        return column;
    }

}
