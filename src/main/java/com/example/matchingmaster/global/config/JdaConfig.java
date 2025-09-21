package com.example.matchingmaster.global.config;

import com.example.matchingmaster.global.command.MatchCreateMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JdaConfig {
    private final MatchCreateMessageHandler handler;

    @Bean
    public JDA jda(@Value("${discord.token}") String token) throws Exception {
        return JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(handler)
                .build()
                .awaitReady();
    }
}
