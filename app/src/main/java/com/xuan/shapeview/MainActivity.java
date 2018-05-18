package com.xuan.shapeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ShapeView shape_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shape_view = findViewById(R.id.shape_view);
    }

    public void drawCircle(View view) {
        shape_view.setShape(ShapeView.Shape.circle);
    }

    public void drawSquare(View view) {
        shape_view.setShape(ShapeView.Shape.square);
    }

    public void drawTriangle(View view) {
        shape_view.setShape(ShapeView.Shape.triangle);
    }

    public void autoChange(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shape_view.autoChange();
                        }
                    });
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
