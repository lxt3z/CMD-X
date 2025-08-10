package su.lxt3z.cmdx;

import su.lxt3z.cmdx.commands.CmdManagerCommand;
import su.lxt3z.cmdx.config.ConfigManager;
import su.lxt3z.cmdx.listeners.CommandListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);

        Objects.requireNonNull(getCommand("cmdx")).setExecutor(new CmdManagerCommand(this));

        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        getLogger().info("CMD-X успешно включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CMD-X успешно выключен!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}