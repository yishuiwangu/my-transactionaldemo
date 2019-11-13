package com.yswg.mytransactionaldemo.service;

import com.yswg.mytransactionaldemo.MyTransactionaldemoApplicationTests;
import com.yswg.mytransactionaldemo.exception.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyTransactionaldemoApplicationTests.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MySpringTransactionTest {

    @Autowired
    private MySpringTransactionService service;

    /*主动捕获了异常, 导致事务不能回滚*/
    @Test
    public void CatchExceptionCanNotRollback() {
        service.CatchExceptionCanNotRollback();
    }


    /*不是 unchecked 异常, 导致事务不能回滚*/
    @Test
    public void NotRuntimeExceptionCanNotRollback() throws CustomException {
        service.NotRuntimeExceptionCanNotRollback();
    }


}
