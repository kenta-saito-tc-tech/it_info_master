package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.IdPassRecord;
import com.example.It_info_pass_master.Entity.UserRecord;

public interface AccountDao {
    public UserRecord findUser(IdPassRecord idPassRecord);
    public UserRecord checkIdExist(String id);
    public int insertUser(UserRecord userRecord);
}
