package com.ita.u1.internetLibrary.service;

public class MailWorkAfterFiveDays implements Runnable{
    @Override
    public void run() {
        MailManagement.sendMailToReaderAfterFiveDays();
    }
}
