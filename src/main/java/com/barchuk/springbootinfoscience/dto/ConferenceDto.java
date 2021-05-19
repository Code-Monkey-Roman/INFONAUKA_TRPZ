package com.barchuk.springbootinfoscience.dto;

import lombok.*;
import java.util.List;

@Data
public class ConferenceDto extends CreateConferenceDto {
    private List<PresentationDto> presentations;
    public boolean registered;
}
