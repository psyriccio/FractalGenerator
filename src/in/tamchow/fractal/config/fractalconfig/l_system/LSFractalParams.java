package in.tamchow.fractal.config.fractalconfig.l_system;
import in.tamchow.fractal.graphicsutilities.containers.PixelContainer;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.strings.StringManipulator;

import java.io.Serializable;
/**
 * Holds Parameters for an LS fractal
 */
public class LSFractalParams implements Serializable {
    public PixelContainer.PostProcessMode postProcessMode;
    public String path;
    String axiom;
    int width;
    int height;
    int depth;
    int init_length;
    int fore_color;
    int back_color;
    int fps;
    double init_angle;
    UnitGrammar[] grammar;
    public LSFractalParams(@NotNull LSFractalParams old) {
        setPath(old.getPath());
        setPostProcessMode(old.getPostProcessMode());
        setHeight(old.getHeight());
        setWidth(old.getWidth());
        setInit_length(old.getInit_length());
        setInit_angle(old.getInit_angle());
        setAxiom(old.getAxiom());
        setDepth(old.getDepth());
        setGrammar(old.getGrammar());
    }
    public LSFractalParams() {
        setPath("");
        setPostProcessMode(PixelContainer.PostProcessMode.NONE);
        setFps(0);
    }
    public String getAxiom() {
        return axiom;
    }
    public void setAxiom(String axiom) {
        this.axiom = axiom;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public UnitGrammar[] getGrammar() {
        return grammar;
    }
    public void setGrammar(@NotNull UnitGrammar[] grammar) {
        this.grammar = new UnitGrammar[grammar.length];
        for (int i = 0; i < this.grammar.length; i++) {
            this.grammar[i] = new UnitGrammar(grammar[i]);
        }
    }
    public double getInit_angle() {
        return init_angle;
    }
    public void setInit_angle(double init_angle) {
        this.init_angle = init_angle;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getInit_length() {
        return init_length;
    }
    public void setInit_length(int init_length) {
        this.init_length = init_length;
    }
    public PixelContainer.PostProcessMode getPostProcessMode() {
        return postProcessMode;
    }
    public void setPostProcessMode(PixelContainer.PostProcessMode postProcessMode) {
        this.postProcessMode = postProcessMode;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void fromString(@NotNull String[] data) {
        @NotNull String[] init = StringManipulator.split(data[0], ",");
        if (init.length == 7) {
            width = Integer.valueOf(init[0]);
            height = Integer.valueOf(init[1]);
            depth = Integer.valueOf(init[2]);
            axiom = init[3];
            fore_color = Integer.valueOf(init[4]);
            back_color = Integer.valueOf(init[5]);
            fps = Integer.valueOf(init[6]);
            init_length = width;
            init_angle = 0.0;
        } else if (init.length == 8) {
            width = Integer.valueOf(init[0]);
            height = Integer.valueOf(init[1]);
            depth = Integer.valueOf(init[2]);
            axiom = init[3];
            fore_color = Integer.valueOf(init[4]);
            back_color = Integer.valueOf(init[5]);
            fps = Integer.valueOf(init[6]);
            try {
                init_length = Integer.valueOf(init[7]);
                init_angle = 0.0;
            } catch (NumberFormatException nfe) {
                init_angle = Double.valueOf(init[7]);
                init_length = width;
            }
        } else if (init.length == 9) {
            width = Integer.valueOf(init[0]);
            height = Integer.valueOf(init[1]);
            depth = Integer.valueOf(init[2]);
            axiom = init[3];
            fore_color = Integer.valueOf(init[4]);
            back_color = Integer.valueOf(init[5]);
            fps = Integer.valueOf(init[6]);
            init_length = Integer.valueOf(init[7]);
            init_angle = Double.valueOf(init[8]);
        }
        grammar = new UnitGrammar[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            grammar[i - 1] = new UnitGrammar(data[i]);
        }
    }
    public int getFps() {
        return fps;
    }
    public void setFps(int fps) {
        this.fps = fps;
    }
    public int getFore_color() {
        return fore_color;
    }
    public void setFore_color(int fore_color) {
        this.fore_color = fore_color;
    }
    public int getBack_color() {
        return back_color;
    }
    public void setBack_color(int back_color) {
        this.back_color = back_color;
    }
    @Override
    public String toString() {
        String representation = width + "," + height + "," + depth + "," + axiom + "," + fore_color + "," + back_color + "," + init_length + "," + init_angle;
        for (UnitGrammar rule : grammar) {
            representation += "\n" + rule;
        }
        return representation;
    }
}