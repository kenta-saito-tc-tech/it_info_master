package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.*;
import com.example.It_info_pass_master.Service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class ManageRestController {
    @Autowired
    private ManageService manageService;

    @PostMapping("/api/questionInsert")
    public int questionInsert(@RequestBody QuestionRecord questionRecord) {
        try {
            QuestionRecord questionId = manageService.questionInsert(questionRecord);
            return questionId.id();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/choiceInsert")
    public ResponseEntity<String> choiceInsert(@RequestBody List<ChoiceRecord> choiceRecord) {
        try {
            manageService.choiceInsert(choiceRecord);
            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/adminAllAgeSelect")
    public List<UserAgeRecord> adminAllAgeSelect() {
        try {
            var ageList = manageService.adminAllAgeSelect();
            return ageList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/adminAllQuestionSelect")
    public List<QuestionRecord> adminAllQuestionSelect() {
        try {
            var list = manageService.adminAllQuestionSelect();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/api/adminCheckAge")
    public List<AdminQuestionRecord> adminCheckAge(@RequestBody Map<String, Integer> requestBody) {
        try {
            Integer age = requestBody.get("age");
            System.out.println(age);
            var list = manageService.adminCheckAge(age);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/adminSetQuestion")
    public ResponseEntity<String> adminSetQuestion(@RequestBody List<AdminQuestionRecord> setQuestion) {
        try {
            manageService.adminSetQuestion(setQuestion);
            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/adminCheckGameAge")
    public List<AdminQuestionRecord> adminCheckGameAge(@RequestBody Map<String, Integer> requestBody) {
        try {
            Integer age = requestBody.get("age");
            System.out.println(age);
            var list = manageService.adminCheckGameAge(age);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/adminSetGameQuestion")
    public ResponseEntity<String> adminSetGameQuestion(@RequestBody List<AdminQuestionRecord> setQuestion) {
        try {
            manageService.adminSetGameQuestion(setQuestion);
            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/adminAddAge")
    public ResponseEntity<String> adminAddAge(@RequestBody Map<String, Integer> requestBody) {
        try {
            Integer age = requestBody.get("age");
            // 年代を追加する処理を実行
            manageService.adminAddAge(age);
            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

//    // 問題追加　
//    @PostMapping("/api/adminAddQuestion")
//    public ResponseEntity<String> adminAddQuestion(@RequestBody List<AdminQuestionRecord> setQuestion) {
//        System.out.println("追加ボタン押下");
//        System.out.println("questionId:"+setQuestion.get(0).ageId());
//        try {
//            manageService.adminAddQuestion(setQuestion);
//            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/api/adminAddQuestion")
    public int adminAddQuestion(@RequestBody List<AdminQuestionRecord> setQuestion) {
        System.out.println("追加ボタン押下");
        System.out.println("questionId:"+setQuestion.get(0).ageId());
        return manageService.adminAddQuestion(setQuestion);

//        try {
//            if (result > 0) {
//                return new ResponseEntity<>("問題を追加しました。", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("問題が追加できませんでした。", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("問題が追加できませんでした。", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PostMapping("/api/adminCheckImpossible")
    public List<QuestionIdRecord> adminCheckImpossible(@RequestBody int ageId) {
        try {
            var list = manageService.adminCheckImpossible(ageId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
