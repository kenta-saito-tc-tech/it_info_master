package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RandomController {
    @Autowired
    private RandomService randomService;
}
