package ru.eyelog.threadspattern.rx_thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.eyelog.threadspattern.R;

public class ActivityRxThread extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {

            int steps = Integer.parseInt(editText.getText().toString());

            Observable<String> observable = Observable.create(emitter -> {

                for (int i = 0; i < steps; i++) {
                    a++;
                    Log.i("Logcat", "a = " + a);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Полылка подписчику со следующим шагом
                    emitter.onNext(String.valueOf(a));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Сигнал подписчику о завершении работы потока.
                emitter.onComplete();

            });

            observable.subscribeOn(Schedulers.computation())
                    // Работаем в фоновом потоке.
                    .subscribeOn(Schedulers.io())
                    // Результаты обрабатываем в основном потоке
                    .observeOn(AndroidSchedulers.mainThread())
                    // Команда которая принимает посылки от onNext()
                    .doOnSubscribe(disposable -> textView.setText("Start counting"))
                    // Команда которая принимает сигналы от onComplete()
                    .doAfterTerminate(() -> textView.setText("Counted to " + a))
                    .subscribe(s -> textView.setText(s));
        });
    }
}
