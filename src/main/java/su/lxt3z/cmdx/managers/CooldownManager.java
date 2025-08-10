package su.lxt3z.cmdx.managers;

import org.bukkit.entity.Player;
import su.lxt3z.cmdx.config.CooldownConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final CooldownConfig cooldownConfig;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public CooldownManager(CooldownConfig cooldownConfig) {
        this.cooldownConfig = cooldownConfig;
    }

    public boolean hasCooldown(String command) {
        return cooldownConfig.getCooldown(command) > 0;
    }

    public long getRemainingCooldown(Player player, String command) {
        UUID uuid = player.getUniqueId();
        String cmd = command.toLowerCase();

        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(cmd)) {
            return 0;
        }

        long remaining = cooldowns.get(uuid).get(cmd) - System.currentTimeMillis();
        return remaining > 0 ? remaining : 0;
    }

    public void setCooldown(Player player, String command) {
        UUID uuid = player.getUniqueId();
        String cmd = command.toLowerCase();
        long cooldown = TimeUnit.SECONDS.toMillis(cooldownConfig.getCooldown(command));

        if (!cooldowns.containsKey(uuid)) {
            cooldowns.put(uuid, new HashMap<>());
        }

        cooldowns.get(uuid).put(cmd, System.currentTimeMillis() + cooldown);
    }

    public String formatCooldown(long milliseconds) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        return seconds + " секунд";
    }
}