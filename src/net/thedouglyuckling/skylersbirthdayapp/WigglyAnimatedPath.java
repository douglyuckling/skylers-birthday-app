package net.thedouglyuckling.skylersbirthdayapp;

public class WigglyAnimatedPath extends AnimatedPath {

    public WigglyAnimatedPath(float x1, SineAnimatedValue y1, float x2, SineAnimatedValue y2, float x3, SineAnimatedValue y3, float x4,
            SineAnimatedValue y4) {
        super(new NonAnimatedValue(x1), y1, new NonAnimatedValue(x2), y2, new NonAnimatedValue(x3), y3, new NonAnimatedValue(x4), y4);
    }

}
