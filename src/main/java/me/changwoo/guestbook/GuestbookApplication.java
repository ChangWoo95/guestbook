package me.changwoo.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // AuditionEntityListener를 활성화하기 위한 Annotation
public class GuestbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestbookApplication.class, args);
    }

}
