package com.olegshan.service;

import com.olegshan.entity.Files;
import com.olegshan.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParseService {

    @Autowired
    FileRepository fileRepository;

    public void parseAll() {
        List<File> fileList = getAllFilesFromDb();

    }

    private List<File> getAllFilesFromDb() {
        return fileRepository.findAll()
                .stream()
                .map(Files::getFile)
                .collect(Collectors.toList());
    }
}
