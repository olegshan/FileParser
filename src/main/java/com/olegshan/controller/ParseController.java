package com.olegshan.controller;

import com.olegshan.service.ParseService;
import com.olegshan.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("files") MultipartFile[] files) {

        if (files == null || files.length == 0) {
            return "You didn't choose any files";
        }
        for (MultipartFile file : files) {
            if (!file.getContentType().equals("text/plain")) {
                return "Please upload only .txt files";
            }
        }
        uploadService.save(files);
        return "All files uploaded successfully";
    }

    @RequestMapping(value = "/parse", method = RequestMethod.GET)
    public Map<String, Integer> parse() {
        return parseService.parse();
    }
}
