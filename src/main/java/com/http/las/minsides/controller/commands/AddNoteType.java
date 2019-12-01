package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component(CommandName.NamesConstants.ADD_TYPE_TO_NOTE_COMMAND)
@AllArgsConstructor
public class AddNoteType implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        update.addTypeToSaveToCurNote();
        ChatUtil.sendMsg(Messages.CREATION_MAY_CONTINUE, chatId, source);
        showAddNotePanel.execute(update);

    }
}
