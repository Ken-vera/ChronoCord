package me.kenvera.chronocord.Command;

import me.kenvera.chronocord.ChronoCord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args[0].equalsIgnoreCase("reload")) {
            ChronoCord.getInstance().getDataManager().reloadConfig("config.yml");
            commandSender.sendMessage("§7[§bChronoCord§7] §aConfig reloaded!");
            return true;
        }
        return false;
    }
}

