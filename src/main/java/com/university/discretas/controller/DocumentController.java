package com.university.discretas.controller;

import com.university.discretas.entity.Document;
import com.university.discretas.entity.Ova;
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
import java.util.Map;
import java.util.Objects;

@Log
@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final IUploadFileService fileService;
    private final DocumentService service;
    private final OvaService ovaService;

    @GetMapping(headers = {"Authorization"})
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String title) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Document> entities;

        if (Objects.isNull(title)) {
            entities = service.findAll(pageable);
        } else {
            entities = service.findAllByTitleContainingIgnoreCase(title, pageable);
        }

        return ResponseEntity.ok(entities);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Document entity = service.findById(id).orElse(null);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Document current = service.findById(id).orElse(null);

        if (Objects.nonNull(current)) {
            service.deleteById(id);
            if (Objects.nonNull(current.getImage())) {
                fileService.delete(current.getImage());
            }
            if (Objects.nonNull(current.getDocument())) {
                fileService.delete(current.getDocument());
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestParam("ovaId") Long ovaId,
            @RequestParam("title") String title,
            @RequestParam("autor") String autor,
            @RequestParam("year") Integer year,
            @RequestParam("presentation") String presentation,
            @RequestParam("url") String url,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "documentFile", required = false) MultipartFile documentFile) {

        String imageFileName = "";
        String documentFileName = "";

        try {

            Ova ova = ovaService.findById(ovaId).orElse(null);

            if (Objects.isNull(ova)) {
                throw new Exception("El OVA no existe");
            }

            if (Objects.nonNull(imageFile)) {
                imageFileName = fileService.copy(imageFile);
            }

            if (Objects.nonNull(documentFile)) {
                documentFileName = fileService.copy(documentFile);
            }

            Document entity = Document.builder()
                    .ova(ova)
                    .title(title)
                    .autor(autor)
                    .year(year)
                    .presentation(presentation)
                    .image(imageFileName)
                    .document(documentFileName)
                    .url(url)
                    .build();

            Document currentEntity = service.save(entity);

            URI uri = URI.create(String.format("api/document/%d", currentEntity.getId()));

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
            @RequestParam("title") String title,
            @RequestParam("autor") String autor,
            @RequestParam("year") Integer year,
            @RequestParam("presentation") String presentation,
            @RequestParam("url") String url,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "documentFile", required = false) MultipartFile documentFile) {


        Map<String, Object> response = new HashMap<>();

        try {

            String imageFileName = "";
            String documentFileName = "";
            Document current = service.findById(id).orElse(null);
            Ova ova = ovaService.findById(ovaId).orElse(null);

            if (Objects.isNull(current)) {
                throw new Exception("El Documento no existe");
            }

            if (Objects.isNull(ova)) {
                throw new Exception("El OVA no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileService.delete(current.getImage());
                imageFileName = fileService.copy(imageFile);
                current.setImage(imageFileName);
            } else {
                imageFileName = current.getImage();
            }

            if (Objects.nonNull(documentFile)) {
                fileService.delete(current.getDocument());
                documentFileName = fileService.copy(documentFile);
                current.setDocument(documentFileName);
            } else {
                documentFileName = current.getDocument();
            }

            current.setOva(ova);
            current.setTitle(title);
            current.setAutor(autor);
            current.setYear(year);
            current.setPresentation(presentation);
            current.setUrl(url);
            current.setImage(imageFileName);
            current.setImage(documentFileName);


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
