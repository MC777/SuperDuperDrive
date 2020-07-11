package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication auth,
                             Model model) throws IOException {
        User user = this.userService.getUser(auth.getName());
        this.fileService.uploadFile(fileUpload, user.getUserid());
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        return "home";
    }

    @RequestMapping("/file/view/{fileid}")
    public ResponseEntity downloadFile(@PathVariable("fileid") Long fileId,
                                       Authentication auth,
                                       Model model) throws IOException {
        File file = this.fileService.getFileById(fileId);
        String contentType = file.getContentType();
        String fileName = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.getFileData());
    }

    @RequestMapping("/file-delete/{fileid}")
    public String deleteFile(@PathVariable("fileid") Long fileId,
                             Authentication auth,
                             Model model) throws IOException {
        fileService.deleteFile(fileId);
        User user = this.userService.getUser(auth.getName());
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        return "home";
    }
}
