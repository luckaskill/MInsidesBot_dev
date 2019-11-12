package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.TaskManager;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.storage.UserSessionInfo;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

import static com.http.las.minsides.controller.entity.CommandNames.ADD_TYPE_TO_NOTE_COMMAND;
import static com.http.las.minsides.controller.entity.CommandNames.SHOW_ADD_NOTE_PANEL_COMMAND;
import static com.http.las.minsides.controller.tools.StockUtil.getOrPutInCreationNote;

@Component
@AllArgsConstructor
public class AddTypesToNote implements Command {
    private ShowAddNotePanel showAddNotePanel;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        String typeName = ChatUtil.getMessage(update);
        Long chatId = ChatUtil.getChatId(update);
        if (typeName == null) {
            TaskManager.addToQueue(this);
            ChatUtil.sendMsg("Not like this... try again", chatId, source);
            return;
        }
        try {
            int number = Integer.parseInt(typeName);
            List<NoteType> noteTypes = UserSessionInfo.USER_NOTE_TYPES.get(chatId);
            if (noteTypes != null && noteTypes.size() > number) {
                Note note = getOrPutInCreationNote(chatId);
                NoteType type = noteTypes.get(number);
                List<NoteType> types = note.getNoteTypes();
                types.add(type);
                ChatUtil.sendMsg("Nice, now u can continue your creation", chatId, source);
                showAddNotePanel.execute(update);
            } else {
                TaskManager.addToQueue(this);
                ChatUtil.sendMsg("Not like this... try again", chatId, source);
            }
        } catch (NumberFormatException e) {
            List<ButtonKeyboardData> data = Arrays.asList(
                    new ButtonKeyboardData(ADD_TYPE_TO_NOTE_COMMAND, "Yes", false),
                    new ButtonKeyboardData(SHOW_ADD_NOTE_PANEL_COMMAND, "No", false)
            );
            UserSessionInfo.TYPES_TO_SAVE.put(chatId, new NoteType().setTypeName(typeName));
            InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(data);
            SendMessage sendMsg = new SendMessage()
                    .setText("You sure you want to save new type with name " + typeName + " ?")
                    .setChatId(chatId)
                    .setReplyMarkup(markup);
            source.execute(sendMsg);
        }
    }
}
