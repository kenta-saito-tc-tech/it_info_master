package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

}
