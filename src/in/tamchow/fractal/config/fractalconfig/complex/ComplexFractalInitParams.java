package in.tamchow.fractal.config.fractalconfig.complex;
import in.tamchow.fractal.color.ColorConfig;
import in.tamchow.fractal.color.Colors;
import in.tamchow.fractal.config.DataFromString;
import in.tamchow.fractal.fractals.complex.ComplexFractalGenerator;
import in.tamchow.fractal.math.complex.Complex;

import java.io.Serializable;
/**
 * Parameters for configuring the initialization of a fractal
 */
public class ComplexFractalInitParams implements Serializable, DataFromString {
    public String function, variableCode;
    public String[][] consts;
    public int width, height, fractal_mode;
    public double tolerance, zoom, zoom_factor, base_precision;
    public Complex degree;
    public ColorConfig color;
    public ComplexFractalInitParams(ComplexFractalInitParams initParams) {
        initParams(initParams.width, initParams.height, initParams.zoom, initParams.zoom_factor, initParams.base_precision, initParams.fractal_mode, initParams.function, initParams.consts, initParams.variableCode, initParams.tolerance, initParams.degree, initParams.getColor());
    }
    public ColorConfig getColor() {
        return new ColorConfig(color);
    }
    public void setColor(ColorConfig color) {
        this.color = new ColorConfig(color);
    }
    private void initParams(int width, int height, double zoom, double zoom_factor, double base_precision, int fractal_mode, String function, String[][] consts, String variableCode, double tolerance, Complex degree, ColorConfig colors) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.zoom_factor = zoom_factor;
        this.base_precision = base_precision;
        this.fractal_mode = fractal_mode;
        this.function = function;
        setConsts(consts);
        this.variableCode = variableCode; this.tolerance = tolerance; this.degree = new Complex(degree);
        setColor(colors);
    }
    private void setConsts(String[][] consts) {
        this.consts = new String[consts.length][consts[0].length];
        for (int i = 0; i < consts.length; i++) {
            System.arraycopy(consts[i], 0, this.consts[i], 0, consts[i].length);
        }
    }
    public ComplexFractalInitParams(int width, int height, double zoom, double zoom_factor, double base_precision, int fractal_mode, String function, String[][] consts, String variableCode, double tolerance, Complex degree, ColorConfig color) {
        initParams(width, height, zoom, zoom_factor, base_precision, fractal_mode, function, consts, variableCode, tolerance, degree, color);
    }
    public ComplexFractalInitParams() {
        String func = "z ^ 2 + c"; String[][] consts = {{"c", "-0.8,+0.156i"}};
        ColorConfig cfg = new ColorConfig(Colors.CALCULATIONS.CURVATURE_AVERAGE_SPLINE, 19, 16, true, true);
        cfg.setPalette(new int[]{ColorConfig.toRGB(66, 30, 15), ColorConfig.toRGB(25, 7, 26), ColorConfig.toRGB(9, 1, 47), ColorConfig.toRGB(4, 4, 73), ColorConfig.toRGB(0, 7, 100), ColorConfig.toRGB(12, 44, 138), ColorConfig.toRGB(24, 82, 177), ColorConfig.toRGB(57, 125, 209), ColorConfig.toRGB(134, 181, 229), ColorConfig.toRGB(211, 236, 248), ColorConfig.toRGB(241, 233, 191), ColorConfig.toRGB(248, 201, 95), ColorConfig.toRGB(255, 170, 0), ColorConfig.toRGB(204, 128, 0), ColorConfig.toRGB(153, 87, 0), ColorConfig.toRGB(106, 52, 3)}, false);
        initParams(1921, 1081, 10, 0, 540, ComplexFractalGenerator.MODE_JULIA, func, consts, "z", 1e-5, new Complex("-1"), cfg);
    }
    public void fromString(String[] params) {
        String[] con = params[9].split(";");
        String[][] consts = new String[con.length][2];
        for (int i = 0; i < consts.length; i++) {
            consts[i] = con[i].split(":");
        } String[] colorcfg = params[10].split(",");
        ColorConfig colorConfig = new ColorConfig(); colorConfig.fromString(colorcfg); if (params.length == 12) {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]), Double.valueOf(params[3]), Double.valueOf(params[4]), Integer.valueOf(params[5]), params[6], consts, params[7], Double.valueOf(params[8]), new Complex(params[11]), colorConfig);
        } else {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Double.valueOf(params[2]), Double.valueOf(params[3]), Double.valueOf(params[4]), Integer.valueOf(params[5]), params[6], consts, params[7], Double.valueOf(params[8]), new Complex("-1"), colorConfig);
        }
    }
}