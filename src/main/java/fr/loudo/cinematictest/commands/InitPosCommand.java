package fr.loudo.cinematictest.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.loudo.cinematictest.CinematicTest;
import fr.loudo.cinematictest.utils.Location;
import fr.loudo.cinematictest.utils.TpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class InitPosCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("cinematic")
                .then(CommandManager.literal("addframe").executes(InitPosCommand::addKeyFrame))
                .then(CommandManager.literal("clearframes").executes(InitPosCommand::clearFrames))
                .then(CommandManager.literal("start")
                        .then(CommandManager.argument("speed", DoubleArgumentType.doubleArg())
                                .executes(context -> start(context, DoubleArgumentType.getDouble(context, "speed")))
                        )
                )
                .then(CommandManager.literal("stop").executes(InitPosCommand::stop))
        );
    }


    private static int addKeyFrame(CommandContext<ServerCommandSource> context) {

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        CinematicTest.getCinematicPos().addKeyframe(
                new Location(
                        camera.getPos().getX(),
                        camera.getPos().getY(),
                        camera.getPos().getZ(),
                        camera.getPitch(),
                        camera.getYaw()
                )
        );
        context.getSource().getPlayer().sendMessage(Text.of("Keyframe added!"), false);

        return Command.SINGLE_SUCCESS;
    }

    private static int clearFrames(CommandContext<ServerCommandSource> context) {

        CinematicTest.getCinematicPos().resetKeyFrames();
        context.getSource().getPlayer().sendMessage(Text.of("Keyframe cleared!"), false);

        return Command.SINGLE_SUCCESS;
    }

    private static int start(CommandContext<ServerCommandSource> context, double speed) {

        ServerPlayerEntity player = context.getSource().getPlayer();
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
