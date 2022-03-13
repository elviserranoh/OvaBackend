package com.university.discretas.controller;

import com.university.discretas.entity.Ova;
import com.university.discretas.service.IUploadFileService;
import com.university.discretas.service.OvaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log
@RestController
@RequestMapping("/api/ova")
@RequiredArgsConstructor
public class OvaController {

    private final IUploadFileService fileService;
    private final OvaService ovaService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Ova> ovas = ovaService.findAll();
        return ResponseEntity.ok(ovas);
    }

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Ova> ovas;

        if (Objects.isNull(name)) {
            ovas = ovaService.findAll(pageable);
        } else {
            ovas = ovaService.findAllByNameIsLike(name, pageable);
        }

        return ResponseEntity.ok(ovas);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Ova ova = ovaService.findById(id).orElse(null);
        return ResponseEntity.ok(ova);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Ova current = ovaService.findById(id).orElse(null);


        if (Objects.nonNull(current)) {
            ovaService.deleteById(id);
            fileService.delete(current.getImage());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        String fileName = "";

        try {

            if (Objects.nonNull(imageFile)) {
                fileName = fileService.copy(imageFile);
            }

            Ova ova = Ova.builder()
                    .name(name)
                    .description(description)
                    .image(fileName)
                    .build();

            ovaService.save(ova);


            URI uri = URI.create(fileName);

            return ResponseEntity.created(uri).body(fileName);

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
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {


        Map<String, Object> response = new HashMap<>();

        try {

            String fileName = "";
            Ova current = ovaService.findById(id).orElse(null);

            if(Objects.isNull(current)) {
                throw new Exception("El Ova no existe");
            }

            if (Objects.nonNull(imageFile)) {
                fileService.delete(current.getImage());
                fileName = fileService.copy(imageFile);
                current.setImage(fileName);
            } else {
                fileName = current.getImage();
            }

            current.setDescription(description);
            current.setName(name);

            ovaService.edit(current);

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

    @GetMapping("/image/{name:.+}")
    public ResponseEntity<Resource> imageLoad(@PathVariable String name) {

        Resource resource = null;

        try {
            resource = fileService.load(name);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!Objects.requireNonNull(resource).exists() && !resource.isReadable()) {
            throw new RuntimeException("error no se pudo cargar la imagen: " + name);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

}
