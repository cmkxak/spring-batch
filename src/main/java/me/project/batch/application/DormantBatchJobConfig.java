package me.project.batch.application;

import me.project.batch.batchcore.ItemReader;
import me.project.batch.batchcore.Job;
import me.project.batch.batchcore.Job.JobBuilder;
import me.project.batch.batchcore.SimpleTasklet;
import me.project.batch.domain.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchJobConfig {

    @Bean
    public Job dormantBatchJob(
            DormantBatchItemReader itemReader,
            DormantBatchItemProcessor itemProcessor,
            DormantBatchItemWriter itemWriter,
            DormantJobExecutionListener dormantJobExecutionListener
    ) {

        return Job.builder()
                .itemReader(itemReader)
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .jobExecutionListener(dormantJobExecutionListener)
                .build();

    }


}
