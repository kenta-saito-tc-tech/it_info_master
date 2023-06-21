package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Entity.CategoryRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserCategoryRecord;
import com.example.It_info_pass_master.Service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RandomRestController {
    @Autowired
    private RandomService randomService;

    @GetMapping("/api/findRandSelect")
    public List<CategoryRecord> findRandSelect(@RequestParam(name = "ageUserId")String[] ageUserId){
        var ageId = Integer.parseInt(ageUserId[0]);
        var userId = Integer.parseInt(ageUserId[1]);
        System.out.println("cont ageId"+ageId);
        System.out.println("cont userId"+userId);
        List<CategoryRecord> check;
        try {
            check = randomService.findRandSelect(ageId, userId);
            if(check.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            for (var a: check){
                System.out.println(a.id());
                System.out.println(a.categoryName());
            }
            return check;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/selectRandom")
    public List<QuestionRecord> selectRandom(@RequestBody SelectRandomRecord selectRandomRecord) {
        System.out.println("cont AgeCategories："+ selectRandomRecord);

        return randomService.selectRandom(selectRandomRecord);
    }

    @PutMapping("/api/category_select_update")
    public int categorySelectUpdate(@RequestBody UserCategoryRecord userCategoryRecord) {
        System.out.println("チェック状況の要素数");
        System.out.println(userCategoryRecord.categorySelect().length);

        return randomService.categorySelectUpdate(userCategoryRecord);
    }

}
