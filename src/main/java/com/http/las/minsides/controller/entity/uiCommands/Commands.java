package com.http.las.minsides.controller.entity.uiCommands;

public enum Commands {
    ADD_TYPE_TO_NOTE_COMMAND(NamesConstants.ADD_TYPE_TO_NOTE_COMMAND);

    String commandName;

    Commands(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public static class NamesConstants {
        public static final String ADD_TYPE_TO_NOTE_COMMAND = "add_type_to_note";
//        public static final String VIEW_ALL_COMMAND = "view_all";
//        public static final String ADD_NOTE_CONTENT_COMMAND = "add_note_content";
//        public static final String ADD_PEOPLE_TO_NOTE_COMMAND = "add_note_content";
//        public static final String ADD_TITLE_COMMAND = "add_note_title";
//        public static final String SAVE_NOTE_COMMAND = "save_note";
//        public static final String START_COMMAND = "/start";
//        public static final String MENU_COMMAND = "/menu";
//        public static final String SAY_HI_SHY_GUY_COMMAND = "/say_hi_to_mihail";
//        public static final String SHOW_ADD_NOTE_PANEL_COMMAND = "show_add_note_panel";
//        public static final String ADD_OTHER_COMMAND = "add_other";
//        public static final String START_ADD_TYPE_TO_NOTE_COMMAND = "start_add_type_to_note";
//        public static final String SAVE_NEW_TYPE = "save_new_type";
//        public static final String ADD_FILTERS = "add_filters";
    }
}
