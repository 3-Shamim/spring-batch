package com.learningstuff.springbatch.jobs.sample_csv_to_db;

import com.learningstuff.springbatch.models.Sample;
import com.learningstuff.springbatch.repositories.SampleRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
@EnableBatchProcessing
@AllArgsConstructor
public class SampleCSVImportJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final SampleRepository sampleRepository;

    @Bean
    public Job runJob() {
        return jobBuilderFactory
                .get("sampleCSVImportJob")
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("sampleCSVImportStep")
                .<Sample, Sample>chunk(10)
                .reader(reader())
                .processor(process())
                .writer(writer())
                .build();

    }

    @Bean
    public FlatFileItemReader<Sample> reader() {

        FlatFileItemReader<Sample> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/static/SampleCSVFile_2kb.csv"));
        itemReader.setName("sampleDataReaderFromCSV");
        itemReader.setLineMapper(lineItemMapper());

        return itemReader;
    }

    @Bean
    public SampleCSVImportProcess process() {
        return new SampleCSVImportProcess();
    }

    @Bean
    public RepositoryItemWriter<Sample> writer() {

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
