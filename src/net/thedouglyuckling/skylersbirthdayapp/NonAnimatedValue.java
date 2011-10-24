package net.thedouglyuckling.skylersbirthdayapp;

public class NonAnimatedValue implements AnimatedValue {
    private float value;

    public NonAnimatedValue(float value) {
        this.value = value;
    }

    @Override
    public void update(long timeDeltaInMillis) {
    }

    @Override
    public float getValue() {
        return value;
    }

}
