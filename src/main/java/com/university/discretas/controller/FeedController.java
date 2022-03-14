package com.university.discretas.controller;

import com.university.discretas.entity.Feed;
import com.university.discretas.entity.Usuario;
import com.university.discretas.repository.UsuarioCustomRepository;
import com.university.discretas.service.FeedService;
import com.university.discretas.service.IUsuarioService;
import com.university.discretas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log
@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Feed> feeds = feedService.findAll();
        return ResponseEntity.ok(feeds);
    }

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Feed> feeds;

        if (Objects.isNull(name)) {
            feeds = feedService.findAll(pageable);
        } else {
            feeds = feedService.findAllByDescriptionContainingIgnoreCase(name, pageable);
        }

        return ResponseEntity.ok(feeds);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Feed ova = feedService.findById(id).orElse(null);
        return ResponseEntity.ok(ova);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Feed current = feedService.findById(id).orElse(null);

        if(Objects.isNull(current)) {
            return ResponseEntity.badRequest().build();
        }

        feedService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("description") String description) {

        try {

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            UserDetails auth = (UserDetails) authentication.getPrincipal();

            Usuario currentUser = usuarioService.findByIdentityDocument(auth.getUsername());

            Feed feed = Feed.builder()
                    .usuario(currentUser)
                    .description(description)
                    .build();
            Feed current = feedService.save(feed);
            URI uri = URI.create(String.format("api/feed/%d", current.getId()));
            return ResponseEntity.created(uri).body(current);

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
            @RequestParam("description") String description) {


        Map<String, Object> response = new HashMap<>();

        try {

            Feed current = feedService.findById(id).orElse(null);

            if (Objects.isNull(current)) {
                throw new Exception("El Anuncio no existe");
            }

            current.setDescription(description);

            feedService.edit(current);

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
