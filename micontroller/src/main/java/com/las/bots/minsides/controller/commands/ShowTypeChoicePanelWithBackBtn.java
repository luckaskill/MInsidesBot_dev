package com.las.bots.minsides.controller.commands;

import com.las.bots.minsides.controller.MInsidesBot;
import com.las.bots.minsides.controller.entity.uiCommands.CommandName;
import com.las.bots.minsides.controller.tools.ButtonUtil;
import com.las.bots.minsides.controller.tools.ChatUtil;
import com.las.bots.minsides.controller.tools.ClientBeanService;
import com.las.bots.minsides.controller.commands.abstractCommands.Command;
import com.las.bots.minsides.controller.entity.ButtonKeyboardData;
import com.las.bots.minsides.controller.storage.SessionUpdate;
import com.las.bots.minsides.shared.shared.Messages;
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
