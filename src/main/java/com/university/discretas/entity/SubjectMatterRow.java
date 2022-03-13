package com.university.discretas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_SUBJECT_MATTER_ROW")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectMatterRow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_matter_id")
    private SubjectMatter subjectMatter;

    public SubjectMatterRow(String content, SubjectMatter subjectMatter) {
        this.content = content;
        this.subjectMatter = subjectMatter;
    }

    @Override
    public String toString() {
        return "SubjectMatterRow{" +
                "content='" + content + '\'' +
                ", subjectMatter=" + subjectMatter +
                '}';
    }
}
