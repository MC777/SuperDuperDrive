package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class CredentialController {
    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;

    public CredentialController(NoteService noteService, UserService userService,
                                FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential-upload")
    public String uploadCredential(@RequestParam("credentialId") Long credentialId,
                                   @RequestParam("url") String credentialUrl,
                                   @RequestParam("username") String credentialUsername,
                                   @RequestParam("password") String credentialPassword,
                                   Authentication auth,
                                   Model model) throws IOException {
        User user = this.userService.getUser(auth.getName());
        if (credentialId == null) {
            this.credentialService.uploadCredential(credentialUrl, credentialUsername, credentialPassword, user.getUserid());
        } else {
            this.credentialService.updateCredential(credentialId, credentialUrl, credentialUsername, credentialPassword, user.getUserid());
        }
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        model.addAttribute("notes", this.noteService.getAllByUserId(user.getUserid()));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }

    @RequestMapping("/credential-delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Long credentialId,
                                   Authentication auth,
                                   Model model) throws IOException {
        User user = this.userService.getUser(auth.getName());
        this.credentialService.deleteCredential(credentialId);
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        model.addAttribute("notes", this.noteService.getAllByUserId(user.getUserid()));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }
}
