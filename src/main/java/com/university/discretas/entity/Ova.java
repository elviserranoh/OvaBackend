package com.university.discretas.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_OVAS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ova implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

}
