package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.TaskManager;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.UserSessionInfo;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.entity.Note;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

import static com.http.las.minsides.controller.entity.CommandNames.ADD_FILTERS;

@Component
@AllArgsConstructor
public class ViewAll implements Command {
    private NotesService service;
    private OpenNoteByNumber noteByNumber;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        List<Note> allNotes = service.getAllNotes(chatId);
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (Note note : allNotes) {
            builder.append("/")
                    .append(count++)
                    .append('\n')
                    .append(note.toShortString());
        }
        ChatUtil.sendMsg(builder.toString(), update, source);
        ChatUtil.sendMsg("Print note number to open it.", update, source);
        UserSessionInfo.USER_NOTES.remove(chatId);
        UserSessionInfo.USER_NOTES.put(chatId, allNotes);
        TaskManager.addToQueue(noteByNumber);


        List<ButtonKeyboardData> keyboardData = Collections.singletonList(
                new ButtonKeyboardData(ADD_FILTERS, Messages.ADD_FILTERS, false));

        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(keyboardData);
        SendMessage sendMsg = new SendMessage()
                .setText("Filters?")
                .setChatId(chatId)
                .setReplyMarkup(markup);
        source.execute(sendMsg);
    }
}
