package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@AllArgsConstructor
public class AddTypesToNote implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;
    private AddType addType;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String typeName = ChatUtil.getMessageText(update);
        Long chatId = ChatUtil.getChatId(update);
        if (typeName == null) {
            update.setNextCommand(this);
            ChatUtil.sendMsg(Messages.NOT_LIKE_THIS, chatId, source);
            return;
        }
        try {
            int number = Integer.parseInt(typeName);
            List<NoteType> noteTypes = update.getUserNoteTypes();
            if (noteTypes != null && noteTypes.size() >= number && number > 0) {
                Note note = update.getOrPutInCreationNote();
                NoteType type = noteTypes.get(number - 1);
                List<NoteType> types = note.getNoteTypes();
                types.add(type);
                ChatUtil.sendMsg(Messages.CREATION_MAY_CONTINUE, chatId, source);
                showAddNotePanel.execute(update);
            } else {
                update.setNextCommand(this);
                ChatUtil.sendMsg(Messages.NOT_LIKE_THIS, chatId, source);
            }
        } catch (NumberFormatException e) {
            InlineKeyboardMarkup markup = ButtonUtil.createYesNoMarkup();
            update.setTypeToSave(new NoteType(typeName));
            String message = "You sure you want to save new type with name " + typeName + "?";
            SendMessage sendMsg = ChatUtil.createSendMarkup(message, chatId, markup);
            update.setNextCommand(addType);
            source.execute(sendMsg);
        }
    }
}
