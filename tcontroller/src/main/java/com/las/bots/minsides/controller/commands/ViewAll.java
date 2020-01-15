package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.ButtonKeyboardData;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.server.notes.service.NotesService;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.util.EntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import static com.las.bots.minsides.controller.entity.uiCommands.CommandName.NamesConstants.ADD_FILTERS;


@Component(CommandName.NamesConstants.VIEW_ALL_COMMAND)
@AllArgsConstructor
public class ViewAll implements Command {
    private NotesService service;
    private OpenNoteByNumber noteByNumber;
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        byte[] key = update.getKey();
        List<Note> allNotes = service.getAllNotes(chatId, key);
        StringBuilder builder = new StringBuilder();
        try {
            List<String> shortNotes = EntityUtil.readEntityValues(Note.TO_SHORT_STRING_METHOD, allNotes);
            String numberedList = ChatUtil.buildSlashedNumList(shortNotes);
            builder.append(numberedList);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new TelegramApiException(e);
        }

        if (builder.length() == 0) {
            ChatUtil.sendMsg(Messages.NOTHING_TO_SHOW, update, source);
        } else {
            String msg = builder.append('\n').toString();
            ChatUtil.sendMsg(msg, update, source);
            ChatUtil.sendMsg(Messages.PRINT_NOTE_NUMBER, update, source);
            update.setUserNotes(allNotes);
            update.setNextCommand(noteByNumber);

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
