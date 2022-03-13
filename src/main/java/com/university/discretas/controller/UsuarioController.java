package com.university.discretas.controller;

import com.university.discretas.dto.CreateUserRequest;
import com.university.discretas.dto.EditUserRequest;
import com.university.discretas.entity.Usuario;
import com.university.discretas.service.EmailService;
import com.university.discretas.service.IUploadFileService;
import com.university.discretas.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(path = "/api/student")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUploadFileService fileService;
    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<?> load() {
        List<Usuario> users = usuarioService.findByAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/page/{page}/pagesize/{pagesize}")
    public ResponseEntity<?> pageable(@PathVariable Integer page,
                                      @PathVariable Integer pagesize,
                                      @RequestParam(required = false) String filterSearch,
                                      @RequestParam(required = false) String valueSearch) {
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<Usuario> users = null;

        if (Objects.isNull(filterSearch) && Objects.isNull(valueSearch)) {
            users = usuarioService.findAllByRolEquals(pageable);
        } else {
            users = usuarioService.findByAllByTextSearch(pageable, filterSearch, valueSearch);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {

            Usuario currentUser = usuarioService.findById(id);

            if (currentUser == null) {
                response.put("errors", "El estudiante no existe");
                response.put("status", 400);

                return ResponseEntity.badRequest().body(response);
            }

            usuarioService.deleteById(id);
            response.put("message", "Estudiante eliminado con exito.");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("message", "No se puedo eliminar el estudiante");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest usuario, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors()
                    .forEach(error -> {
                        errors.put(error.getField(), error.getDefaultMessage());
                    });

            response.put("errors", errors);
            response.put("status", HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }

        try {

            Usuario newUser = usuarioService.save(usuario);
            response.put("usuario", newUser);
            response.put("status", 201);
            URI location = URI.create(String.format("/api/usuarios/%d", newUser.getId()));

            return ResponseEntity.created(location).body(response);

        } catch (Exception e) {
            response.put("status", 500);
            response.put("errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserData(@PathVariable("id") Long usuarioID, @Valid @RequestBody EditUserRequest usuario,
                                            BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        Usuario currentUser = usuarioService.findById(usuarioID);

        if (currentUser == null) {
            response.put("errors", "El estudiante no existe");
            response.put("status", 400);

            return ResponseEntity.badRequest().body(response);
        }

        try {

            currentUser.setFirstName(usuario.getFirstName());
            currentUser.setEmail(usuario.getEmail());
            currentUser.setLastName(usuario.getLastName());
            currentUser.setPhone(usuario.getPhone());

            if (usuario.getPassword().length() > 0) {
                currentUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            Usuario newUser = usuarioService.update(currentUser);

            response.put("usuario", newUser);
            response.put("status", 201);
            URI location = URI.create(String.format("/api/usuarios/%d", newUser.getId()));

            return ResponseEntity.created(location).body(response);

        } catch (Exception e) {
            response.put("status", 500);
            response.put("errors", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> save(@PathVariable("id") Long usuarioID,
                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        String fileName = "";

        try {

            if (Objects.nonNull(imageFile)) {

                Usuario current = usuarioService.findById(usuarioID);

                if (Objects.isNull(current)) {
                    throw new Exception("Usuario no existe");
                }

                fileName = fileService.copy(imageFile);

                current.setImage(fileName);

                usuarioService.update(current);

                return ResponseEntity.ok(fileName);
            }

            throw new Exception("No se pudo actualizar la imagen del usuario");

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }
    }

}
