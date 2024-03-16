package me.project.batch.application;

import me.project.batch.batchcore.Tasklet;
import me.project.batch.util.EmailProvier;
import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerRepository;
import me.project.batch.domain.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DormantBatchTasklet implements Tasklet {

    private final CustomerRepository customerRepository;
    private final EmailProvier emailProvier;

    public DormantBatchTasklet(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvier = new EmailProvier.Fake();
    }

    @Override
    public void execute() {

        int pageNo = 0;

        while (true) {
            final PageRequest pageRequest = PageRequest.of(pageNo, 1, Sort.by("id").ascending());
            Page<Customer> pages = customerRepository.findAll(pageRequest);

            final Customer customer;

            if (pages.isEmpty()) {
                break;
            } else {
                pageNo++;
                customer = pages.getContent().get(0);
            }

            boolean isDormant = LocalDateTime.now().minusDays(365)
                    .isAfter(customer.getLoginAt());

            if (isDormant)
                customer.setStatus(CustomerStatus.DORMANT);

            customerRepository.save(customer);

            emailProvier.send("휴먼 계정 처리.", customer.getEmail());
        }

         emailProvier.send("배치 처리 작업이 모두 수행되었습니다.", "admin@dev.com");
    }
}
