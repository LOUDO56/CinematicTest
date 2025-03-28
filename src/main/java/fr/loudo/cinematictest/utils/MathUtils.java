package fr.loudo.cinematictest.utils;

public class MathUtils {
    public static double lerp(double v0, double v1, double t) {
        return v0 + t * (v1 - v0);
    }

    public static Location getNextLocation(Location firstPos, Location secondPost, double t) {

        double x = lerp(firstPos.getX(), secondPost.getX(), t);
        double y = lerp(firstPos.getY(), secondPost.getY(), t);
        double z = lerp(firstPos.getZ(), secondPost.getZ(), t);
        double pitch = lerp(firstPos.getPitch(), secondPost.getPitch(), t);
        double yaw = lerp(firstPos.getYaw(), secondPost.getYaw(), t);

        return new Location(x, y, z, pitch, yaw);

    }

}
