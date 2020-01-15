package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.ButtonKeyboardData;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.Messages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

import static com.las.bots.minsides.controller.entity.uiCommands.CommandName.NamesConstants.SHOW_ADD_NOTE_PANEL_COMMAND;
import static com.las.bots.minsides.controller.entity.uiCommands.CommandName.NamesConstants.VIEW_ALL_COMMAND;
import static com.las.bots.minsides.shared.shared.Messages.START_MESSAGE;

@Component(CommandName.NamesConstants.START_COMMAND)
@AllArgsConstructor
public class Start implements Command {
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        update.setNextCommand(null);
        List<ButtonKeyboardData> dataList =
                Arrays.asList(
                        new ButtonKeyboardData(VIEW_ALL_COMMAND, Messages.VIEW_ALL_NOTES, true),
                        new ButtonKeyboardData(SHOW_ADD_NOTE_PANEL_COMMAND, Messages.ADD_NEW_NOTE, false)
                );
        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(dataList);

        Long chatId = update.getChatId();
        SendMessage sendMsg = new SendMessage()
                .setText(START_MESSAGE)
                .setChatId(chatId)
                .setReplyMarkup(markup);
        source.execute(sendMsg);
    }
}
