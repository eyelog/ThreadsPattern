package ru.eyelog.threadspattern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ru.eyelog.threadspattern.async_thread.ActivityAsyncThread;
import ru.eyelog.threadspattern.main_thread.ActivityMainThread;
import ru.eyelog.threadspattern.multi_side_thread.ActivityMultiThread;
import ru.eyelog.threadspattern.rx_thread.ActivityRxThread;
import ru.eyelog.threadspattern.side_thread.ActivitySideThread;

public class MainActivity extends AppCompatActivity {

    Button btMainThread, btSideThread, btSideMultiThread, btAsyncThread, btRxThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btMainThread = findViewById(R.id.btMainThread);
        btSideThread = findViewById(R.id.btSideThread);
        btSideMultiThread = findViewById(R.id.btSideMultiThread);
        btAsyncThread = findViewById(R.id.btAsyncThread);
        btRxThread = findViewById(R.id.btRxThread);

        btMainThread.setOnClickListener(v -> startActivity(new Intent(this, ActivityMainThread.class)));
        btSideThread.setOnClickListener(v -> startActivity(new Intent(this, ActivitySideThread.class)));
        btSideMultiThread.setOnClickListener(v -> startActivity(new Intent(this, ActivityMultiThread.class)));
        btAsyncThread.setOnClickListener(v -> startActivity(new Intent(this, ActivityAsyncThread.class)));
        btRxThread.setOnClickListener(v -> startActivity(new Intent(this, ActivityRxThread.class)));
    }
}
