package net.thedouglyuckling.skylersbirthdayapp;

import android.graphics.Path;

public class AnimatedPath {
    private AnimatedValue x1;
    private AnimatedValue y1;
    private AnimatedValue x2;
    private AnimatedValue y2;
    private AnimatedValue x3;
    private AnimatedValue y3;
    private AnimatedValue x4;
    private AnimatedValue y4;

    private Path path = new Path();

    public AnimatedPath(AnimatedValue x1, AnimatedValue y1, AnimatedValue x2, AnimatedValue y2, AnimatedValue x3, AnimatedValue y3,
            AnimatedValue x4, AnimatedValue y4) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
    }

    public void update(long timeDeltaInMillis) {
        x1.update(timeDeltaInMillis);
        y1.update(timeDeltaInMillis);
        x2.update(timeDeltaInMillis);
        y2.update(timeDeltaInMillis);
        x3.update(timeDeltaInMillis);
        y3.update(timeDeltaInMillis);
        x4.update(timeDeltaInMillis);
        y4.update(timeDeltaInMillis);

        path.rewind();
        path.moveTo(x1.getValue(), y1.getValue());
        path.cubicTo(x2.getValue(), y2.getValue(), x3.getValue(), y3.getValue(), x4.getValue(), y4.getValue());
    }

    public Path getPath() {
        return path;
    }
}
