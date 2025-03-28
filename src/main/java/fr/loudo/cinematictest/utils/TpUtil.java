package fr.loudo.cinematictest.utils;

import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.EnumSet;

public class TpUtil {
    public static void teleportPlayer(ServerPlayerEntity player, double x, double y, double z) {
        ServerWorld world = player.getServerWorld();

        // Teleport the player to the new location
        player.teleport(world, x, y, z, EnumSet.noneOf(PositionFlag.class), player.getYaw(), player.getPitch(), false);
    }
}
