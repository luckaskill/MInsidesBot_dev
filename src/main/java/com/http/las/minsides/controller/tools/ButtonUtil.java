package com.http.las.minsides.controller.tools;

import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.http.las.minsides.controller.entity.Messages.*;
import static com.http.las.minsides.controller.entity.uiCommands.CommandName.NamesConstants.*;

public class ButtonUtil {
    public static InlineKeyboardMarkup configureKeyboard(Collection<ButtonKeyboardData> dataList) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        List<List<InlineKeyboardButton>> panel = new ArrayList<>();

        for (ButtonKeyboardData data : dataList) {
            InlineKeyboardButton button = new InlineKeyboardButton()
                    .setCallbackData(data.getCallbackData())
                    .setText(data.getText());

            keyboardButtons.add(button);
            if (data.isLastInDiv()) {
                panel.add(keyboardButtons);
                keyboardButtons = new ArrayList<>();
            }
        }
        if (!keyboardButtons.isEmpty() && !panel.contains(keyboardButtons)) {
            panel.add(keyboardButtons);
        }
        return markup.setKeyboard(panel);
    }


    public static InlineKeyboardMarkup createAddNotePanel() {
        List<ButtonKeyboardData> dataList =
                Arrays.asList(
                        new ButtonKeyboardData(ADD_NOTE_CONTENT_COMMAND, NOTE_CONTENT, true),
                        new ButtonKeyboardData(ADD_TITLE_COMMAND, TITLE, false),
                        new ButtonKeyboardData(ADD_PEOPLE_TO_NOTE_COMMAND, PEOPLE, false),
                        new ButtonKeyboardData(START_ADD_TYPE_TO_NOTE_COMMAND, TYPES, true),
                        new ButtonKeyboardData(SAVE_NOTE_COMMAND, SAVE, false)
                );
        return configureKeyboard(dataList);
    }

    public static InlineKeyboardMarkup createYesNoMarkup() {
        List<ButtonKeyboardData> data = Arrays.asList(
                new ButtonKeyboardData(YES, YES, false),
                new ButtonKeyboardData(NO, NO, false)
        );
        return configureKeyboard(data);
    }

}
