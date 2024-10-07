package com.skies.jdabotnew.listeners;

import com.skies.jdabotnew.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateOnlineStatusListener extends ListenerAdapter {

    private final KafkaProducer kafkaProducer;
    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        String user = event.getUser().getEffectiveName();
        String message = user + " updated status to " + event.getNewOnlineStatus().getKey();
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
        kafkaProducer.sendMessage(user + " updated status to " + event.getNewOnlineStatus().getKey());
    }
}
