package me.project.batch.config;

import me.project.batch.job.Job;
import me.project.batch.listener.DormantJobExecutionListener;
import me.project.batch.tasklet.DormantBatchTasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchJobConfig {

    @Bean
    public Job dormantBatchJob(
            DormantBatchTasklet dormantBatchTasklet,
            DormantJobExecutionListener dormantJobExecutionListener
    ) {
        return new Job(
                dormantBatchTasklet, dormantJobExecutionListener
        );
    }


}
