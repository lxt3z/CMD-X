package su.lxt3z.cmdx.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class BlockerConfig {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private final File configFile;

    public BlockerConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "command_blocker.yml");
    }

    public void load() {
        if (!configFile.exists()) {
            plugin.saveResource("command_blocker.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public List<String> getBlockedCommands() {
        return config.getStringList("blocked_commands");
    }

    public boolean isCommandBlocked(String command) {
        return getBlockedCommands().contains(command.toLowerCase());
    }
}