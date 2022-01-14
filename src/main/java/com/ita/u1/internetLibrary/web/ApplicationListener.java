package com.ita.u1.internetLibrary.web;

import com.ita.u1.internetLibrary.service.FirstMailWork;
import com.ita.u1.internetLibrary.service.MailWorkAfterFiveDays;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ApplicationListener implements ServletContextListener{

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new FirstMailWork(), 0, 1, TimeUnit.DAYS);
        scheduler.scheduleAtFixedRate(new MailWorkAfterFiveDays(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
