package com.university.discretas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_SUBJECT_MATTER_ANSWER_CORRECT")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectMatterAnswerCorrect implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_matter_id")
    private SubjectMatter subjectMatter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_matter_column_id")
    private SubjectMatterColumn column;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_matter_row_id")
    private SubjectMatterRow row;

    public SubjectMatterAnswerCorrect(SubjectMatter subjectMatter, SubjectMatterColumn column, SubjectMatterRow row) {
        this.subjectMatter = subjectMatter;
        this.column = column;
        this.row = row;
    }

    @Override
    public String toString() {
        return "SubjectMatterAnswerCorrect{" +
                ", column=" + column +
                ", row=" + row +
                '}';
    }
}
