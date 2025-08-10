package su.lxt3z.cmdx.config;

import org.bukkit.configuration.file.FileConfiguration;
import su.lxt3z.cmdx.Main;

public class ConfigManager {

    private final Main plugin;
    private final CooldownConfig cooldownConfig;
    private final BlockerConfig blockerConfig;
    private final MessagesConfig messagesConfig;
    private FileConfiguration mainConfig;
    private final SymbolConfig symbolConfig;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        this.cooldownConfig = new CooldownConfig(plugin);
        this.blockerConfig = new BlockerConfig(plugin);
        this.messagesConfig = new MessagesConfig(plugin);
        this.symbolConfig = new SymbolConfig(plugin);
        loadConfigs();
    }

    private void loadConfigs() {
        plugin.saveDefaultConfig();
        mainConfig = plugin.getConfig();
        cooldownConfig.load();
        blockerConfig.load();
        messagesConfig.load();
        symbolConfig.load();
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        mainConfig = plugin.getConfig();
        cooldownConfig.reload();
        blockerConfig.reload();
        messagesConfig.reload();
        symbolConfig.reload();
    }

    public FileConfiguration getMainConfig() {
        return mainConfig;
    }

    public CooldownConfig getCooldownConfig() {
        return cooldownConfig;
    }

    public BlockerConfig getBlockerConfig() {
        return blockerConfig;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public SymbolConfig getSymbolConfig() {
        return symbolConfig;
    }
}
