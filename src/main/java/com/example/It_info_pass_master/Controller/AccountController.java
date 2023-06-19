package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.UserRecord;
import com.example.It_info_pass_master.Service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    /**
     * アカウント編集画面
     */
    @GetMapping("/account_change")
    public String accountChangeView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        return "/account_change";
    }

    /**
     * アカウント管理画面
     */
    @GetMapping("/admin_account")
    public String adminAccountView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        UserRecord user = (UserRecord) session.getAttribute("user");
        if (user.role().equals("1")) { // sessionのroleが1の場合
            return "redirect:/index";
        }

        return "/admin_account";
    }

    /**
     * 問題作成画面
     */
    @GetMapping("/admin_question_make")
    public String adminQuestionMakeView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        UserRecord user = (UserRecord) session.getAttribute("user");
        if (user.role().equals("1")) { // sessionのroleが1の場合
            return "redirect:/index";
        }

        return "/admin_question_make";
    }

    /**
     * ユーザー問い合わせ画面
     */
    @GetMapping("/user_inquiry")
    public String userInquiryView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        UserRecord user = (UserRecord) session.getAttribute("user");
        if (user.role().equals("1")) { // sessionのroleが1の場合
            return "redirect:/index";
        }
        return "/user_inquiry";
    }

    /**
     * 管理者問い合わせ画面
     */
    @GetMapping("/admin_inquiry")
    public String adminInquiryView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        return "/admin_inquiry";
    }

    /**
     * ゲームモード初期画面
     */
    @GetMapping("/game_main")
    public String gameMainView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        return "/game_main";
    }

    /**
     * 一問一答初期画面
     */
    @GetMapping("/question_select")
    public String questionSelectView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        return "/question_select";
    }

    /**
     * ランダム問題画面
     */
    @GetMapping("/random_select")
    public String randomSelectView() {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        return "/random_select";
    }

    /**
     * 問い合わせ情報画面
     *
     * @return
     */
    @GetMapping("/detail_inquiry/{id}")
    public String detailView(@PathVariable("id") int id, Model model) {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        model.addAttribute("id", id);
        return "/detail_inquiry";
    }
}
