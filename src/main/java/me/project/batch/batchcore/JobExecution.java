package me.project.batch.batchcore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.project.batch.batchcore.BatchStatus;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class JobExecution {

    private BatchStatus batchStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
