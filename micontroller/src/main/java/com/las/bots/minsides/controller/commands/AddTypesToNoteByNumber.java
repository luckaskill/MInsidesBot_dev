package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.exception.WrongInputException;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@AllArgsConstructor
public class AddTypesToNoteByNumber implements Command {
    private ShowTypeChoicePanelWithBackBtn showTypeChoicePanel;
    private MInsidesBot source;
    private AddNewType addType;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String typeName = ChatUtil.getNotNullMessageText(update);
        Long chatId = update.getChatId();

        try {
            List<NoteType> userNoteTypes = update.getUserNoteTypes();
            NoteType type = ChatUtil.getByCommandNumber(userNoteTypes, typeName);
            update.addTypeToNote(type);

            showTypeChoicePanel.execute(update);
        } catch (WrongInputException e) {
            InlineKeyboardMarkup markup = ButtonUtil.createYesNoMarkup();
            update.setTypeToSave(new NoteType(typeName, chatId));
            String message = "You sure you want to save new type with name " + typeName + "?";
            SendMessage sendMsg = ChatUtil.createSendMarkup(message, chatId, markup);
            update.setNextCommand(addType);
            source.execute(sendMsg);
        }
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }
}
