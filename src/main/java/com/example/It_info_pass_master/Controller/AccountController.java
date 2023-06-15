package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpSession session;

    /**
     * 初期画面
     *
     * @return ログイン画面
     */
    @GetMapping("/index")
    public String firstView() {
        if (session != null) {
            session.invalidate(); // セッションを無効化して削除する
        }
        return "/index";
    }

    /**
     * menuページ
     * @return
     */
    @GetMapping("/menu")
    public String mainView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        return "/menu";
    }

    /**
     * アカウント登録画面
     *
     * @return
     */
    @GetMapping("/user_add")
    public String newAccountView() {
        return "/user_add";
    }
}
