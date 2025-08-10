package su.lxt3z.cmdx.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    private static final Map<String, String> COLOR_ALIASES = new HashMap<>();
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:(#[0-9a-fA-F]{6}):(#[0-9a-fA-F]{6})>(.*?)</gradient>");

    static {
        COLOR_ALIASES.put("&", "§");
        COLOR_ALIASES.put("<red>", "§c");
        COLOR_ALIASES.put("<green>", "§a");
        COLOR_ALIASES.put("<yellow>", "§e");
        COLOR_ALIASES.put("<blue>", "§9");
        COLOR_ALIASES.put("<gold>", "§6");
    }

    public static String translateColors(String text) {
        if (text == null || text.isEmpty()) return "";

        text = processGradients(text);

        for (Map.Entry<String, String> entry : COLOR_ALIASES.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String processGradients(String text) {
        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String startColor = matcher.group(1);
            String endColor = matcher.group(2);
            String content = matcher.group(3);

            String gradient = createGradient(startColor, endColor, content);
            matcher.appendReplacement(sb, gradient);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String createGradient(String startHex, String endHex, String text) {
        if (text == null || text.isEmpty()) return "";

        int[] startRgb = hexToRgb(startHex);
        int[] endRgb = hexToRgb(endHex);

        StringBuilder result = new StringBuilder();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            int[] currentRgb = interpolateColor(startRgb, endRgb, ratio);
            String hexColor = rgbToHex(currentRgb);

            result.append("§x");
            for (char c : hexColor.toCharArray()) {
                result.append("§").append(c);
            }
            result.append(text.charAt(i));
        }

        return result.toString();
    }

    private static int[] hexToRgb(String hex) {
        return new int[] {
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        };
    }

    private static String rgbToHex(int[] rgb) {
        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }

    private static int[] interpolateColor(int[] start, int[] end, float ratio) {
        return new int[] {
                (int) (start[0] + ratio * (end[0] - start[0])),
                (int) (start[1] + ratio * (end[1] - start[1])),
                (int) (start[2] + ratio * (end[2] - start[2]))
        };
    }

    public static String replacePlaceholders(String text, String... placeholders) {
        if (placeholders.length % 2 != 0) {
            throw new IllegalArgumentException("Количество аргументов должно быть четным");
        }

        for (int i = 0; i < placeholders.length; i += 2) {
            text = text.replace(placeholders[i], placeholders[i+1]);
        }
        return text;
    }
}