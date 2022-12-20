package de.pentamuria.system.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomPlayerScoreboard {

    private final Map<UUID, CustomScoreboard> scoreboards = new HashMap<>();
    private final Map<Integer, String> defaults = new HashMap<>();
    private final String displayName;

    public CustomPlayerScoreboard(String displayName) {
        this.displayName = displayName;
    }

    public void setSidebarScore(int slot, String content) {
        if(slot<0) throw new IllegalArgumentException("slot must be > 0");
        if(slot>16) throw new IllegalArgumentException("slot must be < 16");
        scoreboards.forEach((uuid, customScoreboard)->customScoreboard.setSidebarScore(slot, content));
    }

    public void setDefaultSidebarScore(int slot, String content) {
        if(slot<0) throw new IllegalArgumentException("slot must be > 0");
        if(slot>16) throw new IllegalArgumentException("slot must be < 16");
        setSidebarScore(slot, content);

        if(content==null)defaults.remove(slot);
        else defaults.put(slot, content);
    }

    public void remove(Player player) {
        scoreboards.remove(player.getUniqueId());
    }

    public Map<UUID, CustomScoreboard> getScoreboards() {
        return scoreboards;
    }

    public CustomScoreboard getCustomScoreboard(Player player) {
        return scoreboards.computeIfAbsent(player.getUniqueId(), uuid -> {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(scoreboard);
            CustomScoreboard customScoreboard = new CustomScoreboard(displayName, scoreboard);
            defaults.forEach(customScoreboard::setSidebarScore);
            return customScoreboard;
        });
    }

}
