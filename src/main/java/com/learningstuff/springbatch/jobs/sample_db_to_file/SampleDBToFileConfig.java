package com.learningstuff.springbatch.jobs.sample_db_to_file;

import com.learningstuff.springbatch.models.Sample;
import com.learningstuff.springbatch.repositories.SampleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Slf4j
@Configuration
@AllArgsConstructor
public class SampleDBToFileConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SampleRepository sampleRepository;

    @Bean
    public Job sampleDBToFileJob() {
        return jobBuilderFactory
                .get("sample-db-to-file-job")
//                .incrementer(new RunIdIncrementer())
                .flow(sampleDBToFileStep())
                .next(sampleDBToRestStep())
                .end()
                .build();
    }

    @Bean
    public Step sampleDBToFileStep() {
        return stepBuilderFactory
                .get("sample-db-to-file-step")
                .<Sample, Sample>chunk(10)
                .reader(sampleDBToFileReader())
                .processor(sampleDBToFileProcess())
                .writer(sampleDBToFileWriter())
                .build();

    }

    @Bean
    public Step sampleDBToRestStep() {
        return stepBuilderFactory
                .get("sample-db-to-file-step")
                .<Sample, Sample>chunk(10)
                .reader(sampleDBToFileReader())
                .processor(sampleDBToFileProcess())
                .writer(sampleDBToRestWriter())
                .build();

    }

    @Bean
    public RepositoryItemReader<Sample> sampleDBToFileReader() {
        RepositoryItemReader<Sample> reader = new RepositoryItemReader<>();
        reader.setName("sample-db-to-file-reader");
        reader.setRepository(sampleRepository);
        reader.setMethodName("findAll");

        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("name", Sort.Direction.ASC);
        reader.setSort(map);

        return reader;
    }

    @Bean
    public SampleDBToFileProcess sampleDBToFileProcess() {
        return new SampleDBToFileProcess();
    }

    @Bean
    public ItemWriter<Sample> sampleDBToFileWriter() {

        FlatFileItemWriter<Sample> writer = new FlatFileItemWriter<>();
        writer.setName("sample-db-to-file-writer");
        writer.setResource(new FileSystemResource("src/main/resources/static/my-file.csv"));

        writer.setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                setNames(new String[]{"id", "descriptions", "name", "value"});
            }});
        }});
        writer.setHeaderCallback(w -> w.write("id,descriptions,name,value"));
        return writer;
    }

    @Bean
    public ItemWriter<Sample> sampleDBToRestWriter() {
//        return new LoggingItemWriter();

        return new ItemWriter<Sample>() {
            @Override
            public void write(List<? extends Sample> list) throws Exception {

                // Need to write rest call with rest template

                log.info("Sample list >> {}", list);
            }
        };

    }


}
