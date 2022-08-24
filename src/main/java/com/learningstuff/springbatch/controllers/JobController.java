package com.learningstuff.springbatch.controllers;

import com.learningstuff.springbatch.listerners.CustomJobListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Slf4j
@RestController
@RequestMapping(value = "/jobs")
@AllArgsConstructor
public class JobController {

    private final ApplicationContext context;
    private final JobLauncher jobLauncher;
    private final CustomJobListener listener;

    @Qualifier(value = "sampleCSVImportConfig")
    private final Job sampleCSVImportJob;

    @PostMapping(value = "/sample-csv-import-job")
    public ResponseEntity<?> sampleCSVImportJob() {

        JobParameters parameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(sampleCSVImportJob, parameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Job Successfully launch.");
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<?> sampleCSVImportJobByName(@PathVariable(value = "name") String name) {

        JobParameters parameters = new JobParametersBuilder()
                .addString("jobName", name)
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        Job jobBean = getJobBean(name);

        if (jobBean != null) {
            AbstractJob job = (AbstractJob) jobBean;
            job.registerJobExecutionListener(listener); // override if exist
            try {
                JobExecution jobExecution = jobLauncher.run(job, parameters);
                log.info("Job [" + job.getName() + "] execution finished with status: [" + jobExecution.getExitStatus().getExitCode() + "]");
            } catch (Exception e) {
                throw new RuntimeException("There was an error configuring batch job: " + e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Job Successfully launch.");
    }

    private Job getJobBean(String jobName) {
        try {
            return context.getBean(jobName, Job.class);
        } catch (NoSuchBeanDefinitionException e) {
            log.info("There is no such job with name: [" + jobName + "]");
            return null;
        }
    }


}
