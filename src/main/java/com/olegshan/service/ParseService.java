package com.olegshan.service;

import com.olegshan.entity.Lines;
import com.olegshan.entity.SourceFiles;
import com.olegshan.repository.FileRepository;
import com.olegshan.repository.LinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ParseService {

    private final FileRepository fileRepository;
    private final LinesRepository linesRepository;

    @Autowired
    public ParseService(FileRepository fileRepository, LinesRepository linesRepository) {
        this.fileRepository = fileRepository;
        this.linesRepository = linesRepository;
    }

    public Map<String, Integer> parseAll() {
        Map<String, Integer> map = new HashMap<>();
        List<File> fileList = getAllFilesFromDb();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        fileList.forEach(file -> executorService.submit(() -> parseLines(file, map)));
        executorService.shutdown();
        linesRepository.save(new Lines(map));
        return map;
    }

    private List<File> getAllFilesFromDb() {
        List<SourceFiles> dbList = fileRepository.findAll();
        if (dbList.isEmpty()) {
            throw new RuntimeException("The database is empty. You should upload some files before parsing");
        }
        List<File> list = new ArrayList<>();
        for (SourceFiles f : dbList) {
            File file = new File(f.getName());
            list.add(file);
        }
        return list;
    }

    private void parseLines(File file, Map<String, Integer> map) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                map.put(line, map.getOrDefault(line, 0) + 1);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
