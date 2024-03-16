package me.project.batch.application;

import me.project.batch.batchcore.ItemWriter;
import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerRepository;
import me.project.batch.util.EmailProvier;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final EmailProvier emailProvier;

    public DormantBatchItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvier = new EmailProvier.Fake();
    }


    @Override
    public void write(Customer item) {
        customerRepository.save(item);
        emailProvier.send("휴먼 계정 처리.", item.getEmail());
    }
}
