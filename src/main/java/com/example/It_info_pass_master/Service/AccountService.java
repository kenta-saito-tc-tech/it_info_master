package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.Entity.IdPassRecord;
import com.example.It_info_pass_master.Entity.UserRecord;

public interface AccountService {
    public UserRecord findUser(IdPassRecord idPassRecord);
    public UserRecord checkIdExist(String id);
    public int insertUser(UserRecord userRecord);
}
