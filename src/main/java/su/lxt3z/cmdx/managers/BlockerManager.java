package su.lxt3z.cmdx.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import su.lxt3z.cmdx.config.BlockerConfig;

import java.util.List;

public class BlockerManager {

    private final BlockerConfig blockerConfig;
    private final FileConfiguration mainConfig;

    public BlockerManager(BlockerConfig blockerConfig, FileConfiguration mainConfig) {
        this.blockerConfig = blockerConfig;
        this.mainConfig = mainConfig;
    }

    public boolean isCommandBlocked(String command) {
        return blockerConfig.isCommandBlocked(command);
    }

    public boolean isPlayerAllowed(Player player) {
        List<String> allowedPlayers = mainConfig.getStringList("allowed_players");
        return allowedPlayers.contains(player.getName());
    }
}