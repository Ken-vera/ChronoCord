package me.kenvera.chronocord.Listener;

import me.kenvera.chronocord.ChronoCord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.awt.*;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ip")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Ip Address")
                    .addField("Java :", "mc.crazynetwork.id", false)
                    .setColor(Color.GREEN)
                    .setFooter(ChronoCord.getInstance().getDataManager().getConfig("Config.yml").get().getString("footer-text"),
                            ChronoCord.getInstance().getDataManager().getConfig("Config.yml").get().getString("footer-icon"))
                    .build();
            event.replyEmbeds(embed)
                    .setEphemeral(true)
                    .queue();
        } else {
            Bukkit.getLogger().info("Command " + event.getName() + " is used by " + event.getUser());
        }
    }
}
