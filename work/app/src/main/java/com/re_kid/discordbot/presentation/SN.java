package com.re_kid.discordbot.presentation;

import org.slf4j.Logger;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import picocli.CommandLine.Command;

@Singleton
@Command(name = SN.NAME)
public class SN implements Runnable {

    static final String NAME = "sn";
    
    private final Logger logger;

    @Inject
    public SN(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run() {
        logger.info("sn invoked");
    }

    @Override
    public String toString() {
        return NAME;
    }

}
