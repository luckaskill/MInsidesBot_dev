package com.las.bots.minsides.controller.tools;

import com.las.bots.minsides.controller.entity.ButtonKeyboardData;
import com.las.bots.minsides.shared.shared.Messages;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


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
                        new ButtonKeyboardData(CommandName.NamesConstants.ADD_NOTE_CONTENT_COMMAND, Messages.NOTE_CONTENT, true),
                        new ButtonKeyboardData(CommandName.NamesConstants.ADD_TITLE_COMMAND, Messages.TITLE, false),
                        new ButtonKeyboardData(CommandName.NamesConstants.OPEN_PEOPLE_CHOICE_PANEL_COMMAND, Messages.PEOPLE, false),
                        new ButtonKeyboardData(CommandName.NamesConstants.START_ADD_TYPE_TO_NOTE_COMMAND, Messages.TYPES, true),
                        new ButtonKeyboardData(CommandName.NamesConstants.SAVE_NOTE_COMMAND, Messages.SAVE, false)
                );
        return configureKeyboard(dataList);
    }

    public static InlineKeyboardMarkup createYesNoMarkup() {
        List<ButtonKeyboardData> data = Arrays.asList(
                new ButtonKeyboardData(Messages.YES, Messages.YES, false),
                new ButtonKeyboardData(Messages.NO, Messages.NO, false)
        );
        return configureKeyboard(data);
    }

}
