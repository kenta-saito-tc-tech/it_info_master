package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;
}
