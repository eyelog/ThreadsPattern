package ru.eyelog.threadspattern.rx_thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.eyelog.threadspattern.R;

public class ActivityRxThread extends AppCompatActivity {

    Integer a = 0;

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
            int val = Integer.parseInt(editText.getText().toString());

            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < val; i++) {
                values.add(i);
            }

            Observable<Integer> observable = Observable.fromArray(0, 1, 2, 3, 4);

            Observer<Integer> observer = new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(Integer integer) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    textView.setText(String.valueOf(integer));
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                    //textView.setText(a + " расчёт окончен");
                }
            };

            observable.subscribe(observer);
        });

    }

}
