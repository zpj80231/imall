package com.imall.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.imall.note")
@Slf4j
public class ImallNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImallNoteApplication.class, args);
        log.info("API address -> http://localhost:8080/doc.html");
    }

}
