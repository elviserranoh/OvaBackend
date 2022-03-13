package com.university.discretas.controller;

import com.university.discretas.dto.EmailDTO;
import com.university.discretas.entity.Usuario;
import com.university.discretas.service.EmailService;
import com.university.discretas.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/email")
@RequiredArgsConstructor
public class EmailController {


    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @GetMapping("/forgot-password/{identityDocument}")
    public ResponseEntity<?> forgotPassword(@PathVariable("identityDocument") String identityDocument) {

        Usuario usuario = usuarioService.findByIdentityDocument(identityDocument);


        if (Objects.nonNull(usuario)) {


            try {
                usuario.setPassword(passwordEncoder.encode(usuario.getIdentityDocument()));

                usuarioService.update(usuario);

                EmailDTO emailDTO = EmailDTO.builder()
                        .email(usuario.getEmail())
                        .subject("Estructuras Discretas - Recuperar Contraseña")
                        .subject(String.format("Su contraseña es: %s", usuario.getPassword()))
                        .build();

                emailService.sendEmail(emailDTO);

                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ResponseEntity.badRequest().build();

    }

}
