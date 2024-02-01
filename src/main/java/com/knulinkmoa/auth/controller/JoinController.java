package com.knulinkmoa.auth.controller;

import com.knulinkmoa.auth.dto.JoinDto;
import com.knulinkmoa.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinP() {

        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto) {

        System.out.println("joinDto.getUsername() = " + joinDto.getUsername());
        System.out.println("joinDto.getPassword() = " + joinDto.getPassword());

        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }
}
