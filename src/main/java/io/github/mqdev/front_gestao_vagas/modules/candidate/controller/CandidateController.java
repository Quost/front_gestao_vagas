package io.github.mqdev.front_gestao_vagas.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.mqdev.front_gestao_vagas.modules.candidate.service.CandidateService;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @PostMapping("/signin")
    public String signin(RedirectAttributes redirectAttributes, String username, String password) {
        try {
            candidateService.login(username, password);
            return "candidate/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos");
            return "redirect:/candidate/login";
        }
    }

}
