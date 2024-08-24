package com.re_kid.discordbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class StreamNotifier {

    final static Logger logger = LoggerFactory.getLogger(StreamNotifier.class);

    public static void main(String[] args) {
        logger.info("Start StreamNotifier");

        JDA jda = JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN"))
                .build();

        logger.info("Finish StreamNotifier");
    }
}
