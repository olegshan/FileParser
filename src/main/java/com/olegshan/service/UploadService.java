package com.olegshan.service;

import com.olegshan.entity.Files;
import com.olegshan.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                String name = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Files fileToUpload = new Files(name, bytes);
                fileRepository.save(fileToUpload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
