package com.skies.jdabotnew.listeners;

import com.skies.jdabotnew.kafka.KafkaConfiguration;
import com.skies.jdabotnew.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageRecievedListener extends ListenerAdapter {

    private final KafkaProducer kafkaProducer;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String user = event.getMessage().getAuthor().getEffectiveName();
        String text = event.getMessage().getContentRaw();
        if (text.startsWith("m!play ")) {
            String track = text.substring(6);
            event.getChannel().sendMessage(user + " поставил трек: " + track).queue();
            kafkaProducer.sendMessage(user + " поставил трек: " + track);
        } else {
            event.getChannel().sendMessage(user + " написал " + text).queue();
            kafkaProducer.sendMessage(user + " написал " + text);
        }
    }
}