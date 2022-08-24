package com.learningstuff.springbatch.jobs.sample_db_to_file;

import com.learningstuff.springbatch.models.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Slf4j
public class LoggingItemWriter implements ItemWriter<Sample> {

    @Override
    public void write(List<? extends Sample> list) throws Exception {
        log.info(">>>> Writing samples: {}", list);
    }

}