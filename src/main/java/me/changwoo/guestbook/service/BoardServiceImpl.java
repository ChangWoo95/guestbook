package me.changwoo.guestbook.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.BoardDTO;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.dto.response.PageResultDTO;
import me.changwoo.guestbook.entity.Board;
import me.changwoo.guestbook.entity.Member;
import me.changwoo.guestbook.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository; //생성자 자동주입 방식

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member)en[1], (Long)en[2] ));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }
}
