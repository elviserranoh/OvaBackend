package com.university.discretas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
    private String email;
    private String content;
    private String subject;
}
