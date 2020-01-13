package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.Messages;
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
