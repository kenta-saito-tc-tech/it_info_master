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

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HttpSession session;



    @PostMapping("/question_answer")
        public String questionSelect(@RequestParam(name="question_id") int id,
                                     @RequestParam(name="selectedItem") int choiceId, Model model){
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

    @GetMapping("/question_test/{ageId}/{category}/{id}")
    public String questionTest(@PathVariable("ageId") int ageId,
                               @PathVariable("category") String category,
                               @PathVariable("id") int questionId,
                               HttpSession session,Model model) {

        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        var age = questionService.findAge(ageId);
        model.addAttribute("age", age);

        var question = questionService.findQuestion(questionId);
        model.addAttribute("questionText", question.questionText());
        model.addAttribute("questionTitle", question.questionName());
        model.addAttribute("category", question.categoryId());

        var choices = questionService.findChoices(questionId);
        model.addAttribute("choices", choices);

        //レコードがあるかの確認＆レコードの作成
        //userIdを取得
        var user = (UserRecord) session.getAttribute("user");
        var userId = user.id();
        System.out.print("controllerから確認 ユーザーID：" + userId);
        var checkUser = questionService.findCheckUser(userId, questionId, ageId);

        return "/question_test";
    }
}
