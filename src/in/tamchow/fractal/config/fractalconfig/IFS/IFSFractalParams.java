package in.tamchow.fractal.config.fractalconfig.IFS;
import in.tamchow.fractal.config.fractalconfig.fractal_zooms.ZoomConfig;
import in.tamchow.fractal.graphicsutilities.containers.PixelContainer;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.math.MathUtils;
import in.tamchow.fractal.helpers.strings.StringManipulator;
import in.tamchow.fractal.math.matrix.Matrix;

import java.io.Serializable;

import static in.tamchow.fractal.config.Strings.DECLARATIONS.*;
/**
 * Holds Parameters for an IFS fractal
 */
public class IFSFractalParams implements Serializable {
    public static final String VARIABLE_CODES = "x:y:r:t:p";
    public ZoomConfig zoomConfig;
    public PixelContainer.PostProcessMode postProcessMode;
    public String path;
    Matrix[] transforms, translators;
    boolean ifsMode;
    String x_code, y_code, r_code, t_code, p_code;
    String[] yfunctions, xfunctions;
    double[] weights;
    int[] colors;
    int depth;
    int width;
    int height;
    int fps;
    int frameskip;
    int threads;
    double zoom;
    double zoomlevel;
    double base_precision;
    double skew;
    private IFSFractalParams() {
        setDepth(1);
        setThreads(1);
        @NotNull String[] variableCodes = StringManipulator.split(VARIABLE_CODES, ":");
        setFrameskip(-1);
        setPath("");
        setX_code(variableCodes[0]);
        setY_code(variableCodes[1]);
        setR_code(variableCodes[2]);
        setT_code(variableCodes[3]);
        setP_code(variableCodes[4]);
        setPostProcessMode(PixelContainer.PostProcessMode.NONE);
    }
    public IFSFractalParams(@NotNull IFSFractalParams config) {
        if (!(config.getColors().length == config.getWeights().length && config.getTransforms().length == config.getTranslators().length)) {
            throw new IllegalArgumentException("Configuration object is not properly defined");
        }
        setColors(config.getColors());
        setWeights(config.getWeights());
        setTransforms(config.getTransforms());
        setTranslators(config.getTranslators());
        setDepth(config.getDepth());
        setFrameskip(config.getFrameskip());
        setFps(config.getFps());
        setPath(config.getPath());
        setSkew(config.getSkew());
        setPostProcessMode(config.getPostProcessMode());
        setThreads(config.getThreads());
        setX_code(config.getX_code());
        setY_code(config.getY_code());
        setR_code(config.getR_code());
        setT_code(config.getT_code());
        setP_code(config.getP_code());
        if (config.zoomConfig.zooms != null) {
            this.zoomConfig = new ZoomConfig(config.zoomConfig);
        }
        setPath(config.getPath());
        setPostProcessMode(config.getPostProcessMode());
    }
    @NotNull
    public static IFSFractalParams fromString(@NotNull String[] input) {
        @NotNull IFSFractalParams params = new IFSFractalParams(); //params.setIfsMode(Boolean.valueOf(input[0]));
        @NotNull String[] ifsData = StringManipulator.split(input[0], ":");
        if (ifsData.length == 1) {
            params.setIfsMode(Boolean.valueOf(input[0]));
        } else {
            params.setIfsMode(Boolean.valueOf(ifsData[0]));
            params.setX_code(ifsData[1]);
            params.setY_code(ifsData[2]);
            params.setR_code(ifsData[3]);
            params.setT_code(ifsData[4]);
            params.setP_code(ifsData[5]);
        }
        params.setWidth(Integer.valueOf(input[1]));
        params.setHeight(Integer.valueOf(input[2]));
        params.setBase_precision(Double.valueOf(input[3]));
        params.setZoom(Double.valueOf(input[4]));
        params.setZoomlevel(Double.valueOf(input[5]));
        params.setDepth(Integer.valueOf(input[6]));
        params.setFps(Integer.valueOf(input[7]));
        params.setSkew(Double.valueOf(input[8]));
        if (params.isIfsMode()) {
            params.xfunctions = new String[input.length - 9];
            params.yfunctions = new String[input.length - 9];
            params.colors = new int[input.length - 9];
            params.weights = new double[input.length - 9];
            for (int i = 9; i < input.length; i++) {
                @NotNull String[] parts = StringManipulator.split(input[i], " ");
                params.xfunctions[i] = parts[0];
                params.yfunctions[i] = parts[1];
                params.weights[i] = Double.valueOf(parts[2]);
                params.colors[i] = Integer.valueOf(parts[3], 16);
            }
        } else {
            params.transforms = new Matrix[input.length - 9];
            params.translators = new Matrix[input.length - 9];
            params.colors = new int[input.length - 9];
            params.weights = new double[input.length - 9];
            for (int i = 9; i < input.length; i++) {
                @NotNull String[] parts = StringManipulator.split(input[i], " ");
                params.transforms[i] = new Matrix(parts[0]);
                params.translators[i] = new Matrix(parts[1]);
                params.weights[i] = Double.valueOf(parts[2]);
                params.colors[i] = Integer.valueOf(parts[3], 16);
            }
        }
        return params;
    }
    public String getX_code() {
        return x_code;
    }
    public void setX_code(String x_code) {
        this.x_code = x_code;
    }
    public String getY_code() {
        return y_code;
    }
    public void setY_code(String y_code) {
        this.y_code = y_code;
    }
    public String getR_code() {
        return r_code;
    }
    public void setR_code(String r_code) {
        this.r_code = r_code;
    }
    public String getT_code() {
        return t_code;
    }
    public void setT_code(String t_code) {
        this.t_code = t_code;
    }
    public String getP_code() {
        return p_code;
    }
    public void setP_code(String p_code) {
        this.p_code = p_code;
    }
    public double getSkew() {
        return skew;
    }
    public void setSkew(double skew) {
        this.skew = skew;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getFrameskip() {
        return frameskip;
    }
    public void setFrameskip(int frameskip) {
        this.frameskip = frameskip;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = Math.abs((depth == 0) ? 1 : depth);
    }
    public Matrix[] getTransforms() {
        return transforms;
    }
    public void setTransforms(@NotNull Matrix[] transforms) {
        this.transforms = new Matrix[transforms.length];
        for (int i = 0; i < transforms.length; i++) {
            this.transforms[i] = new Matrix(transforms[i]);
        }
    }
    public Matrix[] getTranslators() {
        return translators;
    }
    public void setTranslators(@NotNull Matrix[] translators) {
        this.translators = new Matrix[translators.length];
        for (int i = 0; i < translators.length; i++) {
            this.translators[i] = new Matrix(translators[i]);
        }
    }
    public double[] getWeights() {
        return weights;
    }
    public void setWeights(@NotNull double[] weights) {
        this.weights = new double[weights.length];
        System.arraycopy(weights, 0, this.weights, 0, this.weights.length);
    }
    public int[] getColors() {
        return colors;
    }
    public void setColors(@NotNull int[] colors) {
        this.colors = new int[colors.length];
        System.arraycopy(colors, 0, this.colors, 0, this.colors.length);
    }
    public int getFps() {
        return fps;
    }
    public void setFps(int fps) {
        this.fps = fps;
    }
    public int getThreads() {
        return threads;
    }
    public void setThreads(int threads) {
        this.threads = MathUtils.clamp(threads, 1, depth);
    }
    public boolean isAnimated() {
        return frameskip >= 0;
    }
    public boolean isIfsMode() {
        return ifsMode;
    }
    public void setIfsMode(boolean ifsMode) {
        this.ifsMode = ifsMode;
    }
    public boolean useThreadedGenerator() {
        return (threads > 1) && (frameskip < 0);
    }
    public String[] getYfunctions() {
        return yfunctions;
    }
    public void setYfunctions(String[] yfunctions) {
        this.yfunctions = yfunctions;
    }
    public String[] getXfunctions() {
        return xfunctions;
    }
    public void setXfunctions(String[] xfunctions) {
        this.xfunctions = xfunctions;
    }
    @NotNull
    @Override
    public String toString() {
        @NotNull String representation = THREADS + threads + "\n" +
                ((frameskip >= 0) ? FRAMESKIP + frameskip + "\n" : "");
        representation += (postProcessMode != null) ? POSTPROCESSING + postProcessMode + "\n" : "";
        representation += ifsMode + ((ifsMode) ? ":" + createCodeString() : "") + "\n" + width + "\n" + height + "\n" + base_precision + "\n" + zoom + "\n" + zoomlevel + "\n" + depth + "\n" + fps + "\n" + skew;
        if (ifsMode) {
            for (int i = 0; i < weights.length; i++) {
                representation += "\n" + xfunctions[i] + " " + yfunctions[i] + " " + weights[i] + " " + colors[i];
            }
        } else {
            for (int i = 0; i < weights.length; i++) {
                representation += "\n" + transforms[i] + " " + translators[i] + " " + weights[i] + " " + colors[i];
            }
        }
        if (zoomConfig != null) {
            representation += "\n" + zoomConfig;
        }
        return representation.trim();
    }
    private String createCodeString() {
        return String.format("%s:%s:%s:%s:%s", getX_code(), getY_code(), getR_code(), getT_code(), getP_code());
    }
    public void setZoomConfig(@NotNull ZoomConfig config) {
        zoomConfig = new ZoomConfig(config);
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public double getZoom() {
        return zoom;
    }
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    public double getZoomlevel() {
        return zoomlevel;
    }
    public void setZoomlevel(double zoomlevel) {
        this.zoomlevel = zoomlevel;
    }
    public double getBase_precision() {
        return base_precision;
    }
    public void setBase_precision(double base_precision) {
        this.base_precision = base_precision;
    }
    public PixelContainer.PostProcessMode getPostProcessMode() {
        return postProcessMode;
    }
    public void setPostProcessMode(PixelContainer.PostProcessMode postProcessMode) {
        this.postProcessMode = postProcessMode;
    }
}