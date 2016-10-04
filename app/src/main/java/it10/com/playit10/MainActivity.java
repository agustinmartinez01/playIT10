package it10.com.playit10;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public static String EXTRA_ADDRESS = "device_address";
    public static final int segundos=1;
    public static final int milisegundos=segundos*1000;
    public static final int delay = 2;
    private ProgressBar progresbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progresbar= (ProgressBar) findViewById(R.id.progressBar);
        progresbar.setMax(maximoProgreso());
        empezarProgreso();
    }
    public void empezarProgreso(){
        new CountDownTimer(milisegundos,1000) {
            @Override
            public void onTick(long l) {
                progresbar.setProgress(establecerSegundos(l));
            }

            @Override
            public void onFinish() {
                Intent nuevofrom= new Intent(MainActivity.this,Principal.class);

                startActivity(nuevofrom);
                finish();
            }
        }.start();
    }
    public int establecerSegundos(long milisecond){
        return (int) ((milisegundos-milisecond)/1000);
    }
    public int maximoProgreso(){
        return segundos-delay;
    }
}
