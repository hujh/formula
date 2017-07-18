package im.hujh.formula;

import java.math.RoundingMode;

/**
 *
 * @author hujh
 */
public class Options {

    public static final Options DEFAULT = new Options();

    private int scale = 9;

    private int intermediateScale = 32;

    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    public Options() {
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getIntermediateScale() {
        return intermediateScale;
    }

    public void setIntermediateScale(int intermediateScale) {
        this.intermediateScale = intermediateScale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

}
