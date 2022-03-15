package com.university.discretas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebateRequest implements Serializable {

    private Long id;
    private String title;
    private String description;
}
