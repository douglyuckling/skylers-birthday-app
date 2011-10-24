package net.thedouglyuckling.skylersbirthdayapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView {
    private MainThread thread;

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(surfaceHolderCallback);

        thread = new MainThread(surfaceHolder, context);
    }

    private final SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            thread.setRunning(true);
            thread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            thread.setSurfaceSize(width, height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // we have to tell thread to shut down & wait for it to finish, or else
            // it might touch the Surface after we return and explode
            boolean retry = true;
            thread.setRunning(false);
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

    };

    public MainThread getThread() {
        return thread;
    }

}
