package com.university.discretas.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_FEED")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

}