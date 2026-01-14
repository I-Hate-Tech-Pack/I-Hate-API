package github.ihatechpack.api.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.function.Function;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/1/14 11:26
 */
public class NewFont extends Font {

    private static final NewFont INSTANCE = new NewFont(Minecraft.getInstance().fontManager.createFont().fonts, true);
    static int Mode = 0;
    boolean C = true;
    static boolean D = true;
    int alpha = 255;
    private static Matrix4f matrix4ff;
    private static MultiBufferSource source;
    private static boolean bb1;
    private static DisplayMode displayMode;
    private static int ii;
    private static int ii1;
    public float tick = 0.0f;

    public static NewFont getINSTANCE() {
        return INSTANCE;
    }

    public NewFont(Function<ResourceLocation, FontSet> fonts, boolean filterFishyGlyphs) {
        super(fonts, filterFishyGlyphs);
        NeoForge.EVENT_BUS.register(this);
    }

    private static long milliTime() {
        return System.nanoTime() / 1000000L;
    }

    private static double rangeRemap(double value, double low1, double high1, double low2, double high2) {
        return low2 + (value - low1) * (high2 - low2) / (high1 - low1);
    }

    private static String getString(FormattedCharSequence formattedCharSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        formattedCharSequence.accept((index, style, codePoint) -> {
            stringBuilder.appendCodePoint(codePoint);
            return true;
        });
        return stringBuilder.toString();
    }

    @SubscribeEvent
    public void tick(ClientTickEvent.Pre evt) {
        float speed = 0.5f;
        this.tick += speed;
        if (this.tick >= 720.0f) {
            this.tick = 0.0f;
        }
        if (this.alpha >= 255) {
            this.alpha -= 5;
        } else {
            this.alpha += 5;
        }

    }

    public float nextColorHue(float the_step) {
        float tick = (this.tick + the_step) % 720.0f;
        if (tick >= 360.0f) {
            return 720.0f - tick;
        }
        return tick;
    }

    private void render_text(String text,
                             float new_x,
                             float new_y,
                             int rgb,
                             boolean isShadow,
                             Matrix4f matrix,
                             MultiBufferSource buffer,
                             Font.DisplayMode displayMode,
                             int backgroundColor,
                             int packedLightCoords,
                             boolean bidirectional) {
        if (!isShadow) {
            super.drawInBatch(text, new_x, new_y, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
            super.drawInBatch(text, new_x, new_y, rgb, true, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
            super.drawInBatch(text, new_x + 0.75f, new_y + 0.75f, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
            super.drawInBatch(text, new_x, new_y, rgb, true, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
        } else if (matrix4ff != null && source != null) {
            super.drawInBatch(text, new_x, new_y, rgb, bb1, matrix4ff, source, displayMode, ii, ii1);
            super.drawInBatch(text, new_x + 0.75f, new_y + 0.75f, rgb, bb1, matrix4ff, source, displayMode, ii, ii1);
        }
    }

    private void render(String text,
                        float new_x,
                        float new_y,
                        int rgb,
                        boolean isShadow,
                        Matrix4f matrix,
                        MultiBufferSource buffer,
                        Font.DisplayMode displayMode,
                        int backgroundColor,
                        int packedLightCoords,
                        boolean bidirectional) {
        render_text(text, new_x, new_y, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
    }

    private int CoreRender(String text,
                           float x,
                           float y,
                           boolean isShadow,
                           Matrix4f matrix,
                           MultiBufferSource buffer,
                           Font.DisplayMode displayMode,
                           int backgroundColor,
                           int packedLightCoords,
                           boolean bidirectional,
                           boolean NoRender) {
        boolean r = false;
        if (!NoRender) {
            // text = text.replaceAll("(?i)§[0-9A-FK-OR]", "");
            if (Mode == 0) {
                float the_step = 0.0f;
                float centerX = x + (float) this.width(text) / 2;
                float centerY = y + (float) this.lineHeight / 2;
                float radius = 4.0f;
                float rotationSpeed = 0.1f;
                float rotationOffset = tick * rotationSpeed;
                char[] chars = text.toCharArray();
                float j = 0.0f;
                for (char c : chars) {
                    String s = String.valueOf(c);
                    j += 1.0f;
                    float angle = (2 * (float) Math.PI * j / chars.length) + rotationOffset;
                    float offset = (float) Math.sin((tick + j) * 0.1f) * 5.0f;
                    float charX = centerX + radius * (float) Math.cos(angle) + offset;
                    float charY = centerY + radius * (float) Math.sin(angle) + offset;
                    int removeInterval = 10;
                    int restoreInterval = 20;
                    if (tick % (removeInterval + restoreInterval) < removeInterval) {
                        r = true;
                    }
                    int rgb = Color.HSBtoRGB(this.nextColorHue(the_step) / 100.0f, 0.8f, 0.8f);
                    if (!r && C) {
                        render(s, charX, charY, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
                    } else if (D) {
                        render(s, x + offset, y + offset, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
                    } else {
                        render(s, x, y, rgb, isShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional);
                    }
                    x += (float) this.width(s);
                    the_step += 1.0f;
                }
            }
        } else {
            text = text.replaceAll("(?i)§[0-9A-FK-OR]", "");
            char[] chars = text.toCharArray();
            for (char c : chars) {
                x += (float) this.width(String.valueOf(c));
            }
        }
        return (int) x;
    }

    private void draw(boolean drawShadow, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource multiBufferSource, DisplayMode mode, int i, int i1) {
        bb1 = drawShadow;
        matrix4ff = matrix4f;
        source = multiBufferSource;
        displayMode = mode;
        ii = i;
        ii1 = i1;
    }


    @Override
    public int drawInBatch(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional) {
        draw(dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
        //CoreRender(text, x, y,dropShadow,matrix, buffer, displayMode, backgroundColor, packedLightCoords, bidirectional,false);
        return super.drawInBatch(text,x,y,color,dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords,bidirectional);
    }

    @Override
    public int drawInBatch(FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, DisplayMode displayMode, int backgroundColor, int packedLightCoords) {
        draw(dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
        CoreRender(getString(text), x, y,dropShadow,matrix, buffer, displayMode, backgroundColor, packedLightCoords, false,false);
        return super.drawInBatch(text,x,y,color,dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
    }

    @Override
    public int drawInBatch(Component text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, DisplayMode displayMode, int backgroundColor, int packedLightCoords) {
        draw(dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
        this.drawInBatch(text.getVisualOrderText(), x, y,color,dropShadow,matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        return super.drawInBatch(text,x,y,color,dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
    }

    @Override
    public int drawInBatch(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, DisplayMode displayMode, int backgroundColor, int packedLightCoords) {
        draw(dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
        CoreRender(text, x, y,dropShadow,matrix, buffer, displayMode, backgroundColor, packedLightCoords, false,false);
        return super.drawInBatch(text,x,y,color,dropShadow,matrix,buffer,displayMode,backgroundColor,packedLightCoords);
    }

}
