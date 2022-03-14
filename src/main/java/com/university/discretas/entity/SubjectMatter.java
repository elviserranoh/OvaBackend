package com.university.discretas.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "TB_SUBJECT_Matter")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectMatter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "indice")
    private Integer index;

    private String title;

    private String type;
    private String question;

    @OneToMany(mappedBy = "subjectMatter",
            cascade = CascadeType.ALL)
    private List<SubjectMatterRow> rows;

    @OneToMany(mappedBy = "subjectMatter",
            cascade = CascadeType.ALL)
    private List<SubjectMatterColumn> columns;

    @OneToMany(mappedBy = "subjectMatter",
            cascade = CascadeType.ALL)
    private List<SubjectMatterAnswerCorrect> answerCorrects;

    private String image;

}
