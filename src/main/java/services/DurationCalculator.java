package services;

import java.util.concurrent.TimeUnit;

/**
 * Класс для расчета и форматирования длительностей.
 */
public class DurationCalculator {

    /**
     * Форматирует длительность в секундах в формат HH:MM:SS.
     *
     * @param duration Длительность в секундах.
     * @return Отформатированная строка длительности в формате HH:MM:SS.
     */
    public static String formatDuration(long duration) {
        long hours = TimeUnit.SECONDS.toHours(duration);
        long minutes = TimeUnit.SECONDS.toMinutes(duration) % 60;
        long seconds = duration % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Суммирует две строки длительностей в формате HH:MM:SS.
     *
     * @param durationOne Первая строка длительности в формате HH:MM:SS.
     * @param durationTwo Вторая строка длительности в формате HH:MM:SS.
     * @return Сумма длительностей в виде строки в формате HH:MM:SS.
     */
    public static String sumDurations(String durationOne, String durationTwo) {
        String[] partsOne = durationOne.split(":");
        String[] partsTwo = durationTwo.split(":");

        int hours = Integer.parseInt(partsOne[0]) + Integer.parseInt(partsTwo[0]);
        int minutes = Integer.parseInt(partsOne[1]) + Integer.parseInt(partsTwo[1]);
        int seconds = Integer.parseInt(partsOne[2]) + Integer.parseInt(partsTwo[2]);

        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds %= 60;
        }

        if (minutes >= 60) {
            hours += minutes / 60;
            minutes %= 60;
        }

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
