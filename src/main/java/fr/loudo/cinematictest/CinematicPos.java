package fr.loudo.cinematictest;

import fr.loudo.cinematictest.utils.Location;
import fr.loudo.cinematictest.utils.MathUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class CinematicPos {

    private double t;

    private PlayerEntity player;
    private boolean isCinematic;
    private boolean eventRegistered;
    private Location firstPos;
    private Location secondPos;
    private Location currentPos;

    public CinematicPos() {
        eventRegistered = false;
    }

    public void start(PlayerEntity player, double speed) {
        this.player = player;
        isCinematic = true;
        currentPos = firstPos;
        t = 0;
        System.out.println("speed: " + speed);
        if(!eventRegistered) {
            eventRegistered = true;
            ServerTickEvents.END_SERVER_TICK.register(server -> {
                if(!isCinematic) return;
                if(t <= 1) {
                    System.out.println("t: " + t);
                    currentPos = MathUtils.getNextLocation(currentPos, secondPos, t);
                    System.out.println("currentPos: " + currentPos);
                    t += speed;
                } else {
                    stop();
                }
            });
        }
    }

    public void stop() {
        player.sendMessage(Text.of("Stopped."), false);
        t = 0;
        isCinematic = false;
    }

    public Location getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(Location firstPos) {
        this.firstPos = firstPos;
    }

    public Location getSecondPos() {
        return secondPos;
    }

    public void setSecondPos(Location secondPos) {
        this.secondPos = secondPos;
    }

    public Location getCurrentPos() {
        return currentPos;
    }

    public boolean isCinematic() {
        return isCinematic;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
