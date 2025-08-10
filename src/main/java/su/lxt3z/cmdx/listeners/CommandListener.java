package su.lxt3z.cmdx.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import su.lxt3z.cmdx.Main;
import su.lxt3z.cmdx.config.ConfigManager;
import su.lxt3z.cmdx.config.MessagesConfig;
import su.lxt3z.cmdx.managers.BlockerManager;
import su.lxt3z.cmdx.managers.CooldownManager;
import su.lxt3z.cmdx.managers.SymbolManager;

import su.lxt3z.cmdx.utils.ColorUtils;

public class CommandListener implements Listener {
    private final CooldownManager cooldownManager;
    private final BlockerManager blockerManager;
    private final MessagesConfig messagesConfig;
    private final SymbolManager symbolManager;

    public CommandListener(Main plugin) {
        ConfigManager configManager = plugin.getConfigManager();

        this.cooldownManager = new CooldownManager(configManager.getCooldownConfig());
        this.symbolManager = new SymbolManager(
                configManager.getSymbolConfig(),
                configManager.getMainConfig()
        );
        this.blockerManager = new BlockerManager(
                configManager.getBlockerConfig(),
                configManager.getMainConfig()
        );
        this.messagesConfig = configManager.getMessagesConfig();
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String rawMessage = event.getMessage();
        String command = rawMessage.split(" ")[0].substring(1).toLowerCase();

        if (shouldBlockCommand(command, player)) {
            sendFormattedMessage(player, "blocked-command");
            event.setCancelled(true);
            return;
        }

        if (shouldBlockSymbols(rawMessage, player)) {
            event.setCancelled(true);
            return;
        }

        checkCooldown(event, player, command);
    }

    private boolean shouldBlockCommand(String command, Player player) {
        return blockerManager.isCommandBlocked(command) &&
                !blockerManager.isPlayerAllowed(player) &&
                !player.hasPermission("cmdx.cooldown.bypass");
    }

    private boolean shouldBlockSymbols(String message, Player player) {
        if (symbolManager.hasBlockedSymbols(message, player)) {
            String blockedSymbol = symbolManager.getFirstBlockedSymbol(message);
            sendFormattedMessage(player, "blocked-symbol", "%symbol%", blockedSymbol);
            return true;
        }
        return false;
    }

    private void checkCooldown(PlayerCommandPreprocessEvent event, Player player, String command) {
        if (cooldownManager.hasCooldown(command) &&
                !player.hasPermission("cmdx.cooldown.bypass")) {

            long remaining = cooldownManager.getRemainingCooldown(player, command);

            if (remaining > 0) {
                sendFormattedMessage(player, "cooldown-message",
                        "%time%", cooldownManager.formatCooldown(remaining));
                event.setCancelled(true);
            } else {
                cooldownManager.setCooldown(player, command);
            }
        }
    }

    private void sendFormattedMessage(Player player, String messageKey, String... placeholders) {
        String message = messagesConfig.getMessage(messageKey);
        message = ColorUtils.replacePlaceholders(message, placeholders);
        player.sendMessage(ColorUtils.translateColors(message));
    }
}