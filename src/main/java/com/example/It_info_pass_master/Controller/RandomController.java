package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Service.RandomService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RandomController {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RandomService randomService;

    @Autowired
    private HttpSession session;

    @GetMapping("/random_main")
    public String questionSelect(@RequestParam("param") String param, Model model) {
        if (session.getAttribute("user") == null) { // セッションがない場合
            return "redirect:/index";
        }
        System.out.println("################### ランダムモードへListを渡す ######################");
        List<QuestionRecord> questionRecords = new ArrayList<>();
        try {
            questionRecords = objectMapper.readValue(param, new TypeReference<List<QuestionRecord>>() {});
            for (QuestionRecord a : questionRecords) {
                System.out.println(a.id());
                System.out.println(a.questionName());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("questionRecords",questionRecords);

        return "/random_main";
    }

//    @GetMapping("/random_main")
//    public String randomSelectView1() {
//        if (session.getAttribute("user") == null) { // セッションがない場合
//            return "redirect:/index";
//        }
//
//        return "/random_main";
//    }
}
