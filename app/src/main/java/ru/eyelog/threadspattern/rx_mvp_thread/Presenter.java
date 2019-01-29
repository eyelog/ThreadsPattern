package ru.eyelog.threadspattern.rx_mvp_thread;

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


    public void setState(String string){
        int steps = Integer.parseInt(string);

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
                .doOnSubscribe(disposable -> getViewState().setState("Start counting"))
                // Команда которая принимает сигналы от onComplete()
                .doAfterTerminate(() -> getViewState().setState("Counted to " + a))
                .subscribe(s -> getViewState().setState(s));
    }
}
