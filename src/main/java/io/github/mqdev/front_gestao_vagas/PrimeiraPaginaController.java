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
    public String loginCandidate(Model model, Candidate candidate) {
        System.out.println("Nome: " + candidate.name);
        System.out.println("Email: " + candidate.email);
        System.out.println("Senha: " + candidate.password);

        model.addAttribute("candidate", candidate);

        return "candidate/info";
    }

    record Candidate(String email, String password, String name) {
    }
}
