package com.ita.u1.internetLibrary.service;

public class FirstMailWork implements Runnable{
    @Override
    public void run() {
        MailManagement.sendMailToReaderFirstTime();
    }
}
