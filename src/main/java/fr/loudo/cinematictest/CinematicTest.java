package fr.loudo.cinematictest;

import fr.loudo.cinematictest.commands.InitPos;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CinematicTest implements ModInitializer {

    private static CinematicPos cinematicPos;

    @Override
    public void onInitialize() {
        cinematicPos = new CinematicPos();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            InitPos.register(commandDispatcher);
        });
    }

    public static CinematicPos getCinematicPos() {
        return cinematicPos;
    }
}
