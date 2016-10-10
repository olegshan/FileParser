package com.olegshan.controller;

import com.olegshan.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;

@RestController
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class ParseController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("files") MultipartFile[] files) {

        if (files == null || files.length == 0) {
            return "You didn't choose any files";
        }
        uploadService.save(files);
        return "All files uploaded successfully";
    }
}
