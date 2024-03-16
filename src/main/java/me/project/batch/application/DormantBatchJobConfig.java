package me.project.batch.application;

import me.project.batch.batchcore.Job;
import me.project.batch.batchcore.SimpleTasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchJobConfig {

    @Bean
    public Job dormantBatchJob(
            SimpleTasklet dormantBatchTasklet,
            DormantJobExecutionListener dormantJobExecutionListener
    ) {
        return new Job(
                dormantBatchTasklet, dormantJobExecutionListener
        );
    }


}
