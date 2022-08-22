package com.learningstuff.springbatch.jobs;

import com.learningstuff.springbatch.models.Sample;
import com.learningstuff.springbatch.repositories.SampleRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SampleDataCSVToDBJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final SampleRepository sampleRepository;

    @Bean
    public FlatFileItemReader<Sample> reader() {

        FlatFileItemReader<Sample> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/static/SampleCSVFile_2kb.csv"));
        itemReader.setName("sampleDataReaderFromCSV");
        itemReader.setLineMapper(lineItemMapper());

        return itemReader;
    }

    private LineMapper<Sample> lineItemMapper() {
        DefaultLineMapper<Sample> mapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "descriptions", "name", "value");

        BeanWrapperFieldSetMapper<Sample> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Sample.class);

        mapper.setLineTokenizer(lineTokenizer);
        mapper.setFieldSetMapper(fieldSetMapper);

        return mapper;
    }

}
