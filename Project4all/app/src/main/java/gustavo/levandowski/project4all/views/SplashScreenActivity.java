package gustavo.levandowski.project4all.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import gustavo.levandowski.project4all.R;
import gustavo.levandowski.project4all.util.CheckConnection;

/***
 * @author Gustavo Levandowski
 */
public class SplashScreenActivity extends Activity {

    //region Propriedades
    private  Animation anime;
    private Animation slideToRightOut;
    private ImageView splashImageViewLogo;
    private int animationType;
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.initialComponents();
        this.findViews();
        this.startAnimationSplash();
    }

    @Override
    protected void onResume() {
        this.startAnimationSplash();
        super.onResume();
    }
    //endregion

    //region Referências/Componentes iniciais

    private void findViews(){
        splashImageViewLogo = findViewById(R.id.app_splashscreen_logo);
    }

    private void initialComponents(){
        setContentView(R.layout.activity_splash_screen);
        SplashScreenActivity splashScreen = this;
        anime = AnimationUtils.loadAnimation(splashScreen, R.anim.appear);
    }
    //endregion

    //region StartAnimation
    private void startAnimationSplash(){
        splashImageViewLogo.setVisibility(View.VISIBLE);

        splashImageViewLogo.startAnimation(anime);

        anime.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(final Animation animation) {

                final int value = (int) (1 + Math.random() * 6);
                animationType = 0;

                switch (value) {
                    case 1:
                        animationType = R.anim.fade_out;
                        break;
                    case 2:
                        animationType = R.anim.push_down_out;
                        break;
                    case 3:
                        animationType = R.anim.disappear;
                        break;
                    case 4:
                        animationType = R.anim.slide_out_right;
                        break;
                    case 5:
                        animationType = R.anim.push_down_out;
                        break;
                    default:
                        animationType = R.anim.slide_out_right;
                        break;
                }

                slideToRightOut = AnimationUtils.loadAnimation(SplashScreenActivity.this, animationType);
                splashImageViewLogo.startAnimation(slideToRightOut);
                splashImageViewLogo.setVisibility(View.INVISIBLE);

                slideToRightOut.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationEnd(final Animation animation) {
                        openFirstActivity();

                    }
                    @Override
                    public void onAnimationRepeat(final Animation animation) { }
                    @Override
                    public void onAnimationStart(final Animation animation) { }
                });
            }

            @Override
            public void onAnimationRepeat(final Animation arg0) {}
            @Override
            public void onAnimationStart(final Animation arg0) { }
        });
    }
    //endregion

    //region Fluxo
    public void openFirstActivity() {
        if (!CheckConnection.isConnected(SplashScreenActivity.this)) {
            new CheckConnection(SplashScreenActivity.this).dialogIfOffline();
        }else {
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    //endregion
}


