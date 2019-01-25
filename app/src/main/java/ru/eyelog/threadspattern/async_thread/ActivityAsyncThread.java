package ru.eyelog.threadspattern.async_thread;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.eyelog.threadspattern.R;

public class ActivityAsyncThread extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            CustomTask customTask = new CustomTask(Integer.parseInt(editText.getText().toString()));
            customTask.execute();
        });

    }

    @SuppressLint("StaticFieldLeak")
    class CustomTask extends AsyncTask<Void, String, String>{

        private int a = 0;
        private int steps;

        CustomTask(int steps) {
            this.steps = steps;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText(a + " расчёт начат");
        }

        @Override
        protected String doInBackground(Void... params) {

            for (int i = 0; i < steps; i++) {
                a++;
                Log.i("Logcat", "a = " + a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(String.valueOf(a));
            }
            return String.valueOf(a + " расчёт окончен");
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            textView.setText(values[values.length-1]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}
