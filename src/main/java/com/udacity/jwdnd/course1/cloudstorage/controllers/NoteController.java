package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping(value = "/add-note")
    public String addNote(Note note, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User activeUser = userService.getUser(authentication.getName());
            Long userid = activeUser.getUserid();
            Long noteid = note.getNoteId();

            if (noteid == null) {
                noteService.create(note, userid);
            } else {
                noteService.update(note, userid);
            }

            model.addAttribute("success", true);
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", true);
            return "result";
        }
    }


    @GetMapping(value = "/delete-note/{id}")
    public String deleteNote(@PathVariable("id") Long id, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User activeUser = userService.getUser(authentication.getName());
            Long userid = activeUser.getUserid();
            if (noteService.isNoteExists(id, userid)) {
                noteService.delete(id, userid);
                model.addAttribute("success", true);
                return "result";
            }
            model.addAttribute("error", true);
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", true);
            return "result";
        }
    }
}