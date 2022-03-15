package com.university.discretas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebateCommentRequest implements Serializable {

    private Long id;
    private String description;
}