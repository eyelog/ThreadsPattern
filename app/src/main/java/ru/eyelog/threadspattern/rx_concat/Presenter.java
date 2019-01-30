package ru.eyelog.threadspattern.rx_concat;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class Presenter extends MvpPresenter<ViewState> {

    int a = 0;

    public Presenter() {
    }


    @SuppressLint("CheckResult")
    public void setState(String string){
        int steps = Integer.parseInt(string);

        Observable<String> observableFirst = Observable.create(emitter -> {

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

        Observable<String> observableSecond = Observable.create(emitter -> {

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

        Observable.concat(observableFirst, observableSecond).subscribeOn(Schedulers.io())
                // Результаты обрабатываем в основном потоке
                .observeOn(AndroidSchedulers.mainThread())
                // Команда которая принимает посылки от onNext()
                .doOnSubscribe(disposable -> getViewState().setState("Start counting"))
                // Команда которая принимает сигналы от onComplete()
                .doAfterTerminate(() -> getViewState().setState("Counted to " + a))
                .subscribe(s -> getViewState().setState(s));
    }
}
