package com.http.las.minsides.controller;

import com.http.las.minsides.controller.commands.*;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.exception.UnknownErrorException;
import com.http.las.minsides.controller.exception.UserFriendlyException;
import com.http.las.minsides.controller.storage.SessionUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.http.las.minsides.controller.entity.uiCommands.CommandNames.*;
import static com.http.las.minsides.controller.storage.SessionUtil.getOrPutInCreationNote;

@Component
public class TaskManager {
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();
    @Autowired
    private MInsidesBot source;

    public TaskManager(SayHiToShyGuy shyGuy, ShowAddNotePanel showAddNotePanel,
                       Start start, AddNoteContent addNoteContent,
                       AddTitle addTitle, SaveNote saveNote, ViewAll viewAll,
                       SaveNewType saveNewType, OpenTypeChoicePanel openTypeChoicePanel) {
        TASK_IMPLS.put(START_COMMAND, start);
        TASK_IMPLS.put(MENU_COMMAND, start);
        TASK_IMPLS.put(SAY_HI_SHY_GUY_COMMAND, shyGuy);
        TASK_IMPLS.put(SHOW_ADD_NOTE_PANEL_COMMAND, showAddNotePanel);
        TASK_IMPLS.put(ADD_NOTE_CONTENT_COMMAND, (update) -> {
            ChatUtil.sendMsg("Print something from ur mind:", update, source);
            SessionUtil.setNextCommand(update, addNoteContent);
        });
        TASK_IMPLS.put(ADD_TITLE_COMMAND, (update) -> {
            ChatUtil.sendMsg("Print title ", update, source);
            SessionUtil.setNextCommand(update, addTitle);
        });
        TASK_IMPLS.put(SAVE_NOTE_COMMAND, saveNote);
        TASK_IMPLS.put(VIEW_ALL_COMMAND, viewAll);
        TASK_IMPLS.put(START_ADD_TYPE_TO_NOTE_COMMAND, openTypeChoicePanel);
        TASK_IMPLS.put(SAVE_NEW_TYPE, saveNewType);
        TASK_IMPLS.put(ADD_TYPE_TO_NOTE_COMMAND, (update) -> {
            Long chatId = ChatUtil.getChatId(update);
            NoteType type = SessionUtil.getNoteTypeToSave(update);
            Note note = getOrPutInCreationNote(update);
            List<NoteType> noteTypes = note.getNoteTypes();
            noteTypes.add(type);
            ChatUtil.sendMsg("Nice, now u can continue your creation", chatId, source);
            showAddNotePanel.execute(update);
        });
    }

    void impl(String input, Update update) {
        Command command = TASK_IMPLS.get(input);
        command = command == null
                ? SessionUtil.getNextCommand(update)
                : command;

        if (SessionUtil.hasNextCommand(update)) {
            SessionUtil.clearNextCommand(update);
        }
        impl(command, update);
    }


    private void impl(Command command, Update update) {
        try {
            if (command == null) {
                throw new UnknownErrorException();
            } else {
                command.execute(update);
            }
        } catch (UserFriendlyException e) {
            try {
                ChatUtil.sendMsg(e.getMessage(), update, source);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ChatUtil.sendMsg(Messages.ERROR_MESSAGE, update, source);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }

}
