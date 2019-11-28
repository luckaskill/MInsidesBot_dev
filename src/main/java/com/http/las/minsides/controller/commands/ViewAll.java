package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.entity.uiCommands.Commands;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.server.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

import static com.http.las.minsides.controller.entity.uiCommands.CommandNames.ADD_FILTERS;

@Component(Commands.NamesConstants.VIEW_ALL_COMMAND)
@AllArgsConstructor
public class ViewAll implements Command {
    private NotesService service;
    private OpenNoteByNumber noteByNumber;
    private MInsidesBot source;

    @Override
    public void execute(Update update) throws TelegramApiException {
        Long chatId = ChatUtil.getChatId(update);
        byte[] key = SessionUtil.getKey(update);
        List<Note> allNotes = service.getAllNotes(chatId, key);
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (Note note : allNotes) {
            builder.append("/")
                    .append(count++)
                    .append(note.toShortString());
        }

        if (builder.length() == 0) {
            ChatUtil.sendMsg(Messages.NOTHING_TO_SHOW, update, source);
        } else {
            String msg = builder.append('\n').toString();
            ChatUtil.sendMsg(msg, update, source);
            ChatUtil.sendMsg(Messages.PRINT_NOTE_NUMBER, update, source);
            SessionUtil.setUserNotes(update, allNotes);
            SessionUtil.setNextCommand(update, noteByNumber);

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
}
