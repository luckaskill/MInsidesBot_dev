package com.las.bots.minsides.controller.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ButtonKeyboardData {
    private String callbackData;
    private String text;
    private boolean isLastInDiv;
}
