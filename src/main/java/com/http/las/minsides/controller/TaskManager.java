package com.http.las.minsides.controller;

import com.http.las.minsides.controller.commands.*;
import com.http.las.minsides.controller.commands.abstractCommands.AskAndWait;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.exception.WrongInputException;
import com.http.las.minsides.controller.storage.SessionUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static com.http.las.minsides.controller.entity.uiCommands.CommandNames.*;

@Component
public class TaskManager {
    private final Map<String, Command> TASK_IMPLS = new HashMap<>();

    public TaskManager(SayHiToShyGuy shyGuy, ShowAddNotePanel showAddNotePanel,
                       Start start, AddNoteContent addNoteContent, AddTitle addTitle,
                       SaveNote saveNote, ViewAll viewAll, SaveNewType saveNewType,
                       OpenTypeChoicePanel openTypeChoicePanel, AddNoteType addNoteType) {
        TASK_IMPLS.put(START_COMMAND, start);
        TASK_IMPLS.put(MENU_COMMAND, start);
        TASK_IMPLS.put(SAY_HI_SHY_GUY_COMMAND, shyGuy);
        TASK_IMPLS.put(SHOW_ADD_NOTE_PANEL_COMMAND, showAddNotePanel);
        TASK_IMPLS.put(ADD_NOTE_CONTENT_COMMAND, new AskAndWait("Print something from ur mind:", addNoteContent));
        TASK_IMPLS.put(ADD_TITLE_COMMAND, new AskAndWait("Print something from ur mind:", addTitle));
        TASK_IMPLS.put(SAVE_NOTE_COMMAND, saveNote);
        TASK_IMPLS.put(VIEW_ALL_COMMAND, viewAll);
        TASK_IMPLS.put(START_ADD_TYPE_TO_NOTE_COMMAND, openTypeChoicePanel);
        TASK_IMPLS.put(SAVE_NEW_TYPE, saveNewType);
        TASK_IMPLS.put(ADD_TYPE_TO_NOTE_COMMAND, addNoteType);
    }

    void impl(String input, Update update) throws TelegramApiException {
        Command command = TASK_IMPLS.get(input);
        command = command == null
                ? SessionUtil.getNextCommand(update)
                : command;

        if (SessionUtil.hasNextCommand(update)) {
            SessionUtil.clearNextCommand(update);
        }
        impl(command, update);
    }


    private void impl(Command command, Update update) throws TelegramApiException {
        if (command != null) {
            command.execute(update);
        } else {
            throw new WrongInputException();
        }
    }

}
