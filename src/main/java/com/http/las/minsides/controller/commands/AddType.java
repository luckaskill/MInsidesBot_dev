package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class AddType implements Command {
    private ShowAddNotePanel showAddNotePanel;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String input = ChatUtil.getInput(update);
        if (input.equals(Messages.YES)) {
            update.addTypeToSaveToCurNote();
            showAddNotePanel.execute(update);
        } else if (input.equals(Messages.NO)) {
            showAddNotePanel.execute(update);
        } else {
            update.setNextCommand(this);
            ChatUtil.wrongInput();
        }
    }
}
