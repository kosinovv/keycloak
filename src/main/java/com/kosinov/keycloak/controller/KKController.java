package com.kosinov.keycloak.controller;

import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.service.KKService;
import jakarta.annotation.security.RolesAllowed;
import java.security.Principal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KKController {

    @Autowired
    private final KKService kkService;

    @GetMapping("/create")
    @RolesAllowed("ADMIN")
    public String createUser(Model model) {
        log.info("KKController запрос страницы создания пользователя");
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
        log.info("KKController запрос страницы выбора пользователя для удаления");
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
        log.info("KKController запрос страницы изменения пароля");
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
        log.info("KKController запрос страницы выбора пользователя для изменения");
        model.addAttribute("actionDscr", "редактирования");
        model.addAttribute("actionCode", "/edit");
        model.addAttribute("usersList", kkService.listUser());
        model.addAttribute("userDTO", new UserDTO());
        return "select-user";
    }

    @PostMapping("/edit")
    public String edit(UserDTO userDTO, Model model) {
        log.info("KKController переход на страницу изменения данных пользователя");
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
