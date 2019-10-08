package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SparkRunner implements CommandLineRunner {

    private final ImportService importService;

    @Override
    public void run(String... args) throws Exception {
        importService.process();
    }
}
