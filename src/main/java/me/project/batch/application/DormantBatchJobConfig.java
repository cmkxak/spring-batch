package me.project.batch.application;

import me.project.batch.batchcore.Job;
import me.project.batch.application.DormantJobExecutionListener;
import me.project.batch.application.DormantBatchTasklet;
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
