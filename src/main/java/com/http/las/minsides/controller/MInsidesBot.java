package com.http.las.minsides.controller;

import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.exception.UserFriendlyException;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.exceptions.StartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
@ComponentScan("com.http.las.minsides")
//@PropertySource("classpath:application.properties")
public class MInsidesBot extends TelegramLongPollingBot {
    @Autowired
    private TaskManager taskManager;

    static {
        try {
            StartAppUtil.preStart();
        } catch (StartException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(MInsidesBot.class);
    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void impl(SessionUpdate update) throws TelegramApiException {
        boolean sessionOk = update.checkSession(update);
        if (sessionOk) {
            String input = ChatUtil.getInput(update);
            if (input != null) {
                taskManager.impl(input, update);
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        SessionUpdate sessionUpdate = new SessionUpdate(update);
        try {
            impl(sessionUpdate);
        } catch (UserFriendlyException e) {
            try {
                e.printStackTrace();
                ChatUtil.sendMsg(e.getMessage(), update, this);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ChatUtil.sendMsg(Messages.ERROR_MESSAGE, update, this);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "MInsides";
    }

    @Override
    public String getBotToken() {
        return "912798529:AAHpUJtCVVLdZBo3HWO8Y5jIci50JtXSGzs";
    }


    private void initBackgroundProcessing() {
//        new Thread(() -> {
//            for()
//        })

    }
}
