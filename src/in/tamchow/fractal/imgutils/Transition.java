package in.tamchow.fractal.imgutils;
/**
 * Creates transitions between images.
 * Note: Images to be transitioned between must be of the same resolution. No scaling is implemented here.
 */
public class Transition {
    private ImageData img1, img2;
    private Animation frames;
    private int       transtype;
    private int       transtime;
    public Transition(int transtype, ImageData img1, ImageData img2, int fps, int time) {
        setImg1(img1);
        setImg2(img2);
        frames = new Animation(fps);
        this.transtype = transtype;
        transtime = time;
    }
    public int getTranstime() {
        return transtime;
    }
    public void setTranstime(int transtime) {
        this.transtime = transtime;
    }
    public Animation getFrames() {
        return frames;
    }
    public void setFrames(Animation frames) {
        this.frames = frames;
    }
    public void setFrames(ImageData[] frames) {
        this.frames.setFrames(frames);
    }
    public ImageData getImg2() {
        return img2;
    }
    public void setImg2(ImageData img2) {
        this.img2 = new ImageData(img2);
    }
    public ImageData getImg1() {
        return img1;
    }
    public void setImg1(ImageData img1) {
        this.img1 = new ImageData(img1);
    }
    public int getTranstype() {
        return transtype;
    }
    public void setTranstype(int transtype) {
        this.transtype = transtype;
    }
    public void doTransition() {
        frames.clearFrames();
        ImageData tmp = new ImageData(img1);
        frames.addFrame(img1);
        int numframes  = frames.getFps() * transtime;
        int bandwidth  = img2.getWidth() / numframes;
        int bandheight = img2.getHeight() / numframes;
        switch (transtype) {
            case TransitionTypes.TOP:
                for (int i = 0; i < img1.getHeight() - bandheight; i += bandheight) {
                    for (int j = 0; j < bandheight; j++) {
                        for (int k = 0; k < img2.getWidth(); k++) {
                            tmp.setPixel(i + j, k, img2.getPixel(i + j, k));
                        }
                    }
                    frames.addFrame(tmp);
                }
                frames.addFrame(img2);
                break;
            case TransitionTypes.BOTTOM:
                for (int i = img1.getHeight() - 1; i >= bandheight; i -= bandheight) {
                    for (int j = bandheight - 1; j >= 0; j--) {
                        for (int k = 0; k < img2.getWidth(); k++) {
                            tmp.setPixel(i - j, k, img2.getPixel(i - j, k));
                        }
                    }
                    frames.addFrame(tmp);
                }
                frames.addFrame(img2);
                break;
            case TransitionTypes.LEFT:
                for (int i = 0; i < img1.getWidth() - bandwidth; i += bandwidth) {
                    for (int j = 0; j < bandwidth; j++) {
                        for (int k = 0; k < img2.getHeight(); k++) {
                            tmp.setPixel(k, i + j, img2.getPixel(k, i + j));
                        }
                    }
                    frames.addFrame(tmp);
                }
                frames.addFrame(img2);
                break;
            case TransitionTypes.RIGHT:
                for (int i = img1.getWidth() - 1; i >= bandwidth; i -= bandwidth) {
                    for (int j = bandwidth - 1; j >= 0; j--) {
                        for (int k = 0; k < img2.getHeight(); k++) {
                            tmp.setPixel(k, i - j, img2.getPixel(k, i - j));
                        }
                    }
                    frames.addFrame(tmp);
                }
                frames.addFrame(img2);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized transition type");
        }
    }
}