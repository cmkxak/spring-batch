package me.project.batch.batchcore;

import me.project.batch.batchcore.JobExecution;

public interface JobExecutionListener {

    void beforeJob(JobExecution jobExecution);

    void afterJob(JobExecution jobExecution);
}
