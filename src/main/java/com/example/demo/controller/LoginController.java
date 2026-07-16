package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.LoginListDAO;

@Controller
public class LoginController {

    @Autowired
    private LoginListDAO loginListDAO;

    // ログイン画面表示
    @GetMapping("/")
    public String showLogin() {

        return "index";
    }

    // ログイン処理
    @PostMapping("/Login")
    public String login(
            @RequestParam(menber_Name = "member_name") String name,
            @RequestParam(name = "pass") String pass,
            Model model) {

        // 会員ID未入力
        if (name == null || name.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "会員IDを入力してください");

            return "index";
        }

        // パスワード未入力
        if (pass == null || pass.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "パスワードを入力してください");

            model.addAttribute(
                    "name",
                    name);

            return "index";
        }

        // DAOで会員検索
        Login loginUser = loginListDAO.findLogin(name, pass);

        // ログイン失敗
        if (loginUser == null) {

            model.addAttribute(
                    "errorMsg",
                    "会員IDまたはパスワードが正しくありません");

            model.addAttribute(
                    "name",
                    name);

            return "index";
        }

        // ログイン成功
        model.addAttribute(
                "loginUser",
                loginUser);

        return "main";
    }

}