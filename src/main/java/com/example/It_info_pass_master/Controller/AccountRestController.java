package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.*;
import com.example.It_info_pass_master.Service.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    /**
     * ログイン時のID、PASSチェック
     *
     * @param idPassRecord
     * @return
     */
    @PostMapping("/log-check")
    public ResponseEntity<String> insertView(@Valid @RequestBody IdPassRecord idPassRecord) {
        try {
            UserRecord user = accountService.findUser(idPassRecord);
            if (user != null) {
                session.setAttribute("user", user);
                //System.out.println(user);
                return new ResponseEntity<>("POST request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("POST request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * userテーブルの追加
     *
     * @param
     * @return
     */
    @PostMapping("/insertUser")
    public ResponseEntity<String> insertProduct(@Valid @RequestBody UserRecord userRecord) {
        try {
            UserRecord user = accountService.checkIdExist(userRecord.loginId());
            if (user == null) {
                try {
                    int count = accountService.insertUser(userRecord);
                    if (count == 1) {
                        return new ResponseEntity<>("POST request processed", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("POST request failed", HttpStatus.BAD_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
                }
            } else {
                return new ResponseEntity<>("product_id exists", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("POST request failed", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * ゲームスコアの上位３位の取得
     *
     * @param
     * @return
     */
    @GetMapping("/ranking_select")
    public List<RankingRecord> findTopThree(@RequestParam(name = "age") String age) {
        try {
            var list = accountService.findTopThree(age);
            return list; //ステータスコード200番
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * 年代の全取得
     *
     * @param
     * @return
     */
    @GetMapping("/age_select")
    public List<AgeRecord> findAgeAll() {
        try {
            var list = accountService.findAgeAll();
            return list; //ステータスコード200番
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * ユーザーのランキング取得
     *
     * @param
     * @return
     */
    @GetMapping("/myRanking")
    public MyRankRecord myRanking(@RequestParam(name = "age") String age, @RequestParam(name = "id") int id) {
        try {
            var myRank = accountService.findUserRank(age, id);
            return myRank; //ステータスコード200番
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }



}
