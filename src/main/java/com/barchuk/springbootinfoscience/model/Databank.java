package com.barchuk.springbootinfoscience.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "databanks")
public class Databank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
