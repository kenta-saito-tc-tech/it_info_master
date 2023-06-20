package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HttpSession session;



    @GetMapping("/question_answer/{id}/{choiceId}")
        public String questionSelect(@PathVariable("id") int id, @PathVariable("choiceId") int choiceId, Model model){
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        System.out.println("パスのID"+id);
        System.out.println("選択肢のID"+choiceId);

        var question = questionService.findQuestion(id);
        var choices = questionService.findChoices(id);

        for(var choice : choices){
            System.out.println(choice.id());
        }
        var answer = questionService.findAnswer(id);
        model.addAttribute("question", question);
        model.addAttribute("choices", choices);
        model.addAttribute("answer", answer);
        model.addAttribute("choiceId", choiceId);

        return "/question_answer";
    }
}
