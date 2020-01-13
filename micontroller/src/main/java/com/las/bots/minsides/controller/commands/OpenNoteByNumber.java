package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.entity.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.las.bots.minsides.controller.tools.ChatUtil.getChatId;
import static com.las.bots.minsides.controller.tools.ChatUtil.sendMsg;

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
