package me.project.batch.job;

import me.project.batch.tasklet.Tasklet;
import me.project.batch.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Job {

    private final Tasklet tasklet;
    private final JobExecutionListener jobExecutionListener;

    public Job(Tasklet tasklet, JobExecutionListener jobExecutionListener) {
        this.tasklet = tasklet;
        this.jobExecutionListener = jobExecutionListener;
    }

    public JobExecution execute() {
        JobExecution jobExecution = new JobExecution();
        jobExecution.setBatchStatus(BatchStatus.STARTING);
        jobExecutionListener.beforeJob(jobExecution);

        //1. 배치 실행
        try {
            tasklet.execute();
        } catch (Exception e) {
            jobExecution.setBatchStatus(BatchStatus.FAILED);
        }

        jobExecution.setBatchStatus(BatchStatus.COMPLETED);
        jobExecution.setEndTime(LocalDateTime.now());
        jobExecutionListener.afterJob(jobExecution);
        return jobExecution;
    }

}
