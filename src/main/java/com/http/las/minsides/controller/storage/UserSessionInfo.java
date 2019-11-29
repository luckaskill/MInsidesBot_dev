package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.commands.CreateUserSession;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.http.las.minsides.controller.entity.Messages.PRE_START;

@Getter
class UserSessionInfo {
    private long chatId;
    @Setter
    private Note noteInCreation;
    @Setter
    private List<Note> notes;
    @Setter
    private List<NoteType> noteTypes;
    @Setter
    private NoteType typeToSave;
    @Setter
    private Command nextCommand;
    @Setter
    private byte[] key;
    @Setter
    private long timeOut = 10_800_000L;
    private long startTime;

    public UserSessionInfo(long chatId, byte[] key) {
        this.chatId = chatId;
        this.key = key;
        refreshTimeout();
    }

    public UserSessionInfo(long chatId) {
        this.chatId = chatId;
        refreshTimeout();
    }

    public boolean timeOuted() {
        long curTime = System.currentTimeMillis();
        long timeLeft = curTime - startTime;
        boolean out = timeLeft >= timeOut;
        return out;
    }

    byte[] getKey() {
        return key;
    }

    boolean hasKey() {
        boolean hasKey = key != null;
        return hasKey;
    }

    void refreshTimeout() {
        startTime = System.currentTimeMillis();
    }
}
