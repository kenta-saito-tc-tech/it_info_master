package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.AccountDao;
import com.example.It_info_pass_master.Entity.IdPassRecord;
import com.example.It_info_pass_master.Entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgAccountService implements AccountService{
    @Autowired
    private AccountDao accountDao;

    @Override
    public UserRecord findUser(IdPassRecord idPassRecord) {
        return accountDao.findUser(idPassRecord);
    }
    @Override
    public UserRecord checkIdExist(String id) {
        return accountDao.checkIdExist(id);
    }
    @Override
    public int insertUser(UserRecord userRecord) { return accountDao.insertUser(userRecord);}

}
