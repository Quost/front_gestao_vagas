package io.github.mqdev.front_gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import io.github.mqdev.front_gestao_vagas.modules.company.dto.CompanySignupDTO;
import io.github.mqdev.front_gestao_vagas.modules.company.service.CompanySignupService;
import io.github.mqdev.front_gestao_vagas.utils.FormatErrorMessage;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanySignupService companySignupService;
    
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("company", new CompanySignupDTO());
        return "company/signup";
    }

    @PostMapping("/signup")
    public String signup(CompanySignupDTO company, Model model) {
        try {
            companySignupService.signup(company);
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", FormatErrorMessage.format(e.getResponseBodyAsString()));
        }
        model.addAttribute("company", company);
        return "company/signup";
    }
}
