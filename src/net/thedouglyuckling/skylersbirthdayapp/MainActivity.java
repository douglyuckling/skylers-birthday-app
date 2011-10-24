package net.thedouglyuckling.skylersbirthdayapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private MainView view;
    private MediaPlayer backgroundMusic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        view = (MainView) findViewById(R.id.main);

        backgroundMusic = MediaPlayer.create(this, R.raw.lose_yourself_guitar_loop);
        backgroundMusic.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.getThread().unPause();
        backgroundMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
        view.getThread().pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backgroundMusic.stop();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundMusic.stop();
        finish();
    }

}