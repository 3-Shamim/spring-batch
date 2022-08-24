package com.learningstuff.springbatch.jobs.sample_csv_to_db;

import com.learningstuff.springbatch.listerners.*;
import com.learningstuff.springbatch.models.Sample;
import com.learningstuff.springbatch.repositories.SampleRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
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
//@EnableBatchProcessing
@AllArgsConstructor
public class SampleCSVImportConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final SampleRepository sampleRepository;

    private final StepItemReadListener stepItemReadListener;

    @Bean
    public Job sampleCSVImportJob() {
        return jobBuilderFactory
                .get("sample-csv-import-job")
                .incrementer(new RunIdIncrementer())
                .flow(sampleCSVImportStep())
                .end()
//                .listener(new CustomJobListener())
                .build();
    }

    @Bean
    public Step sampleCSVImportStep() {

        SimpleStepBuilder<Sample, Sample> builder = stepBuilderFactory
                .get("sample-csv-import-step")
                .<Sample, Sample>chunk(10)
                .reader(sampleCSVImportReader())
                .processor(sampleCSVImportProcess())
                .writer(sampleCSVImportWriter());

                builder.listener(new CustomStepListener())
                .listener(new CustomChunkListener())
                .listener(stepItemReadListener);
                // ..
                // .. can be use all listener
                return builder.build();

    }

    @Bean
    public FlatFileItemReader<Sample> sampleCSVImportReader() {

        FlatFileItemReader<Sample> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/static/SampleCSVFile_11kb.csv"));
        itemReader.setName("sample-data-reader-from-csv");
        itemReader.setLineMapper(lineItemMapper());

        return itemReader;
    }

    @Bean
    public SampleCSVImportProcess sampleCSVImportProcess() {
        return new SampleCSVImportProcess();
    }

    @Bean
    public RepositoryItemWriter<Sample> sampleCSVImportWriter() {

        RepositoryItemWriter<Sample> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(sampleRepository);
        itemWriter.setMethodName("save");

        return itemWriter;
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
