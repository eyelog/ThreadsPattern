package ru.eyelog.threadspattern.rx_concat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.eyelog.threadspattern.R;

public class ActivityRxConcatThread extends MvpAppCompatActivity implements ViewState {

    TextView textView;
    EditText editText;
    Button button;

    @InjectPresenter
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            presenter.setState(editText.getText().toString());
        });
    }

    @Override
    public void setState(String string) {
        textView.setText(string);
    }
}
