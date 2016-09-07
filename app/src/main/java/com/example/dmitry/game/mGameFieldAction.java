package com.example.dmitry.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Dmitry on 26.08.2016.
 */
public class mGameFieldAction {

    final byte SWIPE_LEFT = 0;
    final byte SWIPE_RIGHT = 1;
    final byte SWIPE_TOP = 2;
    final byte SWIPE_DOWN = 3;

    final String TOTAL_SCORE = "Total_Score";


    int[][] arr;
    TextView gameField;
    int[][] checkArr;
    TextView[][] txtGameField;
    TextView txtScore;
    TextView tvTotalScore;
    int score = 0;

    SharedPreferences totalScore;
    int totalScoreValue;

    public mGameFieldAction(int[][] gameFieldArr, TextView gameField,TextView[][] txtGameField,TextView txtScore,TextView tvTotalScore,Context context){


        arr = gameFieldArr;
        this.gameField = gameField;
        this.txtGameField = txtGameField;
        this.txtScore = txtScore;
        this.tvTotalScore = tvTotalScore;
        txtScore.setText(score+"");

        totalScore = context.getSharedPreferences("Total score",Context.MODE_PRIVATE);

        if(totalScore.getInt(TOTAL_SCORE,-1) == -1){
            Log.i("MY_TAG","Game started for first time");
            //If game first Started
            updateTotalScore(0);

        }else{
            totalScoreValue = totalScore.getInt(TOTAL_SCORE,0);
            tvTotalScore.setText(totalScoreValue+"");
        }


    }


    public void swipe(byte direction){
        Log.i("MY_TAG", "DIRECTION " + direction);

        switch(direction){
            case SWIPE_LEFT:{
                swipeLeft();
                break;
            }
            case SWIPE_RIGHT:{
                swipeRight();
                break;
            }
            case SWIPE_TOP:{
                swipeTop();
                break;
            }
            case SWIPE_DOWN:{
                swipeDown();
                break;
            }
        }
    }



    private void swipeRight(){
        boolean done = false;
        while(!done){
            done = true;
            for(int i = 0;i<4;i++){
                for(int j = 0;j<arr.length;j++){
                    if(arr[i][j] != 0 & j != 3){
                        if(arr[i][j] == arr[i][j+1]){
                            arr[i][j] = 0;
                            score += arr[i][j+1] *= 2;

                            done = false;
                        }
                        if(arr[i][j] != 0 & arr[i][j+1] == 0){
                            arr[i][j+1] = arr[i][j];
                            arr[i][j] = 0;
                            done = false;
                        }
                    }
                    //if(done) break;

                }
            }

        }
        updateScreen(gameField);

    }

    private void swipeLeft(){
        boolean done = false;
        while(!done){
            done = true;
            for(int i = 0;i < 4;i++){
                for(int j = 3;j>=0;j--){
                    if(arr[i][j] != 0 & j != 0){
                        if(arr[i][j] == arr[i][j-1]){
                            arr[i][j] = 0;
                            score += arr[i][j-1] *= 2;
                            done = false;
                        }
                        if(arr[i][j] != 0 & arr[i][j-1] == 0){
                            arr[i][j-1] = arr[i][j];
                            arr[i][j] = 0;
                            done = false;
                        }
                    }
                    //if(done) break;

                }

            }


        }
        updateScreen(gameField);
    }

    private void swipeDown(){
        boolean done = false;
        while(!done){
            done = true;
            for(int i = 0;i<4;i++){
                for(int j = 0;j<arr.length;j++){
                    if(arr[i][j] != 0 & i != 3){
                        if(arr[i][j] == arr[i+1][j]){
                            arr[i][j] = 0;
                            score += arr[i+1][j] *= 2;
                            done = false;
                        }
                        if(arr[i][j] != 0 & arr[i+1][j] == 0){
                            arr[i+1][j] = arr[i][j];
                            arr[i][j] = 0;
                            done = false;
                        }
                    }
                    //if(done) break;

                }
            }

        }
        updateScreen(gameField);
    }

    private void swipeTop(){
        boolean done = false;
        while(!done){
            done = true;
            for(int i = 0;i<4;i++){
                for(int j = 0;j<arr.length;j++){
                    if(arr[i][j] != 0 & i != 0){
                        if(arr[i][j] == arr[i-1][j]){
                            arr[i][j] = 0;
                            score += arr[i-1][j] *= 2;
                            done = false;
                        }
                        if(arr[i][j] != 0 & arr[i-1][j] == 0){
                            arr[i-1][j] = arr[i][j];
                            arr[i][j] = 0;
                            done = false;
                        }
                    }
                    //if(done) break;

                }
            }

        }
        updateScreen(gameField);
    }

    public void updateScreen(TextView arrShow){

        if(score > totalScoreValue){
            updateTotalScore(score);
            tvTotalScore.setText(score+"");
        }



        if(checkArr == null) {
            checkArr = new int[4][4];
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    checkArr[k][j] = arr[k][j];
                }
            }
            Log.i("MY_TAG","CHECK_ARR EQUALS NULL");

            addNewItem();

        }
        else {

            boolean isEquals = true;




            for (int i = 0; i < 4; i++) {
                if (!Arrays.equals(arr[i], checkArr[i])) {
                    isEquals = false;

                }
            }
            Log.i("MY_TAG", "isEquals = " + isEquals);


            if(!isEquals){

                addNewItem();
                for (int k = 0; k < 4; k++) {
                    for (int j = 0; j < 4; j++) {
                        checkArr[k][j] = arr[k][j];
                    }
                }
            }
        }


        //set values on the screen
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                if(arr[i][j] == 0)
                    txtGameField[i][j].setText("");
                else {
                    txtGameField[i][j].setText(arr[i][j] + "");
                    if(arr[i][j] <100){
                        txtGameField[i][j].setTextSize(40);
                    }
                    else if(arr[i][j]>100 & arr[i][j] <1000){
                        txtGameField[i][j].setTextSize(35);
                    }
                    else
                        txtGameField[i][j].setTextSize(30);
                }
            }
        }
        //update score
        txtScore.setText(score+"");

    }

    private void addNewItem() {
        boolean isEmpty = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arr[i][j] == 0)
                    isEmpty = true;

            }
        }
        if (isEmpty) {
            Random random = new Random();
            int x;
            int y;
            do {
                x = random.nextInt((3) + 1);
                y = random.nextInt((3) + 1);

            } while (arr[x][y] != 0);

            arr[x][y] = 2;
        }
    }

    private void updateTotalScore(int scoreValue){
        SharedPreferences.Editor editor = totalScore.edit();
        editor.putInt(TOTAL_SCORE,scoreValue);
        editor.commit();
        totalScoreValue = scoreValue;
        Log.i("MY_TAG","TOTAL SCORE = " + scoreValue);
    }
}
