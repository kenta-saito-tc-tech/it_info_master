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
    public List<UserAgeRecord> findAgeAll() {
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

    /**
     * userの名前変更
     *
     * @param
     * @return
     */
    @PutMapping("/updateUserName")
    public ResponseEntity<String> userNameUpdate(@Valid @RequestBody UserRecord userRecord) {
        try {
            int count = accountService.userNameUpdate(userRecord);
            if (count == 1) {
                return new ResponseEntity<>("PUT request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("PUT request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * userのPASS変更
     *
     * @param
     * @return
     */
    @PutMapping("/updateUserPass")
    public ResponseEntity<String> userPassUpdate(@Valid @RequestBody UserRecord userRecord) {
        try {
            int count = accountService.userPassUpdate(userRecord);
            if (count == 1) {
                return new ResponseEntity<>("PUT request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("PUT request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * userテーブルの削除
     *
     * @param
     * @return
     */
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> userDelete(@Valid @RequestBody UserRecord userRecord) {
        try {
            int count = accountService.userDelete(userRecord);
            if (count == 1) {
                return new ResponseEntity<>("DELETE request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("DELETE request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * userテーブル情報全取得
     *
     * @param
     * @return
     */
    @GetMapping("/findAllUser")
    public List<UserRecord> selectUsers() {
        try {
            var list = accountService.findAllUser();
            return list; //ステータスコード200番
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * inquiryテーブルの追加
     * @param
     * @return
     */
    @PostMapping("/insertInquiry")
    public ResponseEntity<String> insertInquiry(@Valid @RequestBody InquiryRecord inquiryRecord) {

        try {
            int count = accountService.insertInquiry(inquiryRecord);
            if (count == 1) {
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
     * Inquiryテーブル情報ユーザー別全取得
     *
     * @param
     * @return
     */
    @GetMapping("/inquiries")
    public List<InquiryRecord> findAllInquiry(@RequestParam(name = "searchId") int id) {
        try {
            var list = accountService.findAllInquiry(id);
            return list; //ステータスコード200番
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * 問い合わせの表示用データ取得
     *
     * @return
     */
    @GetMapping("/inquiry")
    public InquiryRecord inquiryFindById(@RequestParam(name = "searchId") int id) {
        try {
            var data = accountService.inquiryFindById(id);
            return data; //ステータスコード200番
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * Inquiryテーブル情報全取得
     *
     * @param
     * @return
     */
    @GetMapping("/inquiries_admin")
    public List<InquiryAdminRecord> findAllInquiry() {
        try {
            var list = accountService.findAllInquiry();
            return list; //ステータスコード200番
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * inquiryテーブルanswerの更新
     * @param
     * @return
     */
    @PutMapping("/updateAnswerInquiry")
    public ResponseEntity<String> updateAnswerInquiry(@Valid @RequestBody InquiryRecord inquiryRecord) {

        try {
            int count = accountService.updateAnswerInquiry(inquiryRecord);
            if (count == 1) {
                return new ResponseEntity<>("PUT request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("PUT request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * inquiryテーブル既読の更新
     * @param
     * @return
     */
    @PutMapping("/updateReadInquiry")
    public ResponseEntity<String> updateReadInquiry(@Valid @RequestBody InquiryRecord inquiryRecord) {
        try {
            int count = accountService.updateReadInquiry(inquiryRecord.id());
            if (count == 1) {
                return new ResponseEntity<>("PUT request processed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("PUT request failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

    /**
     * グラフの表示用データ取得
     *
     * @return
     */
    @GetMapping("/graph")
    public List<GraphRecord> graphFindByAge(@RequestParam(name = "age") String age) {
        try {
            UserRecord user = (UserRecord) session.getAttribute("user");
            var data = accountService.graphFindByAge(age, user.id());
            return data; //ステータスコード200番
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //ステータスコード400番
        }
    }

}
