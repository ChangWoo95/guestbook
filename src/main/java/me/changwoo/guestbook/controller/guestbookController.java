package me.changwoo.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.service.GuestbookSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class guestbookController {

    private final GuestbookSerivce guestbookSerivce;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list............." + pageRequestDTO);


        model.addAttribute("result", guestbookSerivce.getList(pageRequestDTO)); // result란 key로 paging 객체 list 부여

    }
}
