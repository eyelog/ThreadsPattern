package ru.eyelog.threadspattern.main_thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.eyelog.threadspattern.R;

public class ActivityMainThread extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    private int a = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> doCountActivity(Integer.parseInt(editText.getText().toString())));
    }

    private void doCountActivity(int steps) {

        for (int i = 0; i < steps; i++) {
            synchronized (this){
                a++;
                Log.i("Logcat", "a = " + a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Отображение счётчика мы не увидим =(
                textView.setText(String.valueOf(a));
            }
        }

        textView.setText(a + " расчёт окончен");
    }
}
