package me.changwoo.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.entity.Guestbook;
import me.changwoo.guestbook.repository.GuestbookRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestbookSerivce {

    private final GuestbookRepository guestbookRepository; // RequiredArgsConstructer를 통해 생성자 주입
    
    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO..................");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }
}
