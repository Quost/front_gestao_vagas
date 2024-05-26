package io.github.mqdev.front_gestao_vagas.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.CandidateSignupDTO;
import io.github.mqdev.front_gestao_vagas.modules.candidate.services.ApplyJobService;
import io.github.mqdev.front_gestao_vagas.modules.candidate.services.CandidateLoginService;
import io.github.mqdev.front_gestao_vagas.modules.candidate.services.CandidateProfileService;
import io.github.mqdev.front_gestao_vagas.modules.candidate.services.CandidateSignupService;
import io.github.mqdev.front_gestao_vagas.modules.candidate.services.GetJobsService;
import io.github.mqdev.front_gestao_vagas.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateLoginService candidateLoginService;

    @Autowired
    private CandidateProfileService candidateProfileService;

    @Autowired
    private GetJobsService getJobsService;

    @Autowired
    private ApplyJobService applyJobService;

    @Autowired
    private CandidateSignupService candidateSignupService;

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @PostMapping("/signin")
    public String signin(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
        try {
            var authInfo = candidateLoginService.login(username, password);
            var roles = authInfo.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password,
                    roles);
            auth.setDetails(authInfo.getAccess_token());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext context = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
            session.setAttribute("token", authInfo);

            return "redirect:/candidate/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuário ou senha inválidos");
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile(Model model) {
        try {
            var candidate = this.candidateProfileService.getCandidateProfile(getToken());

            model.addAttribute("candidate", candidate);

            return "candidate/profile";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String jobs(Model model, String filter) {
        if (filter != null) {
            try {
                var jobs = this.getJobsService.getJobs(getToken(), filter);
                model.addAttribute("jobs", jobs);
            } catch (HttpClientErrorException e) {
                return "redirect:/candidate/login";
            }
        }

        return "candidate/jobs";
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String applyJob(@RequestParam UUID jobId) {
        try {
            this.applyJobService.applyJob(getToken(), jobId);
            return "redirect:/candidate/jobs";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("candidate", new CandidateSignupDTO());
        return "candidate/signup";
    }

    @PostMapping("/signup")
    public String signup(CandidateSignupDTO candidate, Model model) {
        try {
            this.candidateSignupService.signup(candidate);
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", FormatErrorMessage.format(e.getResponseBodyAsString()));
        }

        model.addAttribute("candidate", candidate);
        return "candidate/signup";
    }

    private String getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getDetails().toString();
    }

}
