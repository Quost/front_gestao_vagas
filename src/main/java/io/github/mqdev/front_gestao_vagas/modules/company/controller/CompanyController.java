package io.github.mqdev.front_gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.mqdev.front_gestao_vagas.modules.company.dto.CompanySignupDTO;
import io.github.mqdev.front_gestao_vagas.modules.company.dto.CreateJobDTO;
import io.github.mqdev.front_gestao_vagas.modules.company.service.CompanyLoginService;
import io.github.mqdev.front_gestao_vagas.modules.company.service.CompanySignupService;
import io.github.mqdev.front_gestao_vagas.modules.company.service.CreateJobService;
import io.github.mqdev.front_gestao_vagas.modules.company.service.ListAllCompanyJobsService;
import io.github.mqdev.front_gestao_vagas.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyLoginService companyLoginService;

    @Autowired
    private CompanySignupService companySignupService;

    @Autowired
    private CreateJobService createJobService;

    @Autowired
    private ListAllCompanyJobsService listAllCompanyJobsService;

    @GetMapping("/login")
    public String login() {
        return "company/login";
    }

    @PostMapping("/signin")
    public String signin(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
        try {
            var authInfo = companyLoginService.login(username, password);
            var roles = authInfo.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password,
                    roles);
            auth.setDetails(authInfo.getAccess_token());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext context = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
            session.setAttribute("token", authInfo);

            return "redirect:/company/jobs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos");
            return "redirect:/company/login";
        }
    }
    
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

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(Model model) {
        model.addAttribute("jobs", new CreateJobDTO());
        return "company/jobs";
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(CreateJobDTO job, Model model) {
        try {
            createJobService.createJob(job, getToken());
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", FormatErrorMessage.format(e.getResponseBodyAsString()));
        }
        model.addAttribute("jobs", job);
        return "redirect:/company/jobs/list";
    }

    @GetMapping("/jobs/list")
    @PreAuthorize("hasRole('COMPANY')")
    public String listJobs(Model model) {
        model.addAttribute("jobs", listAllCompanyJobsService.listAllCompanyJobs(getToken()));
        return "company/list-jobs";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/company/login";
    }

    private String getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getDetails().toString();
    }
}
