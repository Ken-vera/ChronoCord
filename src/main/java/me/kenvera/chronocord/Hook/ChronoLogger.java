package me.kenvera.chronocord.Hook;

import me.kenvera.chronocord.ChronoCord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Bukkit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class ChronoLogger {
    public void logToken(String playerName, String uuid, String previousToken, String currentToken, String transactionType, String transactionAmount, String issuer, Long... textChannelIds) {
        for (Long textChannelId : textChannelIds) {
            MessageEmbed embed = (new EmbedBuilder())
                    .setTitle("Token Transaction")
                    .addField("Player Name", playerName, false)
                    .addField("UUID", uuid, false)
                    .addField("Transaction Type", transactionType, true)
                    .addField("Transaction Amount", transactionAmount, true)
                    .addField("Previous Token", previousToken, true)
                    .addField("Current Token", currentToken, true)
                    .addField("Issuer", issuer, false)
                    .setThumbnail("https://minotar.net/cube/" + playerName + "/100.png")
                    .setFooter(ChronoCord.getInstance().getDataManager().getConfig("Config.yml").get().getString("footer-text"),
                            ChronoCord.getInstance().getDataManager().getConfig("Config.yml").get().getString("footer-icon"))
                    .setTimestamp(Instant.ofEpochSecond(this.longNow())).build();
            ChronoCord.getInstance().getMessageListener().sendEmbed(textChannelId, embed);
        }
    }

    public void logGroup(String playerName, String uuid, String ipAddress, String parentAdded, String parentRemoved, String inherited, String issuer, String server, Long... textChannelIds) {
        for (Long textChannelId : textChannelIds) {
            MessageEmbed embed = (new EmbedBuilder())
                    .setTitle("Group Logger")
                    .addField("Player Name", playerName, true)
                    .addField("UUID", uuid, true)
                    .addField("Ip Address", ipAddress, false)
                    .addField("Parent Added", parentAdded, true)
                    .addField("Parent Removed", parentRemoved, true)
                    .addField("Inherited", inherited, false)
                    .addField("Issuer", issuer, true)
                    .addField("Server", server, true)
                    .setThumbnail("https://minotar.net/cube/" + playerName + "/100.png")
                    .setFooter(ChronoCord.getInstance().getDataManager().getConfig("config.yml").get().getString("footer-text"),
                            ChronoCord.getInstance().getDataManager().getConfig("config.yml").get().getString("footer-icon"))
                    .setTimestamp(Instant.ofEpochSecond(this.longNow())).build();
            ChronoCord.getInstance().getMessageListener().sendEmbed(textChannelId, embed);
        }
    }

    public void logRPG(String playerName, String uuid, String ipAddress, String itemName, String itemId, String itemType, String itemTier, String server, Long... textChannelIds) {
        for (Long textChannelId : textChannelIds) {
            MessageEmbed embed = (new EmbedBuilder())
                    .setTitle("RPG Logger")
                    .addField("Player Name", playerName, true)
                    .addField("Ip Address", ipAddress, true)
                    .addField("UUID", uuid, false)
                    .addField("Item Name", itemName, false)
                    .addField("Item ID", itemId, false)
                    .addField("Item Type", itemType, false)
                    .addField("Item Tier", itemTier, true)
                    .addField("Server", server, true)
                    .setThumbnail("https://minotar.net/cube/" + playerName + "/100.png")
                    .setFooter(ChronoCord.getInstance().getDataManager().getConfig("config.yml").get().getString("footer-text"),
                            ChronoCord.getInstance().getDataManager().getConfig("config.yml").get().getString("footer-icon"))
                    .setTimestamp(Instant.ofEpochSecond(this.longNow())).build();
            ChronoCord.getInstance().getMessageListener().sendEmbed(textChannelId, embed);
        }
    }

    private String now() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId jakartaZoneId = ZoneId.of("Asia/Jakarta");
        LocalDateTime localDateTime = LocalDateTime.now(jakartaZoneId);
        return formatter.format(localDateTime);
    }

    private Long longNow() {
        ZoneId jakartaZoneId = ZoneId.of("Asia/Jakarta");
        LocalDateTime localDateTime = LocalDateTime.now(jakartaZoneId);
        return localDateTime.atZone(jakartaZoneId).toEpochSecond();
    }
}
