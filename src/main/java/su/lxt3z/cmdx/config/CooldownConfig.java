package su.lxt3z.cmdx.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class CooldownConfig {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private final File configFile;

    public CooldownConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "command_cooldown.yml");
    }

    public void load() {
        if (!configFile.exists()) {
            plugin.saveResource("command_cooldown.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public long getCooldown(String command) {
        return config.getLong("cooldowns." + command.toLowerCase(), 0);
    }
}