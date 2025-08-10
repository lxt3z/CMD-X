package su.lxt3z.cmdx.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessagesConfig {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private final File configFile;

    public MessagesConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "messages.yml");
    }

    public void load() {
        if (!configFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getMessage(String path) {
        return config.getString(path, "&cСообщение не найдено: " + path);
    }
}
