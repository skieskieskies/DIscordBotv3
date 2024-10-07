package com.skies.jdabotnew.configurator;

import com.skies.jdabotnew.commands.CommandManager;
import com.skies.jdabotnew.listeners.MessageRecievedListener;
import com.skies.jdabotnew.listeners.UserUpdateOnlineStatusListener;
import com.skies.jdabotnew.service.MessageCreateService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RequiredArgsConstructor
public class BotConfigurator {

    @Value("${token}")
    public String token;

    private JDA jda;

    private final MessageRecievedListener messageRecievedListener;
    private final UserUpdateOnlineStatusListener userUpdateOnlineStatusListener;
    private final CommandManager commandManager;


    @Bean
    public JDA createBot() {
        jda = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("как ты прожигаешь свою жизнь=)"))
                .addEventListeners(messageRecievedListener, userUpdateOnlineStatusListener, commandManager)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .build();
        return jda;
    }
}


