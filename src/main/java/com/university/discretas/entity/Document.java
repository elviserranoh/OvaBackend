package com.university.discretas.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_DOCUMENTS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String autor;
    private Integer year;
    private String presentation;
    private String url;

    @ManyToOne
    @JoinColumn(name = "ova_id")
    private Ova ova;

    private String image;
    private String document;
}
