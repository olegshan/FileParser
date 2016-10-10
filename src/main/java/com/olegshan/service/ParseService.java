package com.olegshan.service;

import com.olegshan.entity.Files;
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

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private LinesRepository linesRepository;
    private HashMap<String, Integer> map = new HashMap<>();

    public Map<String, Integer> parseAll() {
        List<File> fileList = getAllFilesFromDb();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        fileList.forEach(file -> executorService.execute(() -> parseLines(file)));
        executorService.shutdown();
        linesRepository.save(new Lines(map));
        return map;
    }

    private List<File> getAllFilesFromDb() {
        List<File> list = new ArrayList<>();
        for (Files f : fileRepository.findAll()) {
            File file = new File(f.getName());
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(f.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
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
