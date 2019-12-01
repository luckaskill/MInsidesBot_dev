package com.http.las.minsides.controller.commands;

import com.http.las.minsides.controller.MInsidesBot;
import com.http.las.minsides.controller.commands.abstractCommands.Command;
import com.http.las.minsides.controller.entity.ButtonKeyboardData;
import com.http.las.minsides.controller.entity.Messages;
import com.http.las.minsides.controller.entity.uiCommands.CommandName;
import com.http.las.minsides.controller.storage.SessionUpdate;
import com.http.las.minsides.controller.tools.ButtonUtil;
import com.http.las.minsides.controller.tools.ChatUtil;
import com.http.las.minsides.controller.tools.ClientBeanService;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Component
public class ShowTypeChoicePanelWithBackBtn implements Command {
    private MInsidesBot source;
    @Setter
    private String message;

    public ShowTypeChoicePanelWithBackBtn(MInsidesBot source) {
        this.source = source;
        setMessageToDefault();
    }

    @Override
    public void execute(SessionUpdate update) throws TelegramApiException {
        Long chatId = update.getChatId();
        InlineKeyboardMarkup markup = ButtonUtil.configureKeyboard(Collections.singletonList(
                new ButtonKeyboardData(CommandName.NamesConstants.SHOW_ADD_NOTE_PANEL_COMMAND, Messages.BACK, true)));
        SendMessage sendMarkup = ChatUtil.createSendMarkup(message, chatId, markup);

        Command openTypeChoicePanel = ClientBeanService.getBean(OpenTypeChoicePanel.class);
        openTypeChoicePanel.execute(update);
        source.execute(sendMarkup);
        setMessageToDefault();
    }

    private void setMessageToDefault() {
        message = Messages.ADDED;
    }
}
