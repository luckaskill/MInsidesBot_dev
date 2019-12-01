package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.http.las.minsides.controller.tools.ChatUtil.getChatId;
import static com.http.las.minsides.controller.tools.ChatUtil.sendMsg;

@Component
@AllArgsConstructor
public class OpenNoteByNumber implements Command {
    private MInsidesBot source;

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        String numberStr = ChatUtil.getNotNullMessageText(update);
        Long chatId = getChatId(update);

        List<Note> userNotes = update.getUserNotes();
        Note requestedNote = ChatUtil.getByCommandNumber(userNotes, numberStr);
        sendMsg(requestedNote.toFullString(), chatId, source);
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }
}
