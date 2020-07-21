package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public File uploadFile(MultipartFile multipartFile, Long userId) throws IOException {
        File newFile = new File(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize(),
                multipartFile.getBytes(),
                userId
        );

        try {
            fileMapper.save(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public List<File> getAllFiles(Long userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    public File getFileById(Long fileId) throws IOException {
        return fileMapper.findById(fileId);
    }

    public void deleteFile(Long fileId) throws IOException {
        try {
            fileMapper.deleteById(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFileNameAvailable(MultipartFile multipartFile, Long userid) {
        Boolean isFileNameAvailable = true;
        List <File> files = fileMapper.findFilesByUserId(userid);
        for (int i = 0; i < files.size(); i++){
            File currFile = files.get(i);
            if (currFile.getFileName().equals(multipartFile.getOriginalFilename())) {
                isFileNameAvailable = false;
                break;
            }
        }
        return isFileNameAvailable;
    }
}
