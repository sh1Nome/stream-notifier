package com.rekid.streamnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@SpringBootApplication
public class StreamnotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamnotifierApplication.class, args);
	}

	@Bean
	public JDA jda() {
		return JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN")).build();
	}

}
