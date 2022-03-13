package com.university.discretas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AnswerCorrectRequest implements Serializable {
    private String row;
    private String column;

    @Override
    public String toString() {
        return "AnswerCorrectRequest{" +
                "row='" + row + '\'' +
                ", column='" + column + '\'' +
                '}';
    }
}
