package iut.r411.filrouge;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imageViewIcon;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        imageViewIcon = findViewById(R.id.imageViewIcon);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        // Load flip animation from XML for the TextView
        ObjectAnimator textFlipAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.text_animation);
        textFlipAnimation.setTarget(textView);

        // Load flip animation from XML for the ImageView
        ObjectAnimator flipAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.icon_animation);
        flipAnimation.setTarget(imageViewIcon);

        ObjectAnimator fadeOutAnimationIcon = ObjectAnimator.ofFloat(imageViewIcon, "alpha", 1f, 0f);
        fadeOutAnimationIcon.setDuration(1000);

        ObjectAnimator fadeOutAnimationProgressBar = ObjectAnimator.ofFloat(progressBar, "alpha", 1f, 0f);
        fadeOutAnimationProgressBar.setDuration(1000);

        flipAnimation.addUpdateListener(animation -> {
            int progress = (int) ((float) animation.getCurrentPlayTime() / animation.getDuration() * 100);
            progressBar.setProgress(progress);
        });

        flipAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fadeOutAnimationIcon.start();
                fadeOutAnimationProgressBar.start();
            }
        });

        fadeOutAnimationIcon.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flipAnimation.start();
        textFlipAnimation.start();
    }
}
