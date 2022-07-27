package com.imall.notice.manager;


import com.imall.notice.constant.NoticeConstant;
import com.imall.notice.manager.listener.ManagerEventListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.asteriskjava.manager.ManagerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
@Slf4j
public class AsteriskManager {

    @Autowired
    private ManagerConnection managerConnection;

    @PostConstruct
    @SneakyThrows
    public void start() {
        if (NoticeConstant.producerModel) {
            managerConnection.login();
            managerConnection.addEventListener(new ManagerEventListener());
            log.info("AsteriskManager Login sucessfully...");
        }
    }

    @PreDestroy
    public void destroy() {
        if (NoticeConstant.producerModel) {
            managerConnection.logoff();
            log.info("AsteriskManager logoff sucessfully...");
        }
    }

}

