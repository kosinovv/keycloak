package com.kosinov.keycloak.controller;

import com.kosinov.keycloak.dto.PasswordDTO;
import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.dto.UserDeleteDTO;
import com.kosinov.keycloak.service.KKService;
import jakarta.annotation.security.RolesAllowed;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class KKController {

    private final KKService kkService;
    private String curUserName;

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String admin(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user";
    }

    @GetMapping("/create")
    @RolesAllowed("ADMIN")
    public String createUser(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "create-user";
    }

    @PostMapping("/create")
    @RolesAllowed("ADMIN")
    public String createUser(@RequestBody UserDTO userDTO) {
        kkService.addUser(userDTO);
        return "index";
    }

    @GetMapping("/delete")
    @RolesAllowed("ADMIN")
    public String deleteUser() {
        return "delete-user";
    }

    @PostMapping("/delete")
    @RolesAllowed("ADMIN")
    public String deleteUser(@RequestBody UserDeleteDTO userDeleteDTO) {
        kkService.deleteUser(userDeleteDTO);
        return "index";
    }

    @GetMapping("/changePassword")
    public String changePassword(Principal principal, Model model) {
        curUserName = principal.getName();
        model.addAttribute("username", principal.getName());
        return "change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordDTO passwordDTO) {
        passwordDTO.setUserName(curUserName);
        kkService.changePassword(passwordDTO);
        return "index";
    }
}
