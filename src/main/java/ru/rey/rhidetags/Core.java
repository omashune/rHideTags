package ru.rey.rhidetags;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class Core extends JavaPlugin implements Listener {

    private Scoreboard board;

    @Override
    public void onEnable() {
        // Configuration
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Scoreboard settings
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewTeam("HideTags"); // registering a new team

        Team team = board.getTeam("HideTags");

        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER); // disabling the name tag visibility
        team.setCanSeeFriendlyInvisibles(false); // disabling the visibility of players in invisibility

        Bukkit.getPluginManager().registerEvents(this, this); // registering events

        // Add online players
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player op : Bukkit.getOnlinePlayers()) {
                board.getTeam("HideTags").addEntry(op.getName()); // adding the player to the team
                op.setScoreboard(board); // set the scoreboard for the player
            }
        }

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§6rHideTags §f| §aSuccessfully enabled!");
        Bukkit.getConsoleSender().sendMessage("§6rHideTags §f| §aBy: §fvk.com/re1khsempai!");
        Bukkit.getConsoleSender().sendMessage("");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer(); // getting the player

        board.getTeam("HideTags").addEntry(p.getName()); // adding a player to the team
        p.setScoreboard(board); // set the scoreboard for the player
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (!e.getRightClicked().getType().equals(EntityType.PLAYER)) return;

        String actionBar = getConfig().getString("actionBar")
                .replace("$name", e.getRightClicked().getName());

        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBar)); // sending him the action bar message
    }

}
