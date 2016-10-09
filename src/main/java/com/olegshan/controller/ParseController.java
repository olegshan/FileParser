package com.olegshan.controller;

import com.olegshan.entity.Files;
import com.olegshan.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;

/**
 * Created by olegshan on 09.10.2016.
 */
@RestController
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class ParseController {

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("files") MultipartFile[] files) {

        if (files != null && files.length > 0) {
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
        } else {
            return "You didn't choose any files";
        }
        return "All files uploaded successfully";
    }
}
