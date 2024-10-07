package com.skies.jdabotnew.commands;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandManager extends ListenerAdapter {




    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("welcome")) {
            String userTag = event.getUser().getEffectiveName();
            event.reply("Приветик, че надо, **" + userTag + "**?").queue();

        } else if (command.equals("roles")) {
            event.deferReply().queue();
            String response = "";
            for (Role role: event.getGuild().getRoles()) {
                response += role.getAsMention() + "\n";
            }
            event.getHook().sendMessage(response).queue();
        } else if (command.equals("say")) {
            OptionMapping messageOption = event.getOption("message");
            String message = messageOption.getAsString();

            event.getChannel().sendMessage(message).queue();
            event.reply("Your message was sent!").setEphemeral(true).queue();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "get welcome message"));
        commandData.add(Commands.slash("roles","показывает все роли на сервере"));
        OptionData option1 = new OptionData(OptionType.STRING, "message", "Сообщение, которое ты хочешь, чтоб бот написал", true);
        commandData.add(Commands.slash("say", "Бот напишет чето").addOptions(option1));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

}
