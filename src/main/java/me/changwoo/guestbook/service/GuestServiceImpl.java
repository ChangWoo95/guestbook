package me.changwoo.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.dto.response.PageResultDTO;
import me.changwoo.guestbook.entity.Guestbook;
import me.changwoo.guestbook.entity.QGuestbook;
import me.changwoo.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = guestbookRepository.findById(dto.getGno());

        if(result.isPresent()) {

            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            guestbookRepository.save(entity);
        }

    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0) { // 검색조건이 없는 경우,
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")) {
           conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
