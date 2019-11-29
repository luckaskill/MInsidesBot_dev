package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
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
        String numberStr = update.hasMessage() ?
                (update.getMessage().hasText()
                        ? update.getMessage().getText() : null) : null;

        Long chatId = getChatId(update);

        try {
            if (numberStr != null && numberStr.charAt(0) == '/') {
                int number = Integer.parseInt(numberStr.substring(1));
                List<Note> notes = update.getUserNotes();
                if (notes != null) {
                    Note result = notes.get(number - 1);
                    sendMsg(result.toFullString(), chatId, source);
                } else {
                    ChatUtil.wrongInput();
                }
            } else {
                ChatUtil.wrongInput();
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            ChatUtil.wrongInput();
        }
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }
}
