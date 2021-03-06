package in.tamchow.fractal.config.fractalconfig.complex;
import in.tamchow.fractal.config.fractalconfig.fractal_zooms.ZoomConfig;
import in.tamchow.fractal.graphicsutilities.containers.PixelContainer;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.annotations.Nullable;
import in.tamchow.fractal.helpers.math.MathUtils;
import in.tamchow.fractal.helpers.strings.StringManipulator;

import java.io.Serializable;

import static in.tamchow.fractal.config.Strings.DECLARATIONS.POSTPROCESSING;
import static in.tamchow.fractal.config.Strings.DECLARATIONS.THREADS;
/**
 * Encapsulates {@code ComplexFractalInitParams} and {@code ComplexFractalRunParams}
 */
public class ComplexFractalParams implements Serializable {
    @Nullable
    public ComplexFractalRunParams runParams;
    @Nullable
    public ComplexFractalInitParams initParams;
    @NotNull
    public ZoomConfig zoomConfig = new ZoomConfig();
    public PixelContainer.PostProcessMode postprocessMode;
    public String path;
    private int x_threads, y_threads;
    public ComplexFractalParams() {
        runParams = new ComplexFractalRunParams();
        initParams = new ComplexFractalInitParams();
        initParams.setHeight(1);
        initParams.setWidth(1);
        setPath("");
        setPostProcessMode(PixelContainer.PostProcessMode.NONE);
        setX_threads(1);
        setY_threads(1);
    }
    public ComplexFractalParams(@Nullable ComplexFractalInitParams initParams, @Nullable ComplexFractalRunParams runParams, int x_threads, int y_threads) {
        if (initParams != null) {
            this.initParams = new ComplexFractalInitParams(initParams);
        } else {
            this.initParams = new ComplexFractalInitParams();
            this.initParams.setHeight(1);
            this.initParams.setWidth(1);
        }
        if (runParams != null) {
            this.runParams = new ComplexFractalRunParams(runParams);
        } else {
            this.runParams = new ComplexFractalRunParams();
        }
        setPath("");
        setPostProcessMode(PixelContainer.PostProcessMode.NONE);
        setX_threads(x_threads);
        setY_threads(y_threads);
    }
    public ComplexFractalParams(@Nullable ComplexFractalInitParams initParams, @Nullable ComplexFractalRunParams runParams) {
        if (initParams != null) {
            this.initParams = new ComplexFractalInitParams(initParams);
        } else {
            this.initParams = new ComplexFractalInitParams();
            this.initParams.setHeight(1);
            this.initParams.setWidth(1);
        }
        if (runParams != null) {
            this.runParams = new ComplexFractalRunParams(runParams);
        } else {
            this.runParams = new ComplexFractalRunParams();
        }
        setPath("");
        setPostProcessMode(PixelContainer.PostProcessMode.NONE);
        setX_threads(1);
        setY_threads(1);
    }
    public ComplexFractalParams(@NotNull ComplexFractalParams params) {
        this.initParams = new ComplexFractalInitParams(params.initParams);
        this.runParams = new ComplexFractalRunParams(params.runParams);
        setX_threads(params.getX_threads());
        setY_threads(params.getY_threads());
        if (params.zoomConfig.zooms != null) {
            this.zoomConfig = new ZoomConfig(params.zoomConfig);
        }
        setPostProcessMode(params.getPostProcessMode());
        setPath(params.getPath());
    }
    public int getX_threads() {
        return x_threads;
    }
    public void setX_threads(int x_threads) {
        this.x_threads = MathUtils.clamp(x_threads, 1, initParams.getWidth());
    }
    public int getY_threads() {
        return y_threads;
    }
    public void setY_threads(int y_threads) {
        this.y_threads = MathUtils.clamp(y_threads, 1, initParams.getHeight());
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public PixelContainer.PostProcessMode getPostProcessMode() {
        return postprocessMode;
    }
    public void setPostProcessMode(PixelContainer.PostProcessMode postProcessMode) {
        this.postprocessMode = postProcessMode;
    }
    public boolean useThreadedGenerator() {
        return (getX_threads() * getY_threads() > 1);
    }
    public void threadDataFromString(@NotNull String data) {
        @NotNull String[] parts = StringManipulator.split(data, " ");
        setX_threads(Integer.valueOf(parts[0]));
        setY_threads(Integer.valueOf(parts[1]));
    }
    public void setZoomConfig(@NotNull ZoomConfig config) {
        zoomConfig = new ZoomConfig(config);
    }
    @NotNull
    @Override
    public String toString() {
        return THREADS + x_threads + "," + y_threads + "\n" + POSTPROCESSING + postprocessMode + "\n" + ((zoomConfig != null) ? (zoomConfig + "\n") : "") + initParams + "\n" + runParams + "\n";
    }
}