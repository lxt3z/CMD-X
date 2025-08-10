package su.lxt3z.cmdx.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class SymbolConfig {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private final File configFile;

    public SymbolConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "symbols.yml");
        load();
    }

    public void load() {
        if (!configFile.exists()) {
            plugin.saveResource("symbols.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public List<String> getBlockedSymbols() {
        return config.getStringList("blocked_symbols");
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}