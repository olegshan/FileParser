package com.olegshan.service;

import com.olegshan.entity.SourceFiles;
import com.olegshan.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class UploadService {
    private final FileRepository fileRepository;

    @Autowired
    public UploadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                String name = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                SourceFiles fileToUpload = new SourceFiles(name, Base64.getEncoder().encodeToString(bytes));
                fileRepository.save(fileToUpload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
