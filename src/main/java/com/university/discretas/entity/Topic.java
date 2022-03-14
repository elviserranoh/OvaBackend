package com.university.discretas.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "TB_TOPICS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indice")
    private Integer index;
    private String title;
    private String objetive;

    @ManyToOne
    @JoinColumn(name = "ova_id")
    private Ova ova;

    @OneToMany(mappedBy = "topic",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ContentTopic> contents;

    private String image;

}
