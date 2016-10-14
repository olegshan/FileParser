package com.olegshan.controller;

import com.olegshan.service.ParseService;
import com.olegshan.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * This REST application receives files and parses them in parallel way.
 * The result of parsing is map with lines as the keys and number of their occurrences as the values.
 * Both source files and maps with result of parsing are stored into the database.
 */
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
            if (file.getSize() > (1024 * 1024 * 30)) {
                throw new IllegalArgumentException("Your file is too big. Please upload files up to 30 MB");
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
