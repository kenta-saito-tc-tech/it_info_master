package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserRecord;
import com.example.It_info_pass_master.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HttpSession session;



    @PostMapping("/question_test")
        public String questionSelect(@RequestParam(name="question_id") int id,
                                     @RequestParam(name="selectedItem") int choiceId,
                                     @RequestParam(name="age_id") int ageId, Model model){
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        System.out.println("question_testからageId送信："+ageId);
        System.out.println("問題のID"+id);
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
        model.addAttribute("ageId", ageId);

        return "/question_answer";
    }

    @GetMapping("/question_test")
    public String questionTest(@RequestParam("title") String questionName,
                               @RequestParam("ageId") String ageId,
                               @RequestParam("categoryId") String categoryId,
                               HttpSession session,Model model) {

        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        var age = questionService.findAge(Integer.parseInt(ageId));
        model.addAttribute("age", age);

        var questionId = questionService.findQuestionId(questionName);
        var question = questionService.findQuestion(questionId);
        var category = questionService.findCategory(Integer.parseInt(question.categoryId()));
        model.addAttribute("questionId",questionId);
        model.addAttribute("questionText", question.questionText());
        model.addAttribute("questionTitle", question.questionName());
        model.addAttribute("category", category);
        model.addAttribute("ageId",ageId);

        var choices = questionService.findChoices(questionId);
        model.addAttribute("choices", choices);

        //レコードがあるかの確認＆レコードの作成
        //userIdを取得
        var user = (UserRecord) session.getAttribute("user");
        var userId = user.id();
        var checkUser = questionService.findCheckUser(userId, questionId, Integer.parseInt(ageId));

        System.out.println("一問一答出題画面questionId："+questionId);

        return "/question_test";
    }
}
