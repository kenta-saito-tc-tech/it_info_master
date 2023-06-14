package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManageController {
    @Autowired
    private ManageService manageService;
}
