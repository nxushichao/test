package com.gkdz.server.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gkdz.server.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Objects;

/**
 * @author wu
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    SysUserEntity userEntity = null;
    TransactionStatus transactionStatus = null;
    try {
      transactionStatus = TransactionAspectSupport.currentTransactionStatus();
    } catch (NoTransactionException e) {
      log.info("No transaction");
    }
    Object savepoint = null;
    if (transactionStatus != null) {
      savepoint = transactionStatus.createSavepoint();
    }
    try {
      userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    } catch (Exception e) {
      log.info("未找到登录用户");
      if (Objects.nonNull(savepoint)) {
        TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
      }
    }
    if (userEntity != null) {
      Long userId = userEntity.getId();
      this.strictInsertFill(metaObject, "createUser", Long.class, userId);
      this.strictInsertFill(metaObject, "createBy", Long.class, userId);
    }
    this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    SysUserEntity userEntity = null;
    TransactionStatus transactionStatus = null;
    try {
      transactionStatus = TransactionAspectSupport.currentTransactionStatus();
    } catch (NoTransactionException e) {
      log.info("No transaction");
    }
    Object savepoint = null;
    if (transactionStatus != null) {
      savepoint = transactionStatus.createSavepoint();
    }
    try {
      userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    } catch (Exception e) {
      log.info("未找到登录用户");
      if (Objects.nonNull(savepoint)) {
        TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
      }
    }
    if (userEntity != null) {
      Long userId = userEntity.getId();
      this.strictUpdateFill(metaObject, "updateUser", Long.class, userId);
      this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
    }
    this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
  }
}
