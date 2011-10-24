package net.thedouglyuckling.skylersbirthdayapp;

public class IndependentAnimatedPoint {
    private AnimatedValue xAnimatedValue;
    private AnimatedValue yAnimatedValue;

    public IndependentAnimatedPoint(AnimatedValue xAnimatedValue, AnimatedValue yAnimatedValue) {
        this.xAnimatedValue = xAnimatedValue;
        this.yAnimatedValue = yAnimatedValue;
    }

    public void update(long timeDeltaInMillis) {
        xAnimatedValue.update(timeDeltaInMillis);
        yAnimatedValue.update(timeDeltaInMillis);
    }

    public float getXValue() {
        return xAnimatedValue.getValue();
    }

    public float getYValue() {
        return yAnimatedValue.getValue();
    }

}
