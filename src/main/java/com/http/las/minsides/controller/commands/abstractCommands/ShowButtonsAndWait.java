package com.http.las.minsides.controller.commands.abstractCommands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@AllArgsConstructor
public class ShowButtonsAndWait implements Command {
    private List<ButtonKeyboardData> keyboardData;
    private String message;
    private Command command;

    @Override
    public void execute(Update update) throws TelegramApiException {
        MInsidesBot source = ClientBeanService.getBean(MInsidesBot.class);
        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(keyboardData);
        SendMessage sendMarkup = ChatUtil.createSendMarkup(message, update, markup);
        source.execute(sendMarkup);
        SessionUtil.setNextCommand(update, command);
    }
}
