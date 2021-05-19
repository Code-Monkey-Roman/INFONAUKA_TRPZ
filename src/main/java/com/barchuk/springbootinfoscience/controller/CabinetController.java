package com.barchuk.springbootinfoscience.controller;

import com.barchuk.springbootinfoscience.dto.SpeakerPresentationDto;
import com.barchuk.springbootinfoscience.exceptions.ResourceNotFoundException;
import com.barchuk.springbootinfoscience.service.PresentationService;
import com.barchuk.springbootinfoscience.service.UserService;
import com.barchuk.springbootinfoscience.converters.Converter;
import com.barchuk.springbootinfoscience.model.Presentation;
import com.barchuk.springbootinfoscience.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class CabinetController {
    private final PresentationService presentationService;
    private final UserService userService;
    private final Converter converter;

    @Autowired
    public CabinetController(PresentationService presentationService, UserService userService,
                             Converter converter) {
        this.presentationService = presentationService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/speaker")
    @RolesAllowed({"SPEAKER"})
    public String speakerCabinet(Model model, HttpServletRequest request,
                                 @PageableDefault(size = 5) Pageable pageable) {
        Optional<User> user = userService.getCurrentUser(request);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException();
        }
        User speaker = user.get();
        Page<Presentation> presentations = presentationService.findAllBySpeaker(speaker.getId(), pageable);
        Page<SpeakerPresentationDto> page = presentations.map(converter::toSpeakerPresentationDto);
        model.addAttribute("page", page);
        return "/speaker-cabinet";
    }
}
