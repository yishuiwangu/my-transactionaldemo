package com.yswg.mytransactionaldemo.service.impl;

import com.yswg.mytransactionaldemo.dao.NotableDao;
import com.yswg.mytransactionaldemo.entity.Notable;
import com.yswg.mytransactionaldemo.exception.CustomException;
import com.yswg.mytransactionaldemo.service.MySpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MySpringTransactionServiceImpl  implements MySpringTransactionService{

    @Autowired
    private NotableDao notableDao;


    @Transactional
    @Override
    public void CatchExceptionCanNotRollback() {
        try {
            notableDao.save(new Notable("孙悟空"));
            throw new RuntimeException();
        }catch (Exception e){
            e.printStackTrace();
            //手动回滚
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }


    @Transactional
    @Override
    public void NotRuntimeExceptionCanNotRollback() throws CustomException {
        try {
            notableDao.save(new Notable("猪八戒"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());

        }
    }
}
