package com.example.memgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int numRows = 6;
    int numColumns = 5;
    HashMap<String, ImageView> images;
    int click = 0;
    String firstCard = "";
    int numPairs = 0;
    int numPairsOne = 0;
    int numPairsTwo = 0;
    int firstPos = 0;
    String tagCard;
    int posCard;
    String firstTag;
    boolean playerOne = true;
    boolean playerTwo = false;
    TextView edittext1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Integer[] drawableList = new Integer[]{
                R.drawable.card1,
                R.drawable.card2,
                R.drawable.card3,
                R.drawable.card4,
                R.drawable.card5,
                R.drawable.card6,
                R.drawable.card7,
                R.drawable.card8,
                R.drawable.card9,
                R.drawable.card10,
                R.drawable.card11,
                R.drawable.card12,
                R.drawable.card13,
                R.drawable.card14,
                R.drawable.card15,
                R.drawable.card1,
                R.drawable.card2,
                R.drawable.card3,
                R.drawable.card4,
                R.drawable.card5,
                R.drawable.card6,
                R.drawable.card7,
                R.drawable.card8,
                R.drawable.card9,
                R.drawable.card10,
                R.drawable.card11,
                R.drawable.card12,
                R.drawable.card13,
                R.drawable.card14,
                R.drawable.card15
        };

        edittext1 = (TextView) findViewById(R.id.tvDisplay);
        edittext1.setText("Playar1: " + numPairsOne + " Playar2: " + numPairsTwo);

        List<Integer> list = Arrays.asList(drawableList);
        Collections.shuffle(list);
        list.toArray(drawableList);

        images = new HashMap<>();
        LinearLayout llmain = findViewById(R.id.lvmain);
        for (int row = 1; row <= 6; row++){
            LinearLayout llrow = new LinearLayout(this);
            llrow.setOrientation(LinearLayout.HORIZONTAL);
            for (int col = 1; col <= numColumns; col++){
                ImageView iv = new ImageView(this);
                iv.setTag(row+","+col);
                images.put(row+","+col, iv);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
                layoutParams.weight = 1;
                layoutParams.topMargin = 100;
                iv.setLayoutParams(layoutParams);
                iv.setImageResource(R.drawable.cardclosed);
                final int pos = (row * 5 - (5 - col)) - 1;
                iv.setOnClickListener((v)->{
                    if(click < 2) {
                        click++;
                        //Toast.makeText(MainActivity.this, "Kliknuo si na sliku "+v.getTag().toString(), Toast.LENGTH_SHORT).show();
                        iv.setImageResource(drawableList[pos]);
                        iv.setContentDescription(drawableList[pos].toString());
                        posCard = pos;
                        tagCard = (String) iv.getTag();
                    }
                    if (click == 1) {
                        firstCard = (String) images.get(tagCard).getContentDescription();
                        firstTag = tagCard;
                        firstPos = posCard;
                    } else if (click == 2) {
                        //if (firstCard == (String) images.get(tagCard).getContentDescription()) {
                          if(drawableList[firstPos].equals(drawableList[pos])){
                              if(playerOne == true){
                                  numPairsOne++;
                                  edittext1.setText("Playar1: " + numPairsOne + " Playar2: " + numPairsTwo);
                                  //edittext2.setText(" Playar2: " + numPairsTwo);
                              }
                              if(playerTwo == true){
                                  numPairsTwo++;
                                  edittext1.setText("Playar1: " + numPairsOne + " Playar2: " + numPairsTwo);
                                  //edittext2.setText("Playar2: " + numPairsTwo);
                              }
                              if(numPairsOne + numPairsTwo == 15){
                                  if (numPairsOne < numPairsTwo){
                                      edittext1.setText("Player 2 is the winner");
                                      Handler handler = new Handler();
                                      handler.postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              finish();
                                              startActivity(getIntent());
                                          }
                                      }, 2000);
                                  }
                                  else if (numPairsTwo < numPairsOne) {
                                      edittext1.setText("Player 1 is the winner");
                                      Handler handler1 = new Handler();
                                      handler1.postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              finish();
                                              startActivity(getIntent());
                                          }
                                      }, 2000);
                                  }
                                  else{
                                      edittext1.setText("Player 1 and Player 2 have equal points.");
                                      Handler handler2 = new Handler();
                                      handler2.postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              finish();
                                              startActivity(getIntent());
                                          }
                                      }, 2000);
                                  }
                              }
                            click = 0;
                        } else {
                            Handler handler3 = new Handler();
                            handler3.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    images.get(tagCard).setImageResource(R.drawable.cardclosed);
                                    images.get(firstTag).setImageResource(R.drawable.cardclosed);
                                    if( playerOne == true && click == 2){
                                        playerTwo = true;
                                        playerOne = false;
                                        edittext1.setText("Playar1: " + numPairsOne + " Playar2: " + numPairsTwo);
                                        click = 0;
                                    }
                                    if(playerTwo == true && click == 2){
                                        playerOne = true;
                                        playerTwo = false;
                                        edittext1.setText("Playar1: " + numPairsOne + " Playar2: " + numPairsTwo);
                                        click = 0;
                                    }
                                    }
                            }, 2000);

                        }
                    }
                });
                llrow.addView(iv);
            }
            llmain.addView(llrow);
        }
    }
}