package ru.rey.rhidetags.listeners;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffectType;
import ru.rey.rhidetags.Core;

import java.util.Arrays;
import java.util.Objects;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player) ||
                (((Player) event.getRightClicked()).getActivePotionEffects().stream().anyMatch( // конструкция для выявления
                        potion -> potion.getType().equals(PotionEffectType.INVISIBILITY)        // в инвизе ли игрок
                ) && Arrays.stream(((Player) event.getRightClicked()).getEquipment().getArmorContents()).noneMatch(
                        Objects::nonNull
                ) && !event.getPlayer().isOp())
        ) return;

        event.getPlayer().sendActionBar(
                LegacyComponentSerializer.legacySection().deserialize(
                        Core.message.replace("$name", ((TextComponent) ((Player) event.getRightClicked()).displayName()).content())
                )
        );
    }
}
