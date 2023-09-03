package ru.rey.rhidetags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.rey.rhidetags.listeners.PlayerInteract;
import ru.rey.rhidetags.listeners.PlayerJoin;

public final class Core extends JavaPlugin {
    public static Scoreboard board;
    private static Team team;
    public static String message;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        message = getConfig().getString("message");

        this.boardSettings();

        this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        if (!this.getServer().getOnlinePlayers().isEmpty()) this.getServer().getOnlinePlayers().forEach(Core::hideName);

        this.getLogger().info("");
        this.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;118mSuccessfully enabled\033[0m");
        this.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;118mBy: \033[38;5;231mvk.com/omashune\033[0m");
        this.getLogger().info("");
    }

    public static void hideName(Player p) {
        team.addEntry(p.getName());
        p.setScoreboard(board);
    }

    private void boardSettings() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewTeam("hidenametags");

        team = board.getTeam("hidenametags");
        if(team == null) return;
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.setCanSeeFriendlyInvisibles(false);
    }
}
