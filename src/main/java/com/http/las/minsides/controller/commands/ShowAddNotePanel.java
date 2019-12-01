package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.http.las.minsides.controller.entity.Messages.ADD_NOTE_PANEL_MAIN_MSG;


@Component(CommandName.NamesConstants.SHOW_ADD_NOTE_PANEL_COMMAND)
@AllArgsConstructor
public class ShowAddNotePanel implements Command {
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        InlineKeyboardMarkup markup = ButtonUtil.createAddNotePanel();

        Long chatId = update.getChatId();
        SendMessage sendMsg = new SendMessage()
                .setText(ADD_NOTE_PANEL_MAIN_MSG)
                .setChatId(chatId)
                .setReplyMarkup(markup);
        source.execute(sendMsg);
    }
}
