package me.changwoo.guestbook.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    
    private int page;
    private int size;
    
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
    
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort); // JPA는 페이지가 0부터 시작하기 때문에 1페이지의 경우 0이 될 수 있도록 page-1 형태
    }
}
