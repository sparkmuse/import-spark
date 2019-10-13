package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.repository.ImportJobRepository;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparkImportService {

    private final ReaderService readerService;
    private final MapperService mapperService;
    private final WritterService writterService;
    private final ImportJobRepository importJobRepository;

    @Async
    public void process() {
        Dataset<Row> csv = readerService.read();
        Dataset<Deletion> deletions = mapperService.map(csv);
        writterService.write(deletions);
        importJobRepository.deleteAll();
    }
}
