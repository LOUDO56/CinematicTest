package fr.loudo.cinematictest.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.loudo.cinematictest.CinematicPos;
import fr.loudo.cinematictest.CinematicTest;
import fr.loudo.cinematictest.utils.Location;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class InitPos {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("cinematic")
                .then(CommandManager.literal("pos1").executes(InitPos::setPos1))
                .then(CommandManager.literal("pos2").executes(InitPos::setPos2))
                .then(CommandManager.literal("start")
                        .then(CommandManager.argument("speed", DoubleArgumentType.doubleArg())
                                .executes(context -> start(context, DoubleArgumentType.getDouble(context, "speed")))
                        )
                )
                .then(CommandManager.literal("stop").executes(InitPos::stop))
        );
    }


    private static int setPos1(CommandContext<ServerCommandSource> context) {

        PlayerEntity player = context.getSource().getPlayer();
        CinematicTest.getCinematicPos().setFirstPos(
                new Location(
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        player.getPitch(),
                        player.getY()
                )
        );
        player.sendMessage(Text.of("First pos set!"), false);

        return Command.SINGLE_SUCCESS;
    }

    private static int setPos2(CommandContext<ServerCommandSource> context) {

        PlayerEntity player = context.getSource().getPlayer();
        CinematicTest.getCinematicPos().setSecondPos(
                new Location(
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        player.getPitch(),
                        player.getY()
                )
        );
        player.sendMessage(Text.of("Second pos set!"), false);

        return Command.SINGLE_SUCCESS;
    }

    private static int start(CommandContext<ServerCommandSource> context, double speed) {

        PlayerEntity player = context.getSource().getPlayer();
        CinematicTest.getCinematicPos().start(player, speed);
        player.sendMessage(Text.of("Started."), false);

        return Command.SINGLE_SUCCESS;
    }

    private static int stop(CommandContext<ServerCommandSource> context) {

        PlayerEntity player = context.getSource().getPlayer();
        CinematicTest.getCinematicPos().stop();
        player.sendMessage(Text.of("Stopped."), false);

        return Command.SINGLE_SUCCESS;
    }

}
