package ru.eyelog.threadspattern.rx_concat;

import com.arellomobile.mvp.MvpView;

public interface ViewState extends MvpView {
    void setState(String string);
}
