package github.ihatechpack.api.client.font;

import github.ihatechpack.api.client.event.ClientTickCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/14 11:26
 */
@OnlyIn(Dist.CLIENT)
// font is client only
public class RainbowGradationFont extends Font {

    // default use [r]Hello World[d] for matches
    public static final RainbowGradationFont Match_Instance = new RainbowGradationFont("\\[r]|\\[d]", "\\[r](.*?)\\[d]");
    public static final RainbowGradationFont Instance = new RainbowGradationFont();

    private String relpace_regex;
    private String match_regex;
    private boolean enable_match = false;

    private RainbowGradationFont(String relpace_regex, String match_regex) {
        super(Minecraft.getInstance().fontManager.createFont().fonts, true);
        this.relpace_regex = relpace_regex;
        this.match_regex = match_regex;
        this.enable_match = true;
    }

    private RainbowGradationFont() {
        super(Minecraft.getInstance().fontManager.createFont().fonts, true);
    }

    // calculate the color gradation next, with client tick counter max to 720
    public float nextColorHue(float the_step) {
        float tick = (ClientTickCounter.RainbowGradationCounter + the_step) % 720.0f;
        if (tick >= 360.0f) {
            return 720.0f - tick;
        }
        return tick;
    }

    private String pullString(FormattedCharSequence text) {
        StringBuilder fullText = new StringBuilder();
        text.accept((index, style, codePoint) -> {
            fullText.appendCodePoint(codePoint);
            return true;
        });
        return fullText.toString();
    }

    // this will render all chars
    private int renderTextRainbowGradation(FormattedCharSequence text, float x, float y, boolean isShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional) {
        // clear § chars, but the font will be performed correctly
        // text = text.replaceAll("(?i)§[0-9A-FK-OR]", "");

        // atmo package
        final float[] cur_x = {x};
        final float[] the_step = {0.0f};

        // code point <-> UTF chars
        // formated String draw in chars
        text.accept((index, style, codePoint) -> {
            String atom = Character.toString(codePoint);
            super.drawInBatch(atom, cur_x[0], y, Color.HSBtoRGB(this.nextColorHue(the_step[0]) / 100.0f, 0.8f, 0.8f), isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
            cur_x[0] += (float) this.width(atom);
            the_step[0] += 1.0f;
            return true;
        });
        return (int) cur_x[0];
    }

    private int renderTextRainbowGradation_matches(FormattedCharSequence text, float x, float y, int color, boolean isShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional) {
        final String origin = pullString(text);

        // regex and clean text
        String cleanText = origin.replaceAll(relpace_regex, "");
        boolean[] flags = new boolean[cleanText.length()]; // flags

        Pattern pattern = Pattern.compile(match_regex);
        Matcher matcher = pattern.matcher(origin);

        // this will take some time
        // so matches was one another function
        while (matcher.find()) {
            String rainbowPart = matcher.group(1);
            int startInOriginal = matcher.start();

            // calculate the index
            String beforeMatch = origin.substring(0, startInOriginal);
            int cleanStartIndex = beforeMatch.replaceAll(relpace_regex, "").length();

            // flag them all
            for (int i = 0; i < rainbowPart.length(); i++) {
                flags[cleanStartIndex + i] = true;
            }
        }

        float the_step = 0.0f;
        char[] chars = cleanText.toCharArray();
        int size = chars.length;
        for (int i = 0; i < size; i++) {
            String atom = String.valueOf(chars[i]);
            if (flags[i]) {
                // calculate rainbow rgb
                super.drawInBatch(atom, x, y, Color.HSBtoRGB(this.nextColorHue(the_step) / 100.0f, 0.8f, 0.8f), isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
                // step in
                the_step += 1.0f;
            } else {
                super.drawInBatch(atom, x, y, color, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
            }
            x += (float) this.width(atom);
        }
        return (int) x;
    }

    @Override
    public int drawInBatch(FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, DisplayMode displayMode, int backgroundColor, int packedLightCoords) {
        // cause there is no need for UTF like Arabic, bidirectional is false
        if (enable_match) {
            return renderTextRainbowGradation_matches(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, false);
        }
        return renderTextRainbowGradation(text, x, y, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, false);
    }
}
