package com.olegshan.service;

import com.olegshan.entity.Files;
import com.olegshan.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    @Autowired
    FileRepository fileRepository;

    public void save(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                File usualFile = new File(file.getName());
                file.transferTo(usualFile);
                Files fileToUpload = new Files();
                fileToUpload.setFile(usualFile);
                fileRepository.save(fileToUpload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
