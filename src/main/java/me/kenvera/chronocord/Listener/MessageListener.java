package me.kenvera.chronocord.Listener;

import me.kenvera.chronocord.ChronoCord;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bukkit.Bukkit;

public class MessageListener extends ListenerAdapter {
    public void sendMessage(Long textChannelId, String message, boolean silent) {
        TextChannel textChannel = ChronoCord.getInstance().getTextChannel(textChannelId);

        if (textChannel != null) {
            MessageCreateData data = new MessageCreateBuilder()
                    .setContent(message)
                    .setSuppressedNotifications(silent)
                    .build();
            textChannel.sendMessage(data).queue();
        } else {
            Bukkit.getLogger().severe("Text channel with id " + textChannelId.toString() + " can't be found!");
        }
    }

    public void sendEmbed(Long textChannelId, MessageEmbed embed) {
        TextChannel textChannel = ChronoCord.getInstance().getTextChannel(textChannelId);

        if (textChannel != null) {
            textChannel.sendMessageEmbeds(embed).queue();
        } else {
            Bukkit.getLogger().severe("Text channel with id " + textChannelId.toString() + " can't be found!");
        }
    }

    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        User boostedUser = event.getUser();
        Guild guild = event.getGuild();

        if (guild.getId().equalsIgnoreCase("767363960701190145")) {
            System.out.println();
            System.out.println(boostedUser.getName());
            System.out.println(boostedUser.getName());
            System.out.println(boostedUser.getName());
            System.out.println(boostedUser.getName());
            System.out.println(boostedUser.getName());
            System.out.println();
        }
    }
}
