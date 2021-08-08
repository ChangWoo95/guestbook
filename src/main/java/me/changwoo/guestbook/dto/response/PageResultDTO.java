package me.changwoo.guestbook.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> { // 제네릭 타입을 이용해 DTO 와 EN(entity) 타입 지정

    private List<DTO> dtolist;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {

        dtolist = result.stream().map(fn).collect(Collectors.toList());
    }
}
