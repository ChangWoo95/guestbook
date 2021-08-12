package me.changwoo.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.changwoo.guestbook.dto.GuestbookDTO;
import me.changwoo.guestbook.dto.request.PageRequestDTO;
import me.changwoo.guestbook.service.GuestbookSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto..." + dto);

        Long gno = guestbookSerivce.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("gno: " + gno);

        GuestbookDTO dto = guestbookSerivce.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info("post modify..............................................");
        log.info("dto: " +dto);

        guestbookSerivce.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {

        log.info("gno: " + gno );

        guestbookSerivce.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

}
