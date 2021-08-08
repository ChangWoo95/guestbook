package me.changwoo.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.dto.response.PageResultDTO;
import me.changwoo.guestbook.entity.Guestbook;
import me.changwoo.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;


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

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
