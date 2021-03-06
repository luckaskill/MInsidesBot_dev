package com.las.bots.minsides.controller;

import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.shared.shared.exception.UserFriendlyException;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.shared.shared.util.EntityUtil;
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
@ComponentScan("com.las.bots.minsides")
public class MInsidesBot extends TelegramLongPollingBot {
    @Autowired
    private TaskManager taskManager;

    static {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        EntityUtil.I = 10;
        System.out.println(EntityUtil.I);
        new AnnotationConfigApplicationContext(MInsidesBot.class);
        System.out.println(EntityUtil.I);
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
        boolean sessionOk = update.initSession();
        if (sessionOk) {
            String input = ChatUtil.getInput(update);
            if (input != null) {
                taskManager.impl(input, update);
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        SessionUpdate sessionUpdate = new SessionUpdate(update, this);
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

}
