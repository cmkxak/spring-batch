package me.project.batch;

import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerRepository;
import me.project.batch.domain.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DormantBatchJob {

    private final CustomerRepository customerRepository;
    private final EmailProvier emailProvier;

    public DormantBatchJob(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvier = new EmailProvier.Fake();
    }

    /**
     * 관심사가 너무 다양하고, 확장하기 어려운 포인트가 있다.
     * 뒤섞인 관심사
     *  1. 이해하기 어렵다.
     *  2. 수정하기 어렵다.
     * @return
     */
    public JobExecution execute() {

        int pageNo = 0;
        JobExecution jobExecution = new JobExecution();
        jobExecution.setBatchStatus(BatchStatus.STARTING); //job의 상태 제공
        jobExecution.setStartTime(LocalDateTime.now()); // 시작 시간 제공

        //1. 배치 실행
        try {
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

                //2. 휴먼계정 대상인 경우 상탯값 변경
                if (isDormant)
                    customer.setStatus(CustomerStatus.DORMANT);

                //3. 휴먼 계정으로 상탯값 변경
                customerRepository.save(customer);

                //4. 이메일 전송
                emailProvier.send("휴먼 계정 처리.", customer.getEmail());
            }
            jobExecution.setBatchStatus(BatchStatus.COMPLETED);
        } catch (Exception e) {
            jobExecution.setBatchStatus(BatchStatus.FAILED);
        }

        jobExecution.setEndTime(LocalDateTime.now());
        return jobExecution;
    }

}
