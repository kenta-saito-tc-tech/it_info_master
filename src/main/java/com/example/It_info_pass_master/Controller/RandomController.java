package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.LookCheckRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Entity.UserRecord;
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
import java.util.Arrays;
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
        var alert = "";

        System.out.println("################### GETランダムモード出題 ######################");
        var ageId = selectRandomRecord.ageId();
        var categories = selectRandomRecord.categories();
        var perfect = selectRandomRecord.perfect();
        var userId = selectRandomRecord.userId();

        var question = randomService.selectRandom(ageId, categories, perfect, userId);
        if(question == null){
            alert = "選択したカテゴリの問題が見つかりませんでした";
            model.addAttribute("alert",alert);
            return"/random_select";
        }
        var choices = questionService.findChoices(question.id());
        var checkUser = questionService.findCheckUser(userId, question.id(), ageId);

        model.addAttribute("question", question);
        model.addAttribute("choices", choices);
        model.addAttribute("ageId", ageId);

        return "/random_main";
    }

    @PostMapping("/random_main")
    public String randomAnswerTest(@RequestParam(name = "selectedItem") int choiceId,
                                   @RequestParam(name = "questionId") int questionId,
                                   @RequestParam(name = "ageId")int ageId, Model model) {
        System.out.println("########################random_answer################################");
        System.out.println("questionId:" + questionId);
        System.out.println("choiceId:"+ choiceId);

        var yourChoice = randomService.findYourAnswer(choiceId);
        //var question = questionService.findQuestion(questionId);
        var answer = questionService.findAnswer(questionId);

        model.addAttribute("questionId", questionId);
        model.addAttribute("answer", answer);
        model.addAttribute("yourChoice", yourChoice);
        model.addAttribute("ageId", ageId);
        return "/random_answer";
    }

    @PostMapping("/random_answer")
    public String random_answer(@RequestParam(name="questionId") int questionId,
                                @RequestParam(name="look_check", required = false) String lookingBackCheck,
                                @RequestParam(name="perfect_check", required = false) String perfectCheck, Model model){
        if (session.getAttribute("user") == null) { // セッションがない場合
            return "redirect:/index";
        }

        // セッションからレコードクラスを取得
        var selectRandomRecord = (SelectRandomRecord) session.getAttribute("selectRandomRecord");

        int lookCheck;
        if(lookingBackCheck != null && lookingBackCheck.equals("true")){
            lookCheck =1;
        }else {
            lookCheck =0;
        }

        int perfectCheck1;
        if(perfectCheck != null && perfectCheck.equals("true")){
            perfectCheck1 =1;
        }else {
            perfectCheck1 =0;
        }

        //user_check

        System.out.println("###################POSTランダムモード出題######################");
        var ageId = selectRandomRecord.ageId();
        var categories = selectRandomRecord.categories();
        var perfect = selectRandomRecord.perfect();
        var userId = selectRandomRecord.userId();
        randomService.updateLookingBackCheck(ageId, questionId, userId, lookCheck);
        randomService.updatePerfectCheck(ageId, questionId, userId, perfectCheck1);



        var question = randomService.selectRandom(ageId, categories, perfect, userId);
        if(question == null){
            return "/random_select";
        }
        var choices = questionService.findChoices(question.id());
        var checkUser = questionService.findCheckUser(userId, question.id(), ageId);

        System.out.println("random_mainへmodel/：question");
        System.out.println("random_mainへmodel/：choices");
        model.addAttribute("question", question);
        model.addAttribute("choices", choices);
        model.addAttribute("ageId", ageId);

        return "/random_main";
    }

    //見返しチェックリストを取得する処理
    @GetMapping("/random_look_list")
    public String randomLookList(@ModelAttribute("LookCheck") LookCheckRecord lookCheck,
                                 HttpSession session, Model model) {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        //セッションからuserIdを取得
        var user = (UserRecord) session.getAttribute("user");
        var userId = user.id();

        //見返しリストを取得
        var lookCheckList = randomService.findLookQuestion(userId);
        //後で使う　DBから取得した問題に関する情報をHTML（random_look_list）に送るための処理
        model.addAttribute("list", lookCheckList);

        return "/random_look_list";
    }

    //一覧リストの詳細を表示
    @PostMapping("random_look_detail")
    public String printDetail(@ModelAttribute("LookCheck") LookCheckRecord lookCheck,
                              @RequestParam(name = "checkQuestionId") Integer questionId,
                              @RequestParam(name = "checkQuestionText") String questionText,
                              HttpSession session, Model model) {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }
        System.out.print("クエスチョンIdを確認" + questionId);
        //userIdを取得
        var user = (UserRecord) session.getAttribute("user");
        var userId = user.id();
        var lookCheckList = randomService.findLookQuestion(userId);

        //問題文をrandom_look_detailに送っている
        model.addAttribute("questionText", questionText);

        //問題の解説を取得する処理
        var questionList = randomService.findAnswerText(questionId);
        var answerText = questionList.get(0).answerText();

        //解説をrandom_look_detailに送っている
        model.addAttribute("answerText", answerText);


        //正解の選択肢を取得する処理
        var questionList1 = randomService.findAnswer(questionId);
        var answer = questionList1.get(0).choiceText();
        model.addAttribute("answer", answer);

        //問題タイトルをrandom_look_detailに送る処理
        var questionTitle = questionList.get(0).questionName();
        model.addAttribute("questionTitle", questionTitle);

        //random_look_detailにquestionIdを渡すため
        model.addAttribute("checkQuestionId", questionId);

        return "/random_look_detail";
    }

    //見返しチェックを外す処理
    @PostMapping("/random_look_list")
    public String deleteLookingBack(@RequestParam(name = "checkQuestionId") int checkQuestionId,
                                    @RequestParam(name = "looking_check_back", required = false) Integer lookingBackCheck) {
        if (session.getAttribute("user") == null) { //sessionがない場合
            return "redirect:/index";
        }

        System.out.println("controllerから確認" + checkQuestionId);
        System.out.println("controllerから確認チェックボックス" + lookingBackCheck);

        var ageId = randomService.findAgeId(checkQuestionId);

        var user = (UserRecord) session.getAttribute("user");
        var userId = user.id();

        int check;
        if (lookingBackCheck == null) {
            check = 0;
            var checkTest = randomService.updateLookingBackCheck(ageId, checkQuestionId, userId, check);
        } //else {
//            check = 2;
//        }
//        var checkTest = randomService.updateLookingBackCheck(ageId, checkQuestionId, userId, check);
//        System.out.println("Controllerから確認"+checkTest);
        return "redirect:/random_look_list";
    }
}
