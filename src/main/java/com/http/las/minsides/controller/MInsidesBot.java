package com.http.las.minsides.controller;

import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.exception.UserFriendlyException;
import com.http.las.minsides.controller.tools.ChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
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

    @Override
    public void onUpdateReceived(Update update) {
        String input = getInput(update);
        if (input != null) {
            taskManager.impl(input, update);
        }
    }

    private String getInput(Update update) {
        Message message = update.getMessage();
        String input = message != null
                ? (message.hasText() ? message.getText() : null)
                : null;

        input = input == null
                ? (update.hasCallbackQuery() ? update.getCallbackQuery().getData() : null)
                : input;
        return input;
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
