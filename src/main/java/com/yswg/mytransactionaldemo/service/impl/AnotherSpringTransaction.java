package com.yswg.mytransactionaldemo.service.impl;

import com.yswg.mytransactionaldemo.service.MySpringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnotherSpringTransaction {

    private final MySpringTransactionServiceImpl springTransaction;

    @Autowired
    public AnotherSpringTransaction(MySpringTransactionServiceImpl springTransaction) {
        this.springTransaction = springTransaction;
    }

    /**
     * 不同类中, 一个不标注事务的方法去调用 transactional 的方法, 事务不会失效
     *
     * java.lang.RuntimeException
     at com.yswg.mytransactionaldemo.service.impl.MySpringTransactionServiceImpl.anotherOneSaveMethod(MySpringTransactionServiceImpl.java:121)
     at com.yswg.mytransactionaldemo.service.impl.MySpringTransactionServiceImpl$$FastClassBySpringCGLIB$$8ee06380.invoke(<generated>)
     at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
     at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
     at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
     at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
     at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)

     * */
    public void TransactionalCanRollback() {

        springTransaction.anotherOneSaveMethod();
    }
}
