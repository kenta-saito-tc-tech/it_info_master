package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Service.QuestionService;
import com.example.It_info_pass_master.Service.RandomService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
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

    @Autowired
    private HttpSession session;

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RandomService randomService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/random_main")
    public String questionSelect(Model model) {
        if (session.getAttribute("user") == null) { // セッションがない場合
            return "redirect:/index";
        }

        // セッションからレコードクラスを取得
        var selectRandomRecord = (SelectRandomRecord) session.getAttribute("selectRandomRecord");

        //user_check

        System.out.println("################### ランダムモードへListを渡す ######################");
        var ageId = selectRandomRecord.ageId();
        var categories = selectRandomRecord.categories();
        var perfect = selectRandomRecord.perfect();
        var userId = selectRandomRecord.userId();

        var question = randomService.selectRandom(ageId, categories, perfect, userId);
        var choices = questionService.findChoices(question.id());

        model.addAttribute("question", question);
        model.addAttribute("choices", choices);

        return "/random_main";
    }

    @PostMapping("/random_answer")
    public String randomAnswerTest(@RequestParam(name = "selectedItem") Integer choiceId,
                                   @RequestParam(name = "questionId") Integer id, Model model) {
        System.out.println("########################random_answer################################");
        System.out.println("questionId:" + id);
        System.out.println("choiceId:"+ choiceId);

        model.addAttribute("choiceId", choiceId);
        model.addAttribute("id", id);
        return "/random_answer";
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
