package me.project.batch.application;

import me.project.batch.batchcore.ItemReader;
import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemReader implements ItemReader<Customer> {

    private final CustomerRepository customerRepository;
    private int pageNo = 0;

    public DormantBatchItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer read() {

        final PageRequest pageRequest = PageRequest.of(pageNo, 1, Sort.by("id").ascending());
        Page<Customer> pages = customerRepository.findAll(pageRequest);

        final Customer customer;

        if (pages.isEmpty()) {
            return null;
        } else {
            pageNo++;
            return pages.getContent().get(0);
        }
    }
}
