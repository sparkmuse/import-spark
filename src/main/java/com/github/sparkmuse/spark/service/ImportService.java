package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.Deletion;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final ReaderService readerService;
    private final MapperService mapperService;
    private final WritterService writterService;

    public void process() {
        Dataset<Row> csv = readerService.read();
        Dataset<Deletion> deletions = mapperService.map(csv);
        writterService.write(deletions);
    }
}
