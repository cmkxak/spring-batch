package me.project.batch.application;

import me.project.batch.batchcore.ItemProcessor;
import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        boolean isDormant = LocalDateTime.now().minusDays(365)
                .isAfter(item.getLoginAt());
        if (isDormant) {
            item.setStatus(CustomerStatus.DORMANT);
            return item;
        } else {
            return null;
        }
    }
}
