package com.olegshan.service;

import com.olegshan.entity.SourceFiles;
import com.olegshan.entity.Lines;
import com.olegshan.repository.FileRepository;
import com.olegshan.repository.LinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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
    private Map<String, Integer> map;

    @Autowired
    public ParseService(FileRepository fileRepository, LinesRepository linesRepository) {
        this.fileRepository = fileRepository;
        this.linesRepository = linesRepository;
    }

    public Map<String, Integer> parseAll() {
        List<File> fileList = getAllFilesFromDb();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        fileList.forEach(file -> executorService.execute(() -> parseLines(file)));
        executorService.shutdown();
        linesRepository.save(new Lines(map));
        return map;
    }

    private List<File> getAllFilesFromDb() {
        map = new HashMap<>();
        List<SourceFiles> dbList = fileRepository.findAll();
        if (dbList.isEmpty()) {
            throw new RuntimeException("The database is empty. You should upload some files before parsing");
        }
        List<File> list = new ArrayList<>();
        for (SourceFiles f : dbList) {
            File file = new File(f.getName());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(f.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            list.add(file);
        }
        return list;
    }

    private void parseLines(File file) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                if (map.containsKey(line)) {
                    map.put(line, map.get(line) + 1);
                } else {
                    map.put(line, 1);
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
