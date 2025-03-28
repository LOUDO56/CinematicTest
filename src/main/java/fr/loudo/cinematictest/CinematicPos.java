package fr.loudo.cinematictest;

import fr.loudo.cinematictest.utils.Location;
import fr.loudo.cinematictest.utils.TpUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class CinematicPos {

    private ServerPlayerEntity player;

    private boolean isCinematic;
    private boolean eventRegistered;

    private List<Location> keyFrames;
    private Location currentLoc;

    private long startTime;
    private double duration;
    private int currentIndexKeyframe;

    public CinematicPos() {
        this.keyFrames = new ArrayList<>();
        this.eventRegistered = false;
    }

    public void start(ServerPlayerEntity player, double durationInSeconds) {
        duration = durationInSeconds;
        startTime = System.nanoTime();
        currentIndexKeyframe = 0;
        this.player = player;
        isCinematic = true;
        if(!eventRegistered) {
            eventRegistered = true;
            ServerTickEvents.END_SERVER_TICK.register(server -> {
                if(!isCinematic) return;

                TpUtil.teleportPlayer(player, currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());

            });
        }
    }

    public void stop() {
        isCinematic = false;
    }

    public void addKeyframe(Location keyframe) {
        keyFrames.add(keyframe);
    }

    public void resetKeyFrames() {
        keyFrames.clear();
    }

    public boolean isCinematic() {
        return isCinematic;
    }

    public long getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration / keyFrames.size();
    }

    public List<Location> getKeyFrames() {
        return keyFrames;
    }

    public void setCurrentIndexKeyframe(int currentIndexKeyframe) {
        this.currentIndexKeyframe = currentIndexKeyframe;
    }

    public int getCurrentIndexKeyframe() {
        return currentIndexKeyframe;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setCurrentLoc(Location currentLoc) {
        this.currentLoc = currentLoc;
    }
}
