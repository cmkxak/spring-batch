package me.project.batch;

import me.project.batch.domain.Customer;
import me.project.batch.domain.CustomerRepository;
import me.project.batch.domain.CustomerStatus;
import me.project.batch.job.BatchStatus;
import me.project.batch.job.Job;
import me.project.batch.job.JobExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DormantBatchJobTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Job dormantBatchJob;

    @Transactional
    @Test
    @DisplayName("로그인 시간이 1년을 경과한 고객이 3명이고, 일년 이내에 로그인한 고객이 5명이면 3명의 고객이 휴먼전환대상이다.")
    void test() {

        //given
        saveCustomer(370);
        saveCustomer(370);
        saveCustomer(370);
        saveCustomer(2);
        saveCustomer(3);
        saveCustomer(70);
        saveCustomer(37);
        saveCustomer(30);

        //when
        JobExecution execution = dormantBatchJob.execute();

        //then
        final long count = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == CustomerStatus.DORMANT)
                .count();

        assertThat(count).isEqualTo(3);
        assertThat(execution.getBatchStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Transactional
    @Test
    @DisplayName("고객이 열명 있지만 모두 휴먼 전환 대상이면 휴먼전환 대상은 10명이다.")
    void test2() {

        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);

        JobExecution execution = dormantBatchJob.execute();

        long count = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == CustomerStatus.DORMANT)
                .count();

        assertThat(count).isEqualTo(10);
        assertThat(execution.getBatchStatus()).isEqualTo(BatchStatus.COMPLETED);

    }

    @Transactional
    @Test
    @DisplayName("고객이 없는 경우에도 배치는 정상동작해야한다.")
    void test3() {

        JobExecution execution = dormantBatchJob.execute();

        long count = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == CustomerStatus.DORMANT)
                .count();

        assertThat(count).isEqualTo(0);
        assertThat(execution.getBatchStatus()).isEqualTo(BatchStatus.COMPLETED);
    }


    private void saveCustomer(long loginMinusDay) {
        String uuId = UUID.randomUUID().toString();
        final Customer customer = new Customer(uuId, uuId + "@naver.com");
        customer.setLoginAt(LocalDateTime.now().minusDays(loginMinusDay));
        customerRepository.save(customer);
    }

}