package in.tamchow.fractal.config.fractalconfig;

import in.tamchow.fractal.math.complex.Complex;

import java.io.Serializable;

/**
 * Parameters for configuring the generation of a fractal
 */
public class FractalRunParams implements Serializable {
    public int iterations, start_x, end_x, start_y, end_y;
    public double escape_radius;
    public Complex constant;
    public boolean fully_configured;

    public FractalRunParams(FractalRunParams runParams) {
        if (runParams.fully_configured) {
            initParams(runParams.start_x, runParams.end_x, runParams.start_y, runParams.end_y, runParams.iterations, runParams.escape_radius, runParams.constant);
            fully_configured = true;
        } else {
            initParams(runParams.iterations, runParams.escape_radius, runParams.constant);
            fully_configured = false;
        }
    }

    public FractalRunParams(int start_x, int end_x, int start_y, int end_y, int iterations, double escape_radius) {
        initParams(start_x, end_x, start_y, end_y, iterations, escape_radius);
    }

    public FractalRunParams(int start_x, int end_x, int start_y, int end_y, int iterations, double escape_radius, Complex constant) {
        initParams(start_x, end_x, start_y, end_y, iterations, escape_radius, constant);
    }

    public FractalRunParams(int iterations, double escape_radius) {
        initParams(iterations, escape_radius);
    }

    public FractalRunParams(int iterations, double escape_radius, Complex constant) {
        initParams(iterations, escape_radius, constant);
    }
    public FractalRunParams() {
        initParams(128, 2.0);
    }

    public void initParams(int start_x, int end_x, int start_y, int end_y, int iterations, double escape_radius) {
        this.iterations = iterations;
        this.start_x = start_x;
        this.end_x = end_x;
        this.start_y = start_y;
        this.end_y = end_y;
        this.escape_radius = escape_radius;
        constant = null;
        fully_configured = true;
    }

    public void initParams(int start_x, int end_x, int start_y, int end_y, int iterations, double escape_radius, Complex constant) {
        this.iterations = iterations;
        this.start_x = start_x;
        this.end_x = end_x;
        this.start_y = start_y;
        this.end_y = end_y;
        this.escape_radius = escape_radius;
        this.constant = new Complex(constant);
        fully_configured = true;
    }
    public void initParams(int iterations, double escape_radius) {
        this.iterations = iterations;
        this.escape_radius = escape_radius;
        constant = null;
        fully_configured = false;
    }

    public void initParams(int iterations, double escape_radius, Complex constant) {
        this.iterations = iterations;
        this.escape_radius = escape_radius;
        this.constant = new Complex(constant);
        fully_configured = false;
    }

    /**
     * @param params: Pass in -1 for escape_radius in case of Newton Fractal Mode
     */
    public void paramsFromString(String[] params) {
        if (params.length == 6) {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2]), Integer.valueOf(params[3]), Integer.valueOf(params[4]), Double.valueOf(params[5]));
        } else if (params.length == 7) {
            initParams(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2]), Integer.valueOf(params[3]), Integer.valueOf(params[4]), Double.valueOf(params[5]), new Complex(params[6]));
        } else if (params.length == 2) {
            initParams(Integer.valueOf(params[0]), Double.valueOf(params[1]));
        } else if (params.length == 3) {
            initParams(Integer.valueOf(params[0]), Double.valueOf(params[1]), new Complex(params[2]));
        }
    }
}
