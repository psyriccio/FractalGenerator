package in.tamchow.fractal.imgutils.containers;
import in.tamchow.fractal.config.fractalconfig.fractal_zooms.ZoomParams;
/**
 * Interface that indicates that a system can be zoomed into losslessly
 */
public interface Zoomable {
    void zoom(int cx, int cy, double level);
    void zoom(ZoomParams zoom);
}