package com.re_kid.discordbot.presentation.command;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.re_kid.discordbot.presentation.Controller;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class LangController implements Controller {

    private final Logger logger;

    @Inject
    public LangController(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void execute(Map<String,List<String>> optionAndArgs) {
        logger.info("lang invoked");
    }

}
