package me.changwoo.guestbook.service;

import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.entity.Guestbook;

public interface GuestbookSerivce {
    Long register(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
