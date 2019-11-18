package com.http.las.minsides.controller.storage;

import com.http.las.minsides.controller.Command;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.shared.entity.Note;
import com.http.las.minsides.shared.entity.NoteType;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public class SessionUtil {
    private static final HashMap<Long, UserSessionInfo> SESSIONS = new HashMap<>();

    public static Command getNextCommand(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        Command plannedHandler = sessionInfo.getNextCommand();
        return plannedHandler;
    }

    public static void setNextCommand(Update update, Command command) {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNextCommand(command);
    }

    public static void clearNextCommand(Update update) {
        setNextCommand(update, null);
    }

    public static boolean hasNextCommand(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        boolean hasNextCommand = sessionInfo.getNextCommand() != null;
        return hasNextCommand;
    }

    public static NoteType getNoteTypeToSave(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        NoteType typeToSave = sessionInfo.getTypeToSave();
        return typeToSave;
    }

    public static void setUserTypeToSave(Update update, NoteType typeToSave) {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setTypeToSave(typeToSave);
    }

    public static void setUserNotesTypes(Update update, List<NoteType> noteTypes) {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNoteTypes(noteTypes);
    }

    public static List<NoteType> getUserNoteTypes(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        List<NoteType> noteTypes = sessionInfo.getNoteTypes();
        return noteTypes;
    }

    public static void setUserNotes(Update update, List<Note> notes) {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNotes(notes);
    }

    public static List<Note> getUserNotes(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        List<Note> notes = sessionInfo.getNotes();
        return notes;
    }

    public static Note setNewCreationNote(Update update) {
        UserSessionInfo session = getSession(update);
        Note note = new Note();
        session.setNoteInCreation(note);
        return note;
    }


    public static Note getOrPutInCreationNote(Update update) {
        UserSessionInfo sessionInfo = getSession(update);

        Note noteInCreation = sessionInfo.getNoteInCreation();
        if (noteInCreation == null) {
            Note newNote = new Note();
            sessionInfo.setNoteInCreation(newNote);
            noteInCreation = newNote;
        }

        return noteInCreation;
    }

    public static void removeFromCreation(Update update) {
        UserSessionInfo sessionInfo = getSession(update);
        sessionInfo.setNoteInCreation(null);
    }

    public static Note getCurrentEditedNote(Update update) {
        Note note = getOrPutInCreationNote(update);
        return note;
    }

    private static UserSessionInfo getOrPutUserSession(long chatId) {
        UserSessionInfo session;
        if (!SESSIONS.containsKey(chatId)) {
            session = new UserSessionInfo(chatId);
            SESSIONS.put(chatId, session);
        } else {
            session = SESSIONS.get(chatId);
        }
        return session;
    }

    private static UserSessionInfo getSession(Update update) {
        long chatId = ChatUtil.getChatId(update);
        UserSessionInfo sessionInfo = getOrPutUserSession(chatId);
        return sessionInfo;
    }
}
