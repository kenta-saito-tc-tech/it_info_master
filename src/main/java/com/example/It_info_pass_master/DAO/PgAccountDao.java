package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.IdPassRecord;
import com.example.It_info_pass_master.Entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PgAccountDao implements AccountDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * IDとPASSからUserを探すメソッド
     *
     * @param idPassRecord
     * @return
     */
    @Override
    public UserRecord findUser(IdPassRecord idPassRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", idPassRecord.loginId());
        param.addValue("pass", idPassRecord.password());
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :id AND password = :pass",
                param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * IDが重複しないかチェックするメソッド
     * @param id
     * @return
     */
    @Override
    public UserRecord checkIdExist(String id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :loginId",
                param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * userの新規追加
     *
     * @param
     * @return
     */
    @Override
    public int insertUser(UserRecord userRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", userRecord.loginId());
        param.addValue("password", userRecord.password());
        param.addValue("name", userRecord.name());
        param.addValue("role", userRecord.role());
        int count = jdbcTemplate.update("INSERT INTO Users" +
                "(login_id, password, name, role)" +
                " VALUES (:loginId, :password, :name, :role)", param);
        return count == 1 ? count : null;
    }
    //todo responsibleIdをroleに変更
}
