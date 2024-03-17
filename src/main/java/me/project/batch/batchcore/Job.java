package me.project.batch.batchcore;

import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 배치 실행 결과
 * 배치 실행 시간, 종료 시간 등의 기능을 지원한다.
 */

public class Job {

    private final Tasklet tasklet;
    private final JobExecutionListener jobExecutionListener;

    public Job(Tasklet tasklet, JobExecutionListener jobExecutionListener) {
        this.tasklet = tasklet;
        this.jobExecutionListener = jobExecutionListener;
    }

    @Builder
    public Job(ItemReader<?> itemReader,
               ItemProcessor<?, ?> itemProcessor,
               ItemWriter<?> itemWriter,
               JobExecutionListener jobExecutionListener) {
        this(new SimpleTasklet(itemReader, itemProcessor, itemWriter),
                jobExecutionListener);
    }


    public JobExecution execute() {
        JobExecution jobExecution = new JobExecution();
        jobExecution.setBatchStatus(BatchStatus.STARTING);
        jobExecutionListener.beforeJob(jobExecution);

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
