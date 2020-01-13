package com.las.bots.minsides.controller.storage;

import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.shared.shared.entity.Note;
import com.las.bots.minsides.shared.shared.entity.NoteType;
import com.las.bots.minsides.shared.shared.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<Person> userPeople;
    @Setter
    private NoteType typeToSave;
    @Setter
    private Person personToSave;
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
