package fr.loudo.cinematictest.utils;

public class Easing {

    public static double easeInOutQuart(double x) {
        return x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2;
    }

    public static double easeOutCubic(double x) {
        return 1 - Math.pow(1 - x, 3);
    }

    public static double easeOutSine(double x) {
        return Math.sin((x * Math.PI) / 2);

    }
}
