package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Selection;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class PgRandomDao implements RandomDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private HttpSession session;

    @Override
    public List<CategoryRecord> findRandSelect(int ageId, int userId){
        System.out.println("dao ageId:"+ageId);
        System.out.println("dao userId:"+userId);
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId",ageId);
        param.addValue("userId",userId);

        var checkUser = jdbcTemplate.query("SELECT * " +
                "FROM category " +
                "WHERE id IN (SELECT category_id " +
                "FROM category_select " +
                "WHERE age_id = :ageId AND " +
                "user_id = :userId)", param, new DataClassRowMapper<>(CategoryRecord.class));
        int resultSet = checkUser.isEmpty() ? 0 : 1;

        if(resultSet == 0){
            for(var i = 1; i < 7; i++){
                param.addValue("categoryId",i);
                jdbcTemplate.update("INSERT INTO category_select (user_id, category_id, age_id, category_select) " +
                        "VALUES(:userId, :categoryId, :ageId, 1)", param);
            }

        }

        String query = "SELECT * " +
                "FROM category " +
                "WHERE id IN (SELECT category_id " +
                "FROM category_select " +
                "WHERE age_id = :ageId AND " +
                "user_id = :userId AND " +
                "category_select = 1)";
        List<CategoryRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(CategoryRecord.class));
        for(var a : result){
            System.out.println(a.id());
            System.out.println(a.categoryName());
        }
        return result;
    }
    @Override
    public QuestionRecord selectRandom(Integer ageId, String[] categories,Integer perfect, Integer userId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("userId", userId);
        int[] intArray = new int[categories.length];
        param.addValue("categories", intArray);
        for(var i = 0; i < categories.length; i++){
            intArray[i] = Integer.parseInt(categories[i]);
        }
        if(perfect == 1){
           String query = "select * " +
                   "from questions " +
                   "where id IN (select question_id " +
                   "from question_age " +
                   "WHERE id IN (select question_age_id from user_check where user_id = :userId AND perfect_check = 0 OR user_id = :userId AND perfect_check = 1) " +
                   "OR id NOT IN (select question_age_id from user_check where user_id = :userId AND perfect_check = 1 OR user_id = :userId AND perfect_check = 0) " +
                   "AND age_id = :ageId) " +
                   "AND category_id = ANY(:categories) " +
                   "ORDER BY RANDOM() " +
                   "limit 1";
           var list = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
           System.out.println("randomSelect##################################################");
           System.out.println(list);
           return list.isEmpty() ? null : list.get(0);

       }else if(perfect == 0){
            String query = " select * " +
                    "from questions " +
                    "where id IN (select question_id " +
                    "from question_age " +
                    "WHERE id IN (select question_age_id from user_check where user_id = :userId AND perfect_check = 0) " +
                    "OR id NOT IN (select question_age_id from user_check where user_id = :userId AND perfect_check = 1 OR user_id = :userId AND perfect_check = 0) " +
                    "AND age_id = :ageId) " +
                    "AND category_id = ANY(:categories) " +
                    "ORDER BY RANDOM() " +
                    "limit 1";
            var list = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
            System.out.println("randomSelect##################################################");
            System.out.println(list);
            return list.isEmpty() ? null : list.get(0);
       }
        return null;
    }

    @Override
    public int categorySelectUpdate(UserCategoryRecord userCategoryRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        System.out.println(userCategoryRecord.ageId());
        System.out.println(userCategoryRecord.userId());
        System.out.println(userCategoryRecord.categorySelect());

        param.addValue("ageId", userCategoryRecord.ageId());
        param.addValue("userId", userCategoryRecord.userId());

//        for(var i = 0; i < userCategoryRecord.categorySelect().length; i++){
//            param.addValue("check", userCategoryRecord.categorySelect()[i]);
//            jdbcTemplate.update("UPDATE category_select " +
//                    "SET  = :check " +
//                    "WHERE question_age_id = (SELECT id " +
//                    "FROM question_age WHERE question_id = :questionId) " +
//                    "AND user_id = :userId", param);
//
//        }
        //カテゴリが変わったらiの数値も変える
        for(var i = 0; i < 6; i++){
            param.addValue("check", userCategoryRecord.categorySelect()[i]);
            param.addValue("categoryId", i+1);

            jdbcTemplate.update("UPDATE category_select " +
                    "SET category_select = :check " +
                    "WHERE user_id = :userId " +
                    "AND category_id = :categoryId " +
                    "AND age_id = :ageId", param);
        }

        return 1;
    }

    @Override
    public int sessionRandom(SelectRandomRecord selectRandomRecord){
        System.out.println("sessionに登録");


        session.setAttribute("selectRandomRecord", selectRandomRecord);
        return 0;
    }

    public ChoiceRecord  findYourAnswer(int choiceId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("choiceId", choiceId);

        String query = "SELECT * " +
                "FROM choice " +
                "WHERE id = :choiceId";

        var list = jdbcTemplate.query(query, param, new DataClassRowMapper<>(ChoiceRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    //見返しリストをアップデートする処理
    public int updateLookingBackCheck(int ageId, int questionId, int userId, int check){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("questionId", questionId);
        param.addValue("userId", userId);
        param.addValue("check", check);

        System.out.println("updateLookCheck:ageId:"+ageId);
        System.out.println("updateLookCheck:questionId:"+questionId);
        System.out.println("updateLookCheck:userId:"+userId);
        System.out.println("updateLookCheck:perfectCheckId:"+check);


        //変数を取得
        String sql = "SELECT id " +
                "FROM question_age " +
                "WHERE age_id = :ageId " +
                "AND question_id = :questionId";

        var questionAgeId = jdbcTemplate.queryForObject(sql, param, Integer.class);
        param.addValue("questionAgeId", questionAgeId);


        //見返しリストをアップデートする処理（userIdとquestionAgeIdが必要）
        var resultSet = jdbcTemplate.update("UPDATE user_check SET look_check = :check WHERE user_id = :userId AND question_age_id = :questionAgeId",param);
        System.out.println("controllerから確認"+resultSet);
        return resultSet;
    };

    @Override
    //見返しリストをアップデートする処理
    public int updatePerfectCheck(int ageId, int questionId, int userId, int perfectCheck1){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("questionId", questionId);
        param.addValue("userId", userId);
        param.addValue("perfectCheck", perfectCheck1);

        System.out.println("updatePerfectCheck:ageId:"+ageId);
        System.out.println("updatePerfectCheck:questionId:"+questionId);
        System.out.println("updatePerfectCheck:userId:"+userId);
        System.out.println("updatePerfectCheck:perfectCheckId:"+perfectCheck1);


        //変数を取得
        String sql = "SELECT id " +
                "FROM question_age " +
                "WHERE age_id = :ageId " +
                "AND question_id = :questionId";
        param.addValue("ageId", ageId);

        var questionAgeId = jdbcTemplate.queryForObject(sql, param, Integer.class);
        param.addValue("questionAgeId", questionAgeId);


        //見返しリストをアップデートする処理（userIdとquestionAgeIdが必要）
        var resultSet = jdbcTemplate.update("UPDATE user_check SET perfect_check = :perfectCheck WHERE user_id = :userId AND question_age_id = :questionAgeId",param);
        System.out.println("controllerから確認"+resultSet);
        return resultSet;
    }

    //checkが2になっているリストを取得
    public List<LookCheckRecord> findLookQuestion(int userId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userId", userId);
        List<LookCheckRecord> resultSet = jdbcTemplate.query(
                "SELECT question.id AS id,\n" +
                        "question.question_name AS questionName,\n" +
                        "question.question_text AS questionText,\n" +
                        "category.category_name AS category_name,\n" +
                        "age.age AS age\n" +
                        "FROM questions AS question\n" +
                        "JOIN category ON question.category_id = category.id\n" +
                        "JOIN question_age ON question.id = question_age.question_id\n" +
                        "JOIN age ON question_age.age_id = age.id\n" +
                        "JOIN user_check ON question_age.id = user_check.question_age_id\n" +
                        "WHERE user_check.look_check = 1\n" +
                        "AND user_check.user_id = :userId;", param, new DataClassRowMapper<>(LookCheckRecord.class));
//        System.out.println("DAOから確認" + resultSet);
        return resultSet;
    }

    ;

    @Override
    //解説を取得するメソッド
    public List<QuestionRecord> findAnswerText(Integer questionId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", questionId);
        List<QuestionRecord> resultSet = jdbcTemplate.query("SELECT * FROM questions WHERE id = :questionId", param, new DataClassRowMapper<>(QuestionRecord.class));
        return resultSet;
    }

    ;

    @Override
//正解の選択肢を取得するメソッド
    public List<ChoiceRecord> findAnswer(Integer questionId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionId", questionId);
        List<ChoiceRecord> resultSet = jdbcTemplate.query("SELECT * FROM choice WHERE answer = true AND question_id = :questionId", param, new DataClassRowMapper<>(ChoiceRecord.class));
        return resultSet;
    }

    @Override
    public int findAgeId(int questionId) {
        String sql = "SELECT age_id " +
                "FROM question_age " +
                "WHERE question_id = :question_id";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("question_id", questionId);

        var answer = jdbcTemplate.queryForObject(sql, param, Integer.class);
        return answer;
    }


}
