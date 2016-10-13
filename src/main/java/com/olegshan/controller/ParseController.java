package com.olegshan.controller;

import com.olegshan.service.ParseService;
import com.olegshan.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ParseController {

    private final UploadService uploadService;
    private final ParseService parseService;

    @Autowired
    public ParseController(UploadService uploadService, ParseService parseService) {
        this.uploadService = uploadService;
        this.parseService = parseService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("files") MultipartFile[] files) {

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("You didn't choose any files");
        }
        for (MultipartFile file : files) {
            if (!file.getContentType().equals("text/plain")) {
                throw new IllegalArgumentException("Please upload .txt files only");
            }
        }
        uploadService.save(files);
        return "All files uploaded successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/parse", method = RequestMethod.GET)
    public Map<String, Integer> parse() {
        return parseService.parse();
    }
}
