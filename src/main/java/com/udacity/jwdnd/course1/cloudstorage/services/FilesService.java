package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesService {

    FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public void uploadFile(Files file) {
        filesMapper.insertIntoFiles(file);
    }

    public void deleteFile(Integer fileId) {
        filesMapper.deleteFile(fileId);
    }

    public List<Files> getUserFiles(Integer userId) {
        return filesMapper.getFilesByUserId(userId);
    }

    public Files getFile(Integer fileId) {
        return filesMapper.getFile(fileId);
    }
}
