package in.tamchow.fractal.platform_tools;
import in.tamchow.fractal.graphicsutilities.containers.Animation;
import in.tamchow.fractal.graphicsutilities.containers.PixelContainer;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.strings.StringManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Handles platform conversions of images
 */
public class ImageConverter {
    @NotNull
    public static BufferedImage[] animationFrames(@NotNull Animation animation) {
        @NotNull BufferedImage[] frames = new BufferedImage[animation.getNumFrames()];
        for (int i = 0; i < frames.length; ++i) {
            frames[i] = toImage(animation.getFrame(i));
        }
        return frames;
    }
    @NotNull
    public static BufferedImage toImage(@NotNull PixelContainer img) {
        return toImage(img, 0, 0, img.getWidth(), img.getHeight());
    }
    @NotNull
    public static BufferedImage toImage(@NotNull PixelContainer img, int startx, int starty, int endx, int endy) {
        @NotNull BufferedImage buf = new BufferedImage(endx - startx, endy - starty, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < buf.getHeight(); ++i) {
            for (int j = 0; j < buf.getWidth(); ++j) {
                if ((j + startx) > endx || (i + starty) > endy) {
                    break;
                }
                buf.setRGB(j, i, img.getPixel(starty + i, startx + j));
            }
        }
        return buf;
    }
    @NotNull
    public static Animation framesAsAnimation(@NotNull BufferedImage[] frames, int fps) {
        @NotNull Animation animation = new Animation(fps);
        for (@NotNull BufferedImage frame : frames) {
            animation.addFrame(toPixelContainer(frame));
        }
        return animation;
    }
    @NotNull
    public static PixelContainer toPixelContainer(@NotNull Image img) {
        return toPixelContainer(img, 0, 0, img.getWidth(null), img.getHeight(null));
    }
    @NotNull
    public static PixelContainer toPixelContainer(Image img, int startx, int starty, int endx, int endy) {
        @NotNull BufferedImage buf = new BufferedImage(endx - startx, endy - starty, BufferedImage.TYPE_INT_ARGB);
        buf.getGraphics().drawImage(img, 0, 0, buf.getWidth(), buf.getHeight(), startx, starty, endx, endy, null);
        @NotNull PixelContainer pixelContainer = new PixelContainer(buf.getWidth(), buf.getHeight());
        for (int i = 0; i < pixelContainer.getHeight(); ++i) {
            for (int j = 0; j < pixelContainer.getWidth(); ++j) {
                pixelContainer.setPixel(i, j, buf.getRGB(j, i));
            }
        }
        return pixelContainer;
    }
    @NotNull
    public static PixelContainer drawTextToPixelContainer(String text, String fontName, int fontStyle, int penColor, int backColor, int fontSize, int x, int y, int width, int height) {
        return toPixelContainer(drawTextToImage(text, fontName, fontStyle, penColor, backColor, fontSize, x, y, width, height));
    }
    @NotNull
    public static BufferedImage drawTextToImage(String text, String fontName, int fontStyle, int penColor, int backColor, int fontSize, int x, int y, int width, int height) {
        @NotNull BufferedImage buf = new BufferedImage(fontSize * width, fontSize * height, BufferedImage.TYPE_INT_ARGB);
        @NotNull Graphics2D graphics = buf.createGraphics();
        graphics.setBackground(new Color(backColor));
        graphics.setColor(new Color(penColor));
        //graphics.setStroke(new BasicStroke(fontSize));
        graphics.setFont(new Font(fontName, fontStyle, fontSize));
        //graphics.fillRect(0, 0, buf.getWidth(), buf.getHeight());
        graphics.clearRect(0, 0, buf.getWidth(), buf.getHeight());
        drawString(graphics, text, x, y);
        graphics.dispose();
        return buf;
    }
    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : StringManipulator.split(text, "\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }
}