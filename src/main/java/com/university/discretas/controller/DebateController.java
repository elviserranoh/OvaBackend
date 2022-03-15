package com.university.discretas.controller;

import com.university.discretas.dto.DebateCommentRequest;
import com.university.discretas.dto.DebateRequest;
import com.university.discretas.entity.Debate;
import com.university.discretas.entity.DebateComment;
import com.university.discretas.entity.Usuario;
import com.university.discretas.service.DebateCommentService;
import com.university.discretas.service.DebateService;
import com.university.discretas.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Log
@RestController
@RequestMapping("/api/debate")
@RequiredArgsConstructor
public class DebateController {

    private final IUsuarioService usuarioService;
    private final DebateService debateService;
    private final DebateCommentService debateCommentService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(debateService.findAll());
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> findAllCommentByDebate(@PathVariable Long id) {
        Debate debate = debateService.findById(id).orElse(null);

        if (debate == null) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("message", "El debate no existe");
            errors.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(debateCommentService.findAllByDebate(debate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(debateService.findById(id));
    }

    @GetMapping("/{title}/title")
    public ResponseEntity<?> findAllByTitle(@PathVariable String title) {
        return ResponseEntity.ok(debateService.findAllByTitleContainingIgnoreCase(title));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DebateRequest debateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario currentUser = usuarioService.findByIdentityDocument(authentication.getName());

            Debate debate = Debate.builder()
                    .title(debateRequest.getTitle())
                    .description(debateRequest.getDescription())
                    .usuario(currentUser)
                    .build();

            Debate current = debateService.save(debate);

            URI uri = URI.create(String.format("api/debate/%d", current.getId()));
            return ResponseEntity.created(uri).body(current);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", "No se pudo crear el debate");
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> saveComment(@PathVariable Long id, @RequestBody DebateCommentRequest debateCommentRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario currentUser = usuarioService.findByIdentityDocument(authentication.getName());

            Debate debate = debateService.findById(debateCommentRequest.getId()).orElse(null);

            if (debate == null) {
                Map<String, Object> errors = new HashMap<>();
                errors.put("message", "El debate no existe");
                errors.put("status", HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().body(errors);
            }

            DebateComment debateComment = DebateComment.builder()
                    .description(debateCommentRequest.getDescription())
                    .usuario(currentUser)
                    .debate(debate)
                    .build();

            Debate current = debateService.save(debate);

            URI uri = URI.create(String.format("api/debate/%d", current.getId()));
            return ResponseEntity.created(uri).body(current);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", "No se pudo crear el debate");
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }
    }


}
