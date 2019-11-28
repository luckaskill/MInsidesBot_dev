package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.http.las.minsides.controller.storage.SessionUtil.getOrPutInCreationNote;

@Component
@AllArgsConstructor
public class AddTypesToNote implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;
    private AddType addType;

    @Override
    public void execute(Update update) throws TelegramApiException {
        String typeName = ChatUtil.getMessageText(update);
        Long chatId = ChatUtil.getChatId(update);
        if (typeName == null) {
            SessionUtil.setNextCommand(update, this);
            ChatUtil.sendMsg(Messages.NOT_LIKE_THIS, chatId, source);
            return;
        }
        try {
            int number = Integer.parseInt(typeName);
            List<NoteType> noteTypes = SessionUtil.getUserNoteTypes(update);
            if (noteTypes != null && noteTypes.size() >= number && number > 0) {
                Note note = getOrPutInCreationNote(update);
                NoteType type = noteTypes.get(number - 1);
                List<NoteType> types = note.getNoteTypes();
                types.add(type);
                ChatUtil.sendMsg(Messages.CREATION_MAY_CONTINUE, chatId, source);
                showAddNotePanel.execute(update);
            } else {
                SessionUtil.setNextCommand(update, this);
                ChatUtil.sendMsg(Messages.NOT_LIKE_THIS, chatId, source);
            }
        } catch (NumberFormatException e) {
            InlineKeyboardMarkup markup = ButtonUtil.createYesNoMarkup();
            SessionUtil.setTypeToSave(update, new NoteType(typeName));
            String message = "You sure you want to save new type with name " + typeName + "?";
            SendMessage sendMsg = ChatUtil.createSendMarkup(message, chatId, markup);
            SessionUtil.setNextCommand(update, addType);
            source.execute(sendMsg);
        }
    }
}
