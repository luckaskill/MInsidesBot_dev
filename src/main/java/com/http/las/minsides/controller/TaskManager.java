package com.http.las.minsides.controller;

import com.http.las.minsides.controller.commands.*;
import com.http.las.minsides.controller.storage.UserSessionInfo;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.entity.Note;
import com.http.las.minsides.entity.NoteType;
import com.http.las.minsides.server.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static com.http.las.minsides.controller.entity.CommandNames.*;
import static com.http.las.minsides.controller.tools.StockUtil.getOrPutInCreationNote;

@Component
public class TaskManager {
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();
    private static final Deque<Command> HANDLERS_QUEUE = new LinkedList<>();
    @Autowired
    private MInsidesBot source;

    @Autowired
    private NotesService service;

    public TaskManager(SayHiToShyGuy shyGuy, ShowAddNotePanel showAddNotePanel,
                       Start start, AddNoteContent addNoteContent,
                       AddTitle addTitle, SaveNote saveNote,
                       ViewAll viewAll, AddTypesToNote addTypesToNote, SaveNewType saveNewType) {
        TASK_IMPLS.put(START_COMMAND, start);
        TASK_IMPLS.put(MENU_COMMAND, start);
        TASK_IMPLS.put(SAY_HI_SHY_GUY_COMMAND, shyGuy);
        TASK_IMPLS.put(SHOW_ADD_NOTE_PANEL_COMMAND, showAddNotePanel);
        TASK_IMPLS.put(ADD_NOTE_CONTENT_COMMAND, (update) -> {
            ChatUtil.sendMsg("Print something from ur mind:", update, source);
            HANDLERS_QUEUE.addLast(addNoteContent);
        });
        TASK_IMPLS.put(ADD_TITLE_COMMAND, (update) -> {
            ChatUtil.sendMsg("Print title ", update, source);
            HANDLERS_QUEUE.addLast(addTitle);
        });
        TASK_IMPLS.put(SAVE_NOTE_COMMAND, saveNote);
        TASK_IMPLS.put(VIEW_ALL_COMMAND, viewAll);
        TASK_IMPLS.put(START_ADD_TYPE_TO_NOTE_COMMAND, (update) -> {
            Long chatId = ChatUtil.getChatId(update);
            StringBuilder builder = new StringBuilder("Print new type name");
            List<NoteType> types = service.getUserNoteTypes(chatId);
            if (!types.isEmpty()) {
                builder.append(" or choose from existing: \n");
                int i = 1;
                for (NoteType type : types) {
                    builder.append(i++).append(". ").append(type.getTypeName()).append("\n");
                }
            }
            ChatUtil.sendMsg(builder.toString(), update, source);
            UserSessionInfo.USER_NOTE_TYPES.remove(chatId);
            UserSessionInfo.USER_NOTE_TYPES.put(chatId, types);
            HANDLERS_QUEUE.addLast(addTypesToNote);
        });
        TASK_IMPLS.put(SAVE_NEW_TYPE, saveNewType);
        TASK_IMPLS.put(ADD_TYPE_TO_NOTE_COMMAND, (update) -> {
            Long chatId = ChatUtil.getChatId(update);
            NoteType type = UserSessionInfo.TYPES_TO_SAVE.get(chatId);
            Note note = getOrPutInCreationNote(chatId);
            List<NoteType> noteTypes = note.getNoteTypes();
            noteTypes.add(type);
            ChatUtil.sendMsg("Nice, now u can continue your creation", chatId, source);
            showAddNotePanel.execute(update);
        });
    }

    void impl(String input, Update update) throws TelegramApiException {
        Command command = TASK_IMPLS.get(input);
        command = command == null
                ? (HANDLERS_QUEUE.size() > 0 ? takeCommandFromQueue() : null)
                : command;
        if (command != null) {
            command.execute(update);
        }
    }

    private Command takeCommandFromQueue() {
        Command command = HANDLERS_QUEUE.getLast();
        HANDLERS_QUEUE.removeLast();
        return command;
    }

    public static void clearQueue() {
        HANDLERS_QUEUE.clear();
    }

    public static void addToQueue(Command command) {
        HANDLERS_QUEUE.addLast(command);
    }
    public static void removeFromQueue(Command command) {
        HANDLERS_QUEUE.remove(command);
    }

}
