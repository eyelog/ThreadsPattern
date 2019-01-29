package ru.eyelog.threadspattern.rx_mvp_thread;

import com.arellomobile.mvp.MvpView;

public interface ViewState extends MvpView {
    void setState(String string);
}
