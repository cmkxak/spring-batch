package me.project.batch.batchcore;

public interface ItemProcessor<I, O> {

    O process(I item);
}
