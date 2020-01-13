package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class AddNewType implements Command {
    private ShowTypeChoicePanelWithBackBtn showTypeChoicePanel;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String input = ChatUtil.getInput(update);
        if (input.equals(Messages.YES)) {
            update.addTypeToSaveToCurNote();
        } else if (input.equals(Messages.NO)) {
            showTypeChoicePanel.setMessage(Messages.ALRIGHT_THEN);
        } else {
            ChatUtil.wrongInput();
        }
        showTypeChoicePanel.execute(update);
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }
}
