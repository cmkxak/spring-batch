package me.project.batch.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class JobExecution {

    private BatchStatus batchStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
