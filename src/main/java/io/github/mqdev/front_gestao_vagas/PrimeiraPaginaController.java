package io.github.mqdev.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {
    
    @GetMapping("/home")
    public String homeHtml(Model model) {
        model.addAttribute("mensagemDaController", "Primeira mensagem da controller");
        return "primeiraPagina";
    }

    @GetMapping("/login")
    public String loginHtml() {
        return "candidate/login";
    }

    @PostMapping("/login")
    public String loginCandidate(Candidate candidate) {
        System.out.println("Email: " + candidate.email);
        System.out.println("Senha: " + candidate.password);
        return "candidate/login";
    }

    record Candidate(String email, String password) {}
}
