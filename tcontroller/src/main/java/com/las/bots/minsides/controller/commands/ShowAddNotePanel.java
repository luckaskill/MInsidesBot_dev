package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.las.bots.minsides.shared.shared.Messages.ADD_NOTE_PANEL_MAIN_MSG;


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
