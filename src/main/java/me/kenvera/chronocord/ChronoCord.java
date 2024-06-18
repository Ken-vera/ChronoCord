package me.kenvera.chronocord;

import me.kenvera.chronocord.Hook.ChronoLogger;
import me.kenvera.chronocord.Listener.CommandListener;
import me.kenvera.chronocord.Listener.MessageListener;
import me.kenvera.chronocord.Manager.DataManager;
import me.kenvera.chronocore.ChronoCore;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public final class ChronoCord extends JavaPlugin {
    private static ChronoCord instance;
    public JDA jda;
    private DataManager dataManager;
    private MessageListener messageListener;
    private ChronoLogger chronoLogger;
    private CompletableFuture<String> token;

    @Override
    public void onEnable() {
        instance = this;
        Plugin chronoCorePlugin = Bukkit.getPluginManager().getPlugin("ChronoCore");

        if (chronoCorePlugin instanceof JavaPlugin) {
            ChronoCore chronoCore = (ChronoCore) chronoCorePlugin;
            this.token = chronoCore.getToken();
            Bukkit.getLogger().info("Found and hooked into ChronoCore!");
        } else {
            Bukkit.getLogger().severe("ChronoCore not found, Disabling!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        dataManager = new DataManager(this);
        messageListener = new MessageListener();
        chronoLogger = new ChronoLogger();
        saveDefaultConfig();
        try {
            connect(messageListener, new CommandListener());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Bukkit.getPluginCommand("reload").setExecutor(new Reload());

        Bukkit.getLogger().info("---------------------------");
        Bukkit.getLogger().info("ChronoCord is enabled!");
        Bukkit.getLogger().info("---------------------------");
    }

    @Override
    public void onDisable() {
        if (jda != null) {
            jda.shutdownNow();
            Bukkit.getLogger().info("Disconnected from Discord instance!");
        }
    }

    public void connect(ListenerAdapter... listener) throws InterruptedException {
        String crazynetwork = getDataManager().getConfig("config.yml").get().getString("guild-id");
        if (crazynetwork != null) {
            token.thenAccept(token -> {
                if (token != null) {
                    jda = JDABuilder.createDefault(token)
                            .enableIntents(
                                    GatewayIntent.DIRECT_MESSAGES,
                                    GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                                    GatewayIntent.DIRECT_MESSAGE_TYPING,
                                    GatewayIntent.GUILD_MESSAGES,
                                    GatewayIntent.GUILD_MESSAGE_TYPING,
                                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                                    GatewayIntent.GUILD_MEMBERS,
                                    GatewayIntent.GUILD_WEBHOOKS,
                                    GatewayIntent.GUILD_PRESENCES,
                                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                            .build();

                    try {
                        jda.awaitReady();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    jda.addEventListener(listener);

                    Guild guild = jda.getGuildById(crazynetwork);

                    guild.updateCommands().addCommands(
                            Commands.slash("ip", "CrazyNetwork Ip Address")
                    ).queue();
                    Bukkit.getLogger().info("Successfully connected to Discord instance!");
                } else {
                    Bukkit.getLogger().severe("Failed gathering token from database!");
                    Bukkit.getPluginManager().disablePlugin(this);
                }
            });
        } else {
            Bukkit.getLogger().severe("guild-id is empty, check your config file!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static ChronoCord getInstance() {
        return instance;
    }

    public JDA getJda() {
        return jda;
    }

    public TextChannel getTextChannel(Long id) {
        return jda.getTextChannelById(id);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public ChronoLogger getChronoLogger() {
        return chronoLogger;
    }
}
