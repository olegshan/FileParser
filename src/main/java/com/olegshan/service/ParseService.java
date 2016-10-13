package com.olegshan.service;

import com.olegshan.entity.Lines;
import com.olegshan.entity.SourceFiles;
import com.olegshan.repository.FileRepository;
import com.olegshan.repository.LinesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static int MAX_THREADS_COUNT = 3;
    private final FileRepository fileRepository;
    private final LinesRepository linesRepository;
    private final Logger logger = LoggerFactory.getLogger(ParseService.class);

    @Autowired
    public ParseService(FileRepository fileRepository, LinesRepository linesRepository) {
        this.fileRepository = fileRepository;
        this.linesRepository = linesRepository;
    }

    public Map<String, Integer> parse() {
        logger.info("Parsing started...");
        Map<String, Integer> map = new HashMap<>();
        List<File> fileList = getAllFilesFromDb();
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS_COUNT);
        fileList.forEach(file -> executorService.execute(() -> parseLines(file, map)));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("Error while shutting down the Executor service: ", e);
            }
        }
        logger.debug("Saving parsing results into database...");
        linesRepository.save(new Lines(map));
        logger.debug("Parsing results saved.");
        return map;
    }

    private List<File> getAllFilesFromDb() {
        logger.debug("Getting files from database...");
        List<SourceFiles> dbList = fileRepository.findAll();
        if (dbList.isEmpty()) {
            throw new RuntimeException("The database is empty. You should upload some files before parsing");
        }
        List<File> list = new ArrayList<>();
        for (SourceFiles f : dbList) {
            File file = new File(f.getName());
            list.add(file);
        }
        logger.info("All files received.");
        return list;
    }

    private void parseLines(File file, Map<String, Integer> map) {
        logger.debug("Parsing file {}", file.getName());
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                map.put(line, map.getOrDefault(line, 0) + 1);
            }
        } catch (java.io.IOException e) {
            logger.error("Reading of lines failed: ", e);
        }
    }
}
