package com.kosinov.keycloak.controller;

import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.service.KKService;
import jakarta.annotation.security.RolesAllowed;
import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class KKController {

    @Autowired
    private final KKService kkService;

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
    public String createUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "create-user";
    }

    @PostMapping("/create")
    @RolesAllowed("ADMIN")
    public String createUser(UserDTO userDTO) {
        kkService.addUser(userDTO);
        return "index";
    }

    @GetMapping("/selectUserDel")
    @RolesAllowed("ADMIN")
    public String selectUserDel(Model model) {
        model.addAttribute("actionDscr", "удаления");
        model.addAttribute("actionCode", "/delete");
        model.addAttribute("usersList", kkService.listUser());
        model.addAttribute("userDTO", new UserDTO());
        return "select-user";
    }

    @PostMapping("/delete")
    @RolesAllowed("ADMIN")
    public String delete(UserDTO userDTO) {
        kkService.deleteUser(userDTO);
        return "index";
    }

    @GetMapping("/changePassword")
    public String changePassword(Principal principal, Model model) {
        UserDTO user = kkService.findUser(principal.getName());
        model.addAttribute("userDTO", user);
        return "change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(UserDTO userDTO) {
        kkService.changePassword(userDTO);
        return "index";
    }

    @GetMapping("/selectUserEdit")
    @RolesAllowed("ADMIN")
    public String selectUserEdit(Model model) {
        model.addAttribute("actionDscr", "редактирования");
        model.addAttribute("actionCode", "/edit");
        model.addAttribute("usersList", kkService.listUser());
        model.addAttribute("userDTO", new UserDTO());
        return "select-user";
    }

    @PostMapping("/edit")
    public String edit(UserDTO userDTO, Model model) {
        UserDTO user = kkService.findUser(userDTO.getUserName());
        model.addAttribute("userEditDTO", user);
        return "edit-user";
    }

    @PostMapping("/saveEdit")
    public String saveEdit(UserDTO userDTO) {
        kkService.saveUser(userDTO);
        return "index";
    }
}
