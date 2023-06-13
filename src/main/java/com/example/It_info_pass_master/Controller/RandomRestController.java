package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomRestController {
    @Autowired
    private RandomService randomService;
}
