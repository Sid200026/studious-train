package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int cur = 0;
    int gameState[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    Boolean weHaveWinner = false;
    Boolean draw = false;

    public void dropIn ( View view ) {
        if(!weHaveWinner) {
            ImageView img = findViewById(view.getId());
            TextView win = findViewById(R.id.winner);
            int index = Integer.parseInt(img.getTag().toString());
            if(gameState[index]!=-1)
                return;
            gameState[index] = cur;
            img.setTranslationY(-2000);
            if (cur == 0) {
                cur = 1;
                img.setImageResource(R.drawable.x);
            } else {
                cur = 0;
                img.setImageResource(R.drawable.o);
            }
            img.animate().translationYBy(2000).rotation(3600).setDuration(100);
            if (checkWinner()) {
                weHaveWinner = true;
                String message;
                if(draw)
                {
                    message = "Match Drawn";
                }
                else {
                    if (cur == 0)
                        message = "Blue has won the game";
                    else
                        message = "Red has won the game";
                }
                win.animate().alpha(1);
                win.setText(message);
            }
        }
    }

    public Boolean checkWinner () {
        if(gameState[0] == gameState[1] && gameState[1] == gameState[2] && gameState[0]!=-1)
            return true;
        if(gameState[3] == gameState[4] && gameState[4] == gameState[5] && gameState[3]!=-1)
            return true;
        if(gameState[6] == gameState[7] && gameState[7] == gameState[8] && gameState[6]!=-1)
            return true;
        if(gameState[0] == gameState[3] && gameState[6] == gameState[3] && gameState[0]!=-1)
            return true;
        if(gameState[1] == gameState[4] && gameState[1] == gameState[7] && gameState[1]!=-1)
            return true;
        if(gameState[2] == gameState[5] && gameState[2] == gameState[8] && gameState[2]!=-1)
            return true;
        if(gameState[0] == gameState[4] && gameState[0] == gameState[8] && gameState[0]!=-1)
            return true;
        if(gameState[2] == gameState[4] && gameState[6] == gameState[2] && gameState[2]!=-1)
            return true;
        Boolean drawFlag = true;
        for(int i=0;i<9;i++){
            if(gameState[i] == -1) {
                drawFlag = false;
            }
            if(!drawFlag){
                break;
            }
        }
        if(drawFlag)
        {
            draw = true;
            return true;
        }
        return false;

    }

    public void reset ( View view ) {
        cur = 0;
        draw = false;
        for(int i=0;i<=8;i++) {
            gameState[i] = -1;
        }
        weHaveWinner = false;
        TextView win = findViewById(R.id.winner);
        win.animate().alpha(0);
        win.setText("");

        ImageView img = findViewById(R.id.imageView1);
        img.setImageResource(0);

        img = findViewById(R.id.imageView2);
        img.setImageResource(0);

        img = findViewById(R.id.imageView3);
        img.setImageResource(0);

        img = findViewById(R.id.imageView4);
        img.setImageResource(0);

        img = findViewById(R.id.imageView5);
        img.setImageResource(0);

        img = findViewById(R.id.imageView6);
        img.setImageResource(0);

        img = findViewById(R.id.imageView7);
        img.setImageResource(0);

        img = findViewById(R.id.imageView8);
        img.setImageResource(0);

        img = findViewById(R.id.imageView9);
        img.setImageResource(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
