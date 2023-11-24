package com.example.krypton;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kryptolib.PaymentzSdk;

public class MainActivity extends Activity {

    private Button paymentzBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentzBtn = (Button) findViewById(R.id.button);
        PaymentzSdk paymentzSdk = new PaymentzSdk();


        paymentzBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentzSdk.login(MainActivity.this, "1","OZa6b6SNays37d+UVaWnfqJsuHI5W+i4SawctRtwy4A=");
            }
        });
    }
}