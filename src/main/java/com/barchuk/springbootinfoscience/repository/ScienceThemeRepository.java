package com.barchuk.springbootinfoscience.repository;


import com.barchuk.springbootinfoscience.model.ScienceTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceThemeRepository extends JpaRepository<ScienceTheme, Long> {
}
