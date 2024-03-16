package me.project.batch.application;

import me.project.batch.batchcore.JobExecution;
import me.project.batch.batchcore.JobExecutionListener;
import me.project.batch.util.EmailProvier;
import org.springframework.stereotype.Component;

@Component
public class DormantJobExecutionListener implements JobExecutionListener {

    private final EmailProvier emailProvier;

    public DormantJobExecutionListener() {
        this.emailProvier = new EmailProvier.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        emailProvier.send("배치 작업이 완료되었습니다."
                + "배치 상태 : " + jobExecution.getBatchStatus().name()
                + "배치 종료 시간 : " + jobExecution.getEndTime(), "admin-dev@test.com");
    }
}
