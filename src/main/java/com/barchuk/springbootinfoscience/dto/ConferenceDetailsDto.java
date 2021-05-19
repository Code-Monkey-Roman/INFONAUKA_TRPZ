package com.barchuk.springbootinfoscience.dto;

import lombok.Data;

@Data
public class ConferenceDetailsDto extends CreateConferenceDto {
    private int participantsCount;
    private int presentationsCount;
}
