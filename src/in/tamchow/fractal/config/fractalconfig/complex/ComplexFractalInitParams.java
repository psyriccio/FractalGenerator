package in.tamchow.fractal.config.fractalconfig.complex;
import in.tamchow.fractal.color.Colorizer;
import in.tamchow.fractal.color.Colors;
import in.tamchow.fractal.config.DataFromString;
import in.tamchow.fractal.fractals.complex.ComplexFractalGenerator;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.annotations.Nullable;
import in.tamchow.fractal.helpers.strings.StringManipulator;
import in.tamchow.fractal.math.complex.Complex;

import java.io.Serializable;

import static in.tamchow.fractal.config.Strings.BLOCKS.ENDINIT;
import static in.tamchow.fractal.config.Strings.BLOCKS.INIT;
import static in.tamchow.fractal.config.Strings.DECLARATIONS.*;
/**
 * Parameters for configuring the initialization of a fractal
 */
public class ComplexFractalInitParams implements Serializable, DataFromString {
    @Nullable
    public String function, variableCode, oldvariablecode, linetrap;
    public String[][] consts;
    public int width, height, switch_rate;
    public ComplexFractalGenerator.Mode fractal_mode;
    public double tolerance, zoom, zoom_factor, base_precision, skew;
    public Complex trap_point;
    public Colorizer color;
    public ComplexFractalInitParams(@NotNull ComplexFractalInitParams initParams) {
        initParams(initParams.width, initParams.height, initParams.zoom, initParams.zoom_factor, initParams.base_precision, initParams.fractal_mode, initParams.function, initParams.consts, initParams.variableCode, initParams.oldvariablecode, initParams.tolerance, initParams.getColor(), initParams.switch_rate, initParams.trap_point, initParams.linetrap, initParams.skew);
    }
    public ComplexFractalInitParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, double tolerance, @NotNull Colorizer color, int switch_rate, @NotNull Complex trap_point) {
        initParams(width, height, zoom, zoom_factor, base_precision, fractal_mode, function, consts, variableCode, variableCode + "_p", tolerance, color, switch_rate, trap_point, null, 0);
    }
    public ComplexFractalInitParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, double tolerance, @NotNull Colorizer color, int switch_rate, @NotNull Complex trap_point, String linetrap) {
        initParams(width, height, zoom, zoom_factor, base_precision, fractal_mode, function, consts, variableCode, variableCode + "_p", tolerance, color, switch_rate, trap_point, linetrap, 0);
    }
    public ComplexFractalInitParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, double tolerance, @NotNull Colorizer color, int switch_rate, @NotNull Complex trap_point, String linetrap, double skew) {
        initParams(width, height, zoom, zoom_factor, base_precision, fractal_mode, function, consts, variableCode, variableCode + "_p", tolerance, color, switch_rate, trap_point, linetrap, skew);
    }
    public ComplexFractalInitParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, String oldvariablecode, double tolerance, @NotNull Colorizer color, int switch_rate, @NotNull Complex trap_point, String linetrap, double skew) {
        initParams(width, height, zoom, zoom_factor, base_precision, fractal_mode, function, consts, variableCode, oldvariablecode, tolerance, color, switch_rate, trap_point, linetrap, skew);
    }
    public ComplexFractalInitParams() {
        @NotNull String func = "z ^ 2 + c";
        @NotNull String[][] consts = {{"c", "-0.8,+0.156i"}};
        @NotNull Colorizer cfg = new Colorizer(Colors.MODE.CURVATURE_AVERAGE_ABS_SPLINE, 19, 16, 0, true, false);
        cfg.setPalette(new int[]{Colorizer.toRGB(66, 30, 15), Colorizer.toRGB(25, 7, 26), Colorizer.toRGB(9, 1, 47), Colorizer.toRGB(4, 4, 73), Colorizer.toRGB(0, 7, 100), Colorizer.toRGB(12, 44, 138), Colorizer.toRGB(24, 82, 177), Colorizer.toRGB(57, 125, 209), Colorizer.toRGB(134, 181, 229), Colorizer.toRGB(211, 236, 248), Colorizer.toRGB(241, 233, 191), Colorizer.toRGB(248, 201, 95), Colorizer.toRGB(255, 170, 0), Colorizer.toRGB(204, 128, 0), Colorizer.toRGB(153, 87, 0), Colorizer.toRGB(106, 52, 3)}, false);
        initParams(1921, 1081, 10, 0, 540, ComplexFractalGenerator.Mode.JULIA, func, consts, "z", "z_p", 1e-5, cfg, 0, Complex.ZERO, null, 0);
    }
    @NotNull
    public Colorizer getColor() {
        return new Colorizer(color);
    }
    public void setColor(@NotNull Colorizer color) {
        this.color = new Colorizer(color);
    }
    private void initParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, String oldvariablecode, double tolerance, @NotNull Colorizer colors, int switch_rate, @NotNull Complex trap_point, String linetrap, double skew) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.zoom_factor = zoom_factor;
        this.base_precision = base_precision;
        this.skew = skew;
        this.fractal_mode = fractal_mode;
        this.function = function;
        this.switch_rate = switch_rate;
        setConsts(consts);
        setOldvariablecode(oldvariablecode);
        this.variableCode = variableCode;
        this.tolerance = tolerance;
        setColor(colors);
        setTrap_point(trap_point);
        this.linetrap = linetrap;
    }
    private void setConsts(@NotNull String[][] consts) {
        this.consts = new String[consts.length][consts[0].length];
        for (int i = 0; i < consts.length; i++) {
            System.arraycopy(consts[i], 0, this.consts[i], 0, consts[i].length);
        }
    }
    @Nullable
    public String getOldvariablecode() {
        return oldvariablecode;
    }
    public void setOldvariablecode(String oldvariablecode) {
        this.oldvariablecode = oldvariablecode;
    }
    @Override
    public String toString() {
        String representation = INIT + "%n%d%n%d%n%f%n%f%n%f%n%s%n%s%n%s%n%s%n" + OLD_VARIABLE_CODE + "%s$n%f%n%s%n" + SWITCH_RATE + "%d%n" + TRAP_POINT + "%s%n";
        representation = String.format(representation, width, height, zoom, zoom_factor,
                base_precision, fractal_mode, function, constantsToString(), variableCode,
                oldvariablecode, tolerance, color, switch_rate, trap_point);
        if (linetrap != null) {
            representation += TRAP_LINE + linetrap + "\n";
        }
        representation += skew + "\n" + ENDINIT;
        return representation;
    }
    @NotNull
    private String constantsToString() {
        @NotNull String representation = "";
        for (@NotNull String[] constant : consts) {
            for (String s : constant) {
                representation += s + ":";
            }
            representation = representation.substring(0, representation.length() - 1) + ";";
        }
        representation = representation.substring(0, representation.length() - 1);
        return representation;
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
    @Nullable
    public String getLinetrap() {
        return linetrap;
    }
    public void setLinetrap(String linetrap) {
        this.linetrap = linetrap;
    }
    public Complex getTrap_point() {
        return trap_point;
    }
    public void setTrap_point(@NotNull Complex trap_point) {
        this.trap_point = new Complex(trap_point);
    }
    public int getSwitch_rate() {
        return switch_rate;
    }
    public void setSwitch_rate(int switch_rate) {
        this.switch_rate = switch_rate;
    }
    public void fromString(@NotNull String[] params) {
        @NotNull String[] con = StringManipulator.split(params[9], ";");
        @NotNull String[][] consts = new String[con.length][2];
        for (int i = 0; i < consts.length; i++) {
            consts[i] = StringManipulator.split(con[i], ":");
        }
        @NotNull String[] colorcfg = StringManipulator.split(params[10], ",");
        @NotNull Colorizer colorConfig = new Colorizer();
        colorConfig.fromString(colorcfg);
        if (params.length == 13) {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]), Double.valueOf(params[3]), Double.valueOf(params[4]), ComplexFractalGenerator.Mode.valueOf(params[5]), params[6], consts, params[7], Double.valueOf(params[8]), colorConfig, 0, Double.valueOf(params[12]));
        } else {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]), Double.valueOf(params[3]), Double.valueOf(params[4]), ComplexFractalGenerator.Mode.valueOf(params[5]), params[6], consts, params[7], Double.valueOf(params[8]), colorConfig, 0, Double.valueOf(params[12]));
        }
    }
    private void initParams(int width, int height, double zoom, double zoom_factor, double base_precision, ComplexFractalGenerator.Mode fractal_mode, String function, @NotNull String[][] consts, String variableCode, double tolerance, @NotNull Colorizer colors, int switch_rate, double skew) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.zoom_factor = zoom_factor;
        this.base_precision = base_precision;
        this.fractal_mode = fractal_mode;
        this.function = function;
        this.switch_rate = switch_rate;
        setConsts(consts);
        this.variableCode = variableCode;
        this.tolerance = tolerance;
        setColor(colors);
        setTrap_point(Complex.ZERO);
        this.linetrap = null;
        setOldvariablecode(variableCode + "_p");
        this.skew = skew;
    }
}