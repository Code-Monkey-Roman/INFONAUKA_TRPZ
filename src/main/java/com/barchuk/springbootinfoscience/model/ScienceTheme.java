package com.barchuk.springbootinfoscience.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "science_theme")
public class ScienceTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theme", nullable = false)
    @Size(min = 2, max = 30, message = "Title should be between 2 and 30 characters")
    private String title;

//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToMany
//    @JoinColumn(name = "science_theme_id")
//    private Set<Databank> databankIds = new HashSet<>();
}
