package com.barchuk.springbootinfoscience.controller;

import com.barchuk.springbootinfoscience.dto.ConferenceDetailsDto;
import com.barchuk.springbootinfoscience.dto.ConferenceDto;
import com.barchuk.springbootinfoscience.dto.ConferenceSearchFilters;
import com.barchuk.springbootinfoscience.exceptions.ResourceNotFoundException;
import com.barchuk.springbootinfoscience.service.ConferenceService;
import com.barchuk.springbootinfoscience.service.UserService;
import com.barchuk.springbootinfoscience.converters.Converter;
import com.barchuk.springbootinfoscience.dto.CreateConferenceDto;
import com.barchuk.springbootinfoscience.model.Conference;
import com.barchuk.springbootinfoscience.model.User;
import com.barchuk.springbootinfoscience.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final UserService userService;
    private final Converter converter;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, UserService userService,
                                Converter converter) {
        this.conferenceService = conferenceService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/conferences")
    public String findAll(ConferenceSearchFilters filters, Model model, @PageableDefault(size = 9)
            Pageable pageable) throws Exception {

        if (filters == null) {
            filters = new ConferenceSearchFilters();
        }

        Page<Conference> conferences = conferenceService.findAll(filters, pageable);
        Page<ConferenceDetailsDto> page = conferences.map(converter::toConferenceDetailsDto);

        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return "/conference-list";
    }

    @GetMapping("/conference/{id}")
    public String findOne(HttpServletRequest request, @PathVariable Long id, Model model) {
        Conference conference = conferenceService.findById(id);

        if (conference == null) {
            throw new ResourceNotFoundException();
        }
        Optional<User> user = userService.getCurrentUser(request);
        if (user.get().getRole() == UserRole.PARTICIPANT) {
            conference.getPresentations().removeIf(p -> !p.isPresentationApproved());
        }

        ConferenceDto dto = converter.toConferenceDto(conference, user.get());
        model.addAttribute("conference", dto);
        return "/conference-view";
    }

    @GetMapping("/conference/create")
    @RolesAllowed({"MODERATOR"})
    public String createConferenceForm(Model model) {
        CreateConferenceDto dto = new CreateConferenceDto();
        model.addAttribute("conference", dto);
        return "/conference-create";
    }

    @PostMapping("/conference/create")
    @RolesAllowed({"MODERATOR"})
    public String createConference(CreateConferenceDto conferenceDto) {
        Conference conference = converter.toConferenceModel(conferenceDto);
        conferenceService.saveConference(conference);
        return "redirect:/conference/" + conference.getId();
    }

    @PostMapping("/conference/{id}/delete")
    @RolesAllowed({"MODERATOR"})
    public String deleteConferenceForm(@PathVariable Long id) {
        conferenceService.deleteConference(id);
        return "redirect:/conferences";
    }

    @GetMapping("/conference/{id}/edit")
    @RolesAllowed({"MODERATOR"})
    public String editConference(HttpServletRequest request, @PathVariable Long id, Model model) {
        Conference conference = conferenceService.findById(id);
        if (conference == null) {
            throw new ResourceNotFoundException();
        }
        Optional<User> user = userService.getCurrentUser(request);
        ConferenceDto dto = converter.toConferenceDto(conference, user.get());
        model.addAttribute("conference", dto);
        return "/conference-edit";
    }

    @PostMapping("/conference/{id}/edit")
    @RolesAllowed({"MODERATOR"})
    public String editAndSaveConference(ConferenceDto conferenceDto, @PathVariable Long id) {
        Conference conference = converter.toConferenceModel(conferenceDto);
        conference.setId(id);
        conferenceService.saveConference(conference);
        return "redirect:/conference/" + id;
    }

    @PostMapping("/conference/{id}/signup")
    @RolesAllowed({"PARTICIPANT"})
    public String signupToConference(HttpServletRequest request, @PathVariable Long id) {
        Conference conference = conferenceService.findById(id);
        Optional<User> user = userService.getCurrentUser(request);
        if (user.isPresent()) {
            boolean alreadyRegistered = conference.getParticipants()
                    .stream()
                    .anyMatch(u -> u.getEmail().equals(user.get().getEmail()));

            if (!alreadyRegistered) {
                conference.getParticipants().add(user.get());
                conferenceService.saveConference(conference);
            }
        }
        return "redirect:/conference/" + conference.getId();
    }

    @PostMapping("/conference/{id}/signout")
    @RolesAllowed({"PARTICIPANT"})
    public String signOutFromConference(HttpServletRequest request, @PathVariable Long id) {
        Conference conference = conferenceService.findById(id);
        Principal principal = request.getUserPrincipal();
        conference.getParticipants().removeIf(u -> u.getEmail().equals(principal.getName()));
        conferenceService.saveConference(conference);
        return "redirect:/conference/" + conference.getId();
    }

    @GetMapping("/")
    public String goToConferenceList() {
        return "redirect:/conferences";
    }

}
