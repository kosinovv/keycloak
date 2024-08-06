package com.kosinov.keycloak.controller;

import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.service.KKService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("kk")
@RequiredArgsConstructor
public class KKController {

    private final KKService kkService;

    @GetMapping(path = "/")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/users")
    public UserDTO users(@RequestBody UserDTO userDTO) {
        return kkService.addUser(userDTO);
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}
