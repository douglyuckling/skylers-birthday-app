package net.thedouglyuckling.skylersbirthdayapp;

public class SineAnimatedValue implements AnimatedValue {
    private float angularVelocityInRadiansPerMilli;
    private float angleInRadians;
    private float offset;
    private float scale;

    public SineAnimatedValue(float min, float max, float startingValue, float cyclesPerSecond) {
        offset = (max + min) / 2.0f;
        scale = (max - min) / 2.0f;

        angularVelocityInRadiansPerMilli = (cyclesPerSecond * (float) M.TAU) / 1000.0f;

        double startingSine = (startingValue - offset) / scale;
        if (startingSine > 1.0f)
            startingValue = 1.0f;
        else if (startingSine < -1.0f)
            startingValue = -1.0f;

        angleInRadians = (float) Math.asin(startingSine);
        if (angleInRadians < 0.0f) {
            angleInRadians += (float) M.TAU;
        }
    }

    @Override
    public void update(long timeDeltaInMillis) {
        angleInRadians += angularVelocityInRadiansPerMilli * (float) timeDeltaInMillis;
        while (angleInRadians < 0.0f) {
            angleInRadians += (float) M.TAU;
        }
        while (angleInRadians > (float) M.TAU) {
            angleInRadians -= (float) M.TAU;
        }
    }

    @Override
    public float getValue() {
        return scale * (float) Math.sin(angleInRadians) + offset;
    }

}
