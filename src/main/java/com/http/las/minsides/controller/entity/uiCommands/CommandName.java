package com.http.las.minsides.controller.entity.uiCommands;

public enum CommandName {
    ADD_TYPE_TO_NOTE_COMMAND(NamesConstants.ADD_TYPE_TO_NOTE_COMMAND),
    VIEW_ALL_COMMAND(NamesConstants.VIEW_ALL_COMMAND),
    ADD_NOTE_CONTENT_COMMAND(NamesConstants.ADD_NOTE_CONTENT_COMMAND, false),
    ADD_PEOPLE_TO_NOTE_COMMAND(NamesConstants.ADD_PEOPLE_TO_NOTE_COMMAND, false),
    ADD_TITLE_COMMAND(NamesConstants.ADD_TITLE_COMMAND, false),
    SAVE_NOTE_COMMAND(NamesConstants.SAVE_NOTE_COMMAND),
    START_COMMAND(NamesConstants.START_COMMAND),
    SAY_HI_SHY_GUY_COMMAND(NamesConstants.SAY_HI_SHY_GUY_COMMAND),
    SHOW_ADD_NOTE_PANEL_COMMAND(NamesConstants.SHOW_ADD_NOTE_PANEL_COMMAND),
    START_ADD_TYPE_TO_NOTE_COMMAND(NamesConstants.START_ADD_TYPE_TO_NOTE_COMMAND),
    SAVE_NEW_TYPE(NamesConstants.SAVE_NEW_TYPE),
    ADD_FILTERS(NamesConstants.ADD_FILTERS),
    ;

    public static class NamesConstants {
        public static final String ADD_TYPE_TO_NOTE_COMMAND = "add_type_to_note";
        public static final String VIEW_ALL_COMMAND = "/view";
        public static final String ADD_NOTE_CONTENT_COMMAND = "add_note_content";
        public static final String ADD_PEOPLE_TO_NOTE_COMMAND = "add_people_content";
        public static final String ADD_TITLE_COMMAND = "add_note_title";
        public static final String SAVE_NOTE_COMMAND = "save_note";
        public static final String START_COMMAND = "/start";
        public static final String SAY_HI_SHY_GUY_COMMAND = "/say_hi_to_mihail";
        public static final String SHOW_ADD_NOTE_PANEL_COMMAND = "/add_new";
        public static final String START_ADD_TYPE_TO_NOTE_COMMAND = "start_add_type_to_note";
        public static final String SAVE_NEW_TYPE = "save_new_type";
        public static final String ADD_FILTERS = "add_filters";
    }

    private final String commandName;
    private final boolean autoCommand;

    CommandName(String commandName) {
        this.commandName = commandName;
        autoCommand = true;
    }

    CommandName(String commandName, boolean autoCommand) {
        this.commandName = commandName;
        this.autoCommand = autoCommand;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isAutoCommand() {
        return autoCommand;
    }

    @Override
    public String toString() {
        return commandName;
    }
}
