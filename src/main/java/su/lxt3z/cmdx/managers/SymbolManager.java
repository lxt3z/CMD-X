package su.lxt3z.cmdx.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import su.lxt3z.cmdx.config.SymbolConfig;

public class SymbolManager {
    private final SymbolConfig symbolConfig;
    private final FileConfiguration mainConfig;

    public SymbolManager(SymbolConfig symbolConfig, FileConfiguration mainConfig) {
        this.symbolConfig = symbolConfig;
        this.mainConfig = mainConfig;
    }

    public boolean hasBlockedSymbols(String message, Player player) {
        if (canBypassSymbolRestriction(player)) {
            return false;
        }

        for (String symbol : symbolConfig.getBlockedSymbols()) {
            if (message.contains(symbol)) {
                return true;
            }
        }
        return false;
    }

    public String getFirstBlockedSymbol(String message) {
        for (String symbol : symbolConfig.getBlockedSymbols()) {
            if (message.contains(symbol)) {
                return symbol;
            }
        }
        return null;
    }

    private boolean canBypassSymbolRestriction(Player player) {
        return mainConfig.getStringList("symbol_restrictions.bypass_players")
                .contains(player.getName());
    }
}