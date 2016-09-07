package com.example.dmitry.game;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    RelativeLayout touchSurface;

    int[][] arr = {{0,0,0,0},
                         {0,0,0,0},
                         {0,0,0,0},
                         {0,0,0,0}};
    TextView arrShow;
    TextView txtScore;
    mGameFieldAction gameField;
    float oldTouchCoordinatesX = 0;
    float oldTouchCoordinatesY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchSurface = (RelativeLayout)findViewById(R.id.touchSurface);
        txtScore = (TextView)findViewById(R.id.txtScore);



        touchSurface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float distX;
                float distY;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        Log.i("MY_TAG","onTouch Action Down");
                        oldTouchCoordinatesX = event.getX();
                        oldTouchCoordinatesY = event.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        Log.i("MY_TAG","onTouch Action up");

                        //calculation xAxis
                        boolean xRight;
                        boolean yDown;
                        if(xRight = (event.getX() > oldTouchCoordinatesX)){
                            distX = event.getX()- oldTouchCoordinatesX;
                        }
                        else{
                            distX = Math.abs(oldTouchCoordinatesX-event.getX());
                        }

                        //calculation yAxis
                        if(yDown = (event.getY() > oldTouchCoordinatesY)){
                            distY = event.getY()- oldTouchCoordinatesY;
                        }
                        else{
                            distY = Math.abs(oldTouchCoordinatesY-event.getY());
                        }


                        if(distX>distY){

                            if(xRight)
                                gameField.swipe((byte)1);
                            else
                                gameField.swipe((byte) 0);
                        }
                        else {

                            if (yDown) {
                                Log.i("MY_TAG_TOUCH","DOWN");
                                gameField.swipe((byte) 3);
                            }
                            else {
                                Log.i("MY_TAG_TOUCH","UP");
                                gameField.swipe((byte) 2);
                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });

        //Real Hard Code. Array of textView pass to mGameFieldAction to set values
        TextView[][] txtGameField = {
                {(TextView) findViewById(R.id.txt_cell_0_0),
        (TextView) findViewById(R.id.txt_cell_0_1),
        (TextView) findViewById(R.id.txt_cell_0_2),
        (TextView) findViewById(R.id.txt_cell_0_3)},
                {(TextView) findViewById(R.id.txt_cell_1_0),
        (TextView) findViewById(R.id.txt_cell_1_1),
        (TextView) findViewById(R.id.txt_cell_1_2),
        (TextView) findViewById(R.id.txt_cell_1_3)},
                {(TextView) findViewById(R.id.txt_cell_2_0),
        (TextView) findViewById(R.id.txt_cell_2_1),
        (TextView) findViewById(R.id.txt_cell_2_2),
        (TextView) findViewById(R.id.txt_cell_2_3)},
                {(TextView) findViewById(R.id.txt_cell_3_0),
        (TextView) findViewById(R.id.txt_cell_3_1),
        (TextView) findViewById(R.id.txt_cell_3_2),
        (TextView) findViewById(R.id.txt_cell_3_3)}
        };


        //Customize textView(make text bigger and bolder)
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                txtGameField[i][j].setTextSize(30);
                txtGameField[i][j].setTypeface(null, Typeface.BOLD);
                txtGameField[i][j].setBackground(getDrawable(R.drawable.courners_cells));
            }
        }
        ;

        gameField = new mGameFieldAction(arr,arrShow,txtGameField,txtScore);
    }








}
