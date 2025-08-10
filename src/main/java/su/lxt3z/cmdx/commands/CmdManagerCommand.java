package su.lxt3z.cmdx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import su.lxt3z.cmdx.Main;
import su.lxt3z.cmdx.config.MessagesConfig;
import su.lxt3z.cmdx.utils.ColorUtils;

public class CmdManagerCommand implements CommandExecutor {
    private final Main plugin;
    private final MessagesConfig messages;

    public CmdManagerCommand(Main plugin) {
        this.plugin = plugin;
        this.messages = plugin.getConfigManager().getMessagesConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || !sender.hasPermission("cmdx.admin")) {
            sendColoredMessage(sender, "usage.cmdmanager");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reloadConfigs();
            sendColoredMessage(sender, "config-reloaded");
            return true;
        }

        return false;
    }

    private void sendColoredMessage(CommandSender sender, String messageKey) {
        String message = messages.getMessage(messageKey);
        sender.sendMessage(ColorUtils.translateColors(message));
    }
}