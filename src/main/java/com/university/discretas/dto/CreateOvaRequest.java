package com.university.discretas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOvaRequest implements Serializable {

    private String name;
    private String description;
    private MultipartFile image;
}
