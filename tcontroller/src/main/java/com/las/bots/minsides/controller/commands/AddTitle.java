package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.shared.shared.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class AddTitle implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        Note note = update.getOrPutInCreationNote();

        String textMsg = ChatUtil.getMessageText(update);
        note.setTitle(textMsg);

        ChatUtil.sendMsg(Messages.CREATION_MAY_CONTINUE, chatId, source);
        showAddNotePanel.execute(update);
    }
}
