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
public class MySpringTransactionServiceImpl implements MySpringTransactionService {

    @Autowired
    private NotableDao notableDao;


    @Transactional
    @Override
    public void CatchExceptionCanNotRollback() {
        try {
            notableDao.save(new Notable("孙悟空"));
            throw new RuntimeException();
        } catch (Exception e) {
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

    @Transactional
    @Override
    public void RuntimeExceptionCanRollback() {
        notableDao.save(new Notable("RuntimeExceptionCanRollback沙僧"));
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = CustomException.class)
    @Override
    public void AssignExceptionCanRollback() throws CustomException {

        try {
            notableDao.save(new Notable("AssignExceptionCanRollback沙僧"));
            throw new RuntimeException();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

//        notableDao.save(new Notable("AssignExceptionCanRollback沙僧"));
//        throw new RuntimeException();
    }


    /**
     * <h2>在 private 方法上标注 transactional, 事务无效</h2>
     */
    @SuppressWarnings("all")
    @Transactional
    public void oneSaveMethod() {
        notableDao.save(new Notable("oneSaveMethod唐僧"));
    }


    /*
    *java.sql.SQLIntegrityConstraintViolationException: Column 'name' cannot be null
    *org.springframework.transaction.UnexpectedRollbackException: Transaction silently rolled back because it has been marked as rollback-only
    * rollback-only指的是事务处理失败
    * 注释:
    * 在有默认事务的方法调用另一个待遇默认事务的方法时,2个事务合二为一.当其中一个方法出现异常时,会被spring标记为rollback-only,当该方法的异常被捕获没有抛出,
    * 则在最后的事务提交时,会出现spring事务的处理异常UnexpectedRollbackException,导致整个方法的处理失败
    * 所以当前方法最后没有在数据库里插入数据的原因不是事务回滚,而是事务的处理失败异常
    * 则,如果方法异常了,抛出异常,最后数据库没有出现插入值,就是spring事务的回滚,且控制台就不会出现UnexpectedRollbackException
    * 只有ava.sql.SQLIntegrityConstraintViolationException: Column 'name' cannot be null
    * */
    @Transactional
    @Override
    public void RollbackOnlyCanRollback() throws CustomException {
        oneSaveMethod();
        try {
            notableDao.save(new Notable());
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw ex;
        }
    }


    /**
     * 不标记事务的方法调用了标记了事务的方法,出现了异常,事务不会回滚
     *
     * 	at com.yswg.mytransactionaldemo.service.impl.MySpringTransactionServiceImpl.anotherOneSaveMethod(MySpringTransactionServiceImpl.java:116)
     at com.yswg.mytransactionaldemo.service.impl.MySpringTransactionServiceImpl.NonTransactionalCanNotRollback(MySpringTransactionServiceImpl.java:109)
     at com.yswg.mytransactionaldemo.service.impl.MySpringTransactionServiceImpl$$FastClassBySpringCGLIB$$8ee06380.invoke(<generated>)
     at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
     */
    @Override
    public void NonTransactionalCanNotRollback() {
        anotherOneSaveMethod();
    }


    @Transactional
    public void anotherOneSaveMethod() {
        notableDao.save(new Notable("不标记事务的方法调用了标记了事务的方法,出现了异常,事务不会回滚"));
        throw new RuntimeException();
    }


}
