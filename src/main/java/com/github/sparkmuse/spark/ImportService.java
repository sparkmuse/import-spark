package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.service.MapperService;
import com.github.sparkmuse.spark.service.ReaderService;
import com.github.sparkmuse.spark.service.WritterService;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@RequiredArgsConstructor
public class ImportService {

    private final SparkSession sparkSession;
    private final ReaderService readerService;
    private final MapperService mapperService;
    private final WritterService writterService;

    public void process() {
        Dataset<Row> csv = readerService.read(sparkSession);
        Dataset<DeletionClean> deletions = mapperService.map(csv);
        writterService.write(deletions);
    }
}
