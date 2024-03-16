package me.project.batch.listener;

import me.project.batch.job.JobExecution;

public interface JobExecutionListener {

    void beforeJob(JobExecution jobExecution);

    void afterJob(JobExecution jobExecution);
}
