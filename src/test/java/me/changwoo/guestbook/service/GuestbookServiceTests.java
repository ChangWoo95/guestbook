package me.changwoo.guestbook.service;

import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.dto.response.PageResultDTO;
import me.changwoo.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookSerivce service;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title....")
                .content("Sample content....")
                .writer("user0")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for ( GuestbookDTO guestbookDTO : resultDTO.getDtolist()) {
            System.out.println(guestbookDTO);
        }
    }
}
