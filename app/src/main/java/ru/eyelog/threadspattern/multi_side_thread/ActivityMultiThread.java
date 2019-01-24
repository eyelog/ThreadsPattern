package ru.eyelog.threadspattern.multi_side_thread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.eyelog.threadspattern.R;

public class ActivityMultiThread extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    Thread thread[] = new Thread[3];

    private int a = 0;
    private int steps = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_threads);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            steps = Integer.parseInt(editText.getText().toString());

            // Несинхронные потоки неполедовательны
            for (int i = 0; i < 3; i++) {
//                thread[i] = new Thread(new CustomRunnable(steps * i));
//                thread[i].start();
                startThread(i, steps);
            }

        });
    }

    synchronized void startThread(int i, int steps){
        thread[i] = new Thread(new CustomRunnable(steps * i));
        thread[i].start();
    }

    @SuppressLint("HandlerLeak")
    class CustomHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            String stA = bundle.getString("a");
            textView.setText(stA);
        }
    }

    class CustomRunnable implements Runnable{

        CustomHandler customHandler;
        Message message;
        Bundle bundle;

        int steps;

        CustomRunnable(int steps) {
            this.steps = steps;
            customHandler = new CustomHandler();
            bundle = new Bundle();
        }

        @Override
        public void run() {
            for (int i = 0; i < steps; i++) {
                a++;
                bundle.putString("a", String.valueOf(a));
                message = customHandler.obtainMessage();
                message.setData(bundle);
                customHandler.sendMessage(message);
                Log.i("Logcat", "a = " + a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            bundle.putString("a", a + " расчёт окончен");
            message = customHandler.obtainMessage();
            message.setData(bundle);
            customHandler.sendMessage(message);
        }
    }
}
