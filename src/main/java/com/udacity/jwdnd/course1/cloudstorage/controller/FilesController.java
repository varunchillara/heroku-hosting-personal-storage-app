package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.customexceptions.RepeatedFileException;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class FilesController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private FilesService filesService;

    public boolean isDuplicateFiles(String fileName, Integer userId) {
        List<Files> userFiles = filesService.getUserFiles(userId);

        for(Files file:userFiles) {
            if(fileName.equals(file.getFileName())) {
                return true;
            }
        }
        return false;
    }


    @PostMapping("/files")
    public String uploadToDB(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model, RedirectAttributes redirectAttributes)
    throws IOException {

        try {

            if (fileUpload.isEmpty()) {
                redirectAttributes.addFlashAttribute("isEmpty", "no file to upload. choose a file before pressing upload");
                return "redirect:/home";
            }

            Files file = new Files();
            file.setFileData(fileUpload.getBytes());

            String username = (String) authentication.getPrincipal();
            Users user = usersService.getUser(username);

            if(isDuplicateFiles(fileUpload.getOriginalFilename(), user.getUserId()) == true) {
                throw new RepeatedFileException();
            }

            file.setUserId(user.getUserId());
            file.setFileSize(String.valueOf(fileUpload.getSize()));
            file.setContentType(fileUpload.getContentType());
            file.setFileName(fileUpload.getOriginalFilename());

            filesService.uploadFile(file);

            redirectAttributes.addFlashAttribute("fileUploaded", "file successfully uploaded!");

        }catch (RepeatedFileException f) {
            redirectAttributes.addFlashAttribute("duplicateFile", "file already exists!");
        }
        return "redirect:/home";
    }

    @GetMapping("/files/download/{id}")
    public ResponseEntity downloadFile(@PathVariable("id") Integer id) {

        Files file = filesService.getFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        filesService.deleteFile(id);
        redirectAttributes.addFlashAttribute("fileDeleted", "file successfully deleted!");
        return "redirect:/home";
    }
}