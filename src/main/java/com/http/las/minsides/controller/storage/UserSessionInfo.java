package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
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
    private NoteType typeToSave;
    @Setter
    private Command nextCommand;

    UserSessionInfo(long chatId) {
        this.chatId = chatId;
    }

}
