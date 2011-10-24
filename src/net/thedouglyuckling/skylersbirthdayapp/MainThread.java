package net.thedouglyuckling.skylersbirthdayapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Context context;

    private boolean running = false;
    private boolean paused = false;

    private int canvasWidth = 1;
    private int canvasHeight = 1;

    private long lastPhysicsUpdate;

    private double angle1 = 0.0 * Math.PI;
    private double angle2 = 0.2 * Math.PI;

    private double angularVelocity1 = 0.00050 * Math.PI;
    private double angularVelocity2 = 0.00040 * Math.PI;

    float offset1;
    float offset2;
    float offset3;
    float offset4;

    private BitmapDrawable backgroundImage;
    private BitmapDrawable catImage;

    private Rect backgroundRect;
    private Paint backgroundPaint;
    private Paint textFillPaint;
    private Paint textStrokePaint;
    private Paint mediumTextFillPaint;
    private Paint mediumTextStrokePaint;
    private Paint largeTextFillPaint;
    private Paint largeTextStrokePaint;

    private Paint blackPaint;

    private AnimatedPath animatedPath1 = new WigglyAnimatedPath( //
            000.0f, new SineAnimatedValue(-40.0f, 15.0f, -40.0f, 0.8f), //
            100.0f, new SineAnimatedValue(-50.0f, 25.0f, -30.0f, 0.6f), //
            200.0f, new SineAnimatedValue(-55.0f, 27.0f, 20.0f, 1.0f), //
            300.0f, new SineAnimatedValue(-52.0f, 25.0f, -10.0f, 0.6f));
    private AnimatedPath animatedPath2 = new WigglyAnimatedPath( //
            000.0f, new SineAnimatedValue(-35.0f, 30.0f, -12.0f, 0.3f), //
            100.0f, new SineAnimatedValue(-52.0f, 25.0f, 06.0f, 0.6f), //
            200.0f, new SineAnimatedValue(-50.0f, 25.0f, -00.0f, 0.9f), //
            300.0f, new SineAnimatedValue(-50.0f, 29.0f, 20.0f, 0.6f));
    private AnimatedPath animatedPath3 = new WigglyAnimatedPath( //
            000.0f, new SineAnimatedValue(-40.0f, 25.0f, -35.0f, 0.3f), //
            100.0f, new SineAnimatedValue(-50.0f, 27.0f, -20.0f, 0.6f), //
            200.0f, new SineAnimatedValue(-55.0f, 25.0f, 19.0f, 0.8f), //
            300.0f, new SineAnimatedValue(-42.0f, 30.0f, 10.0f, 1.2f));
    
    private SineAnimatedValue catYMotion = new SineAnimatedValue(-20f, 5f, 0f, 0.5f);

    public MainThread(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;

        initRenderingObjects();
    }

    private void initRenderingObjects() {
        backgroundRect = new Rect(0, 0, canvasWidth, canvasHeight);

        backgroundPaint = new Paint();
        backgroundPaint.setARGB(255, 122, 186, 122);

        textFillPaint = new Paint();
        textFillPaint.setARGB(255, 183, 110, 184);
        textFillPaint.setStyle(Paint.Style.FILL);
        textFillPaint.setAntiAlias(true);

        textStrokePaint = new Paint();
        textStrokePaint.setARGB(255, 255, 255, 255);
        textStrokePaint.setStyle(Paint.Style.STROKE);
        textStrokePaint.setAntiAlias(true);
        textStrokePaint.setStrokeWidth(2.0f);
        textStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        textStrokePaint.setStrokeJoin(Paint.Join.ROUND);

        mediumTextFillPaint = new Paint(textFillPaint);
        mediumTextFillPaint.setTextSize(75.0f);

        mediumTextStrokePaint = new Paint(textStrokePaint);
        mediumTextStrokePaint.setTextSize(75.0f);

        largeTextFillPaint = new Paint(textFillPaint);
        largeTextFillPaint.setTextSize(100.0f);

        largeTextStrokePaint = new Paint(textStrokePaint);
        largeTextStrokePaint.setTextSize(100.0f);

        blackPaint = new Paint();
        blackPaint.setARGB(255, 0, 0, 0);

        backgroundImage = (BitmapDrawable) context.getResources().getDrawable(R.drawable.plaid_img);
        backgroundImage.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        catImage = (BitmapDrawable) context.getResources().getDrawable(R.drawable.amazon);
    }

    @Override
    public void run() {
        lastPhysicsUpdate = System.currentTimeMillis() + 50;

        while (running) {
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    if (!paused) {
                        updateAnimationState();
                    }

                    doDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void updateAnimationState() {
        long now = System.currentTimeMillis();

        if (lastPhysicsUpdate > now) {
            return;
        }

        long timeDelta = now - lastPhysicsUpdate;

        angle1 += angularVelocity1 * (double) timeDelta;
        while (angle1 < 0.0) {
            angle1 += 2.0 * Math.PI;
        }
        while (angle1 > 2.0 * Math.PI) {
            angle1 -= 2.0 * Math.PI;
        }

        angle2 += angularVelocity2 * (double) timeDelta;
        while (angle2 < 0.0) {
            angle2 += 2.0 * Math.PI;
        }
        while (angle2 > 2.0 * Math.PI) {
            angle2 -= 2.0 * Math.PI;
        }

        offset1 = (float) Math.sin(angle1) * 30.0f;
        offset2 = (float) Math.cos(angle1) * 32.0f;
        offset3 = (float) Math.sin(angle2) * 40.0f;
        offset4 = (float) Math.cos(angle2) * 35.0f;

        animatedPath1.update(timeDelta);
        animatedPath2.update(timeDelta);
        animatedPath3.update(timeDelta);
        catYMotion.update(timeDelta);

        lastPhysicsUpdate = now;
    }

    private void doDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -canvasHeight / 2);
        // canvas.rotate(2.0f);
        // canvas.drawRect(backgroundRect, backgroundPaint);
        backgroundImage.setBounds(0, 0, canvasWidth * 2, canvasHeight * 2);
        backgroundImage.draw(canvas);

        canvas.restore();
        canvas.save();
        canvas.translate(canvasWidth - catImage.getIntrinsicWidth(), canvasHeight - catImage.getIntrinsicHeight());
        canvas.translate(0, catYMotion.getValue());

        catImage.setBounds(0, 0, catImage.getIntrinsicWidth(), catImage.getIntrinsicHeight());
        catImage.draw(canvas);

        canvas.restore();

        canvas.translate(140, 120);
        canvas.drawTextOnPath("Happy", animatedPath1.getPath(), 0f, 0f, mediumTextFillPaint);
        canvas.drawTextOnPath("Happy", animatedPath1.getPath(), 0f, 0f, mediumTextStrokePaint);

        canvas.translate(-20, 120);
        canvas.drawTextOnPath("Birthday", animatedPath2.getPath(), 0f, 0f, mediumTextFillPaint);
        canvas.drawTextOnPath("Birthday", animatedPath2.getPath(), 0f, 0f, mediumTextStrokePaint);

        canvas.translate(-20, 120);
        canvas.drawTextOnPath("Skyler!", animatedPath3.getPath(), 0f, 0f, largeTextFillPaint);
        canvas.drawTextOnPath("Skyler!", animatedPath3.getPath(), 0f, 0f, largeTextStrokePaint);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setSurfaceSize(int width, int height) {
        synchronized (surfaceHolder) {
            canvasWidth = width;
            canvasHeight = height;

            // Resize stuff as necessary...
            backgroundRect = new Rect(0, 0, canvasWidth * 2, canvasHeight * 2);
        }
    }

    public void pause() {
        synchronized (surfaceHolder) {
            paused = true;
        }
    }

    public void unPause() {
        synchronized (surfaceHolder) {
            paused = false;
        }
    }

}
