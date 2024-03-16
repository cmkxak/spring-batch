package me.project.batch.util;

import lombok.extern.slf4j.Slf4j;

public interface EmailProvier {

    void send(String message, String email);

    @Slf4j
    class Fake implements EmailProvier {

        @Override
        public void send(String message, String email) {
            log.info("메시지 전송 완료.");
        }
    }


}
