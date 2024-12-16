package com.re_kid.discordbot.presentation;

import java.util.List;
import java.util.Map;

public interface Controller {
    public void execute(Map<String,List<String>> optionAndArgs);
}
