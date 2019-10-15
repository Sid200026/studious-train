package com.example.mathsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView countTimer;
    Button playAgain;
    Button button;
    TextView textView;
    EditText editText;
    MediaPlayer mediaPlayer;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> optionA = new ArrayList<>();
    ArrayList<String> optionB = new ArrayList<>();
    ArrayList<String> optionC = new ArrayList<>();
    ArrayList<String> optionD = new ArrayList<>();
    ArrayList<Integer> answer = new ArrayList<>();
    TextView q;
    Button a,b,c,d;
    int correct;
    int attempted;
    int questionIndex;
    TextView score;


    public void checkAnswer (View view) {
        Button ans = findViewById(view.getId());
        int val = Integer.parseInt(ans.getTag().toString());
        if(val == answer.get(questionIndex))
        {
            correct++;
        }
        attempted++;
        questionIndex++;
        score.setText(correct+" / "+attempted);
        displayQuestion(questionIndex);
    }

    public void startAudio() {
        mediaPlayer.start();
    }

    public int getIndex() {
        return questionIndex;
    }

    public void displayQuestion(int index) {
        if(questionIndex>4) {
            questionIndex = 0;
            index = 0;
        }
        q.setText(questions.get(index));
        a.setText(optionA.get(index));
        b.setText(optionB.get(index));
        c.setText(optionC.get(index));
        d.setText(optionD.get(index));

    }

    public void startTimer(final int time) {
        correct = 0;
        attempted = 0;
        score.setText(correct+" / "+attempted);
        questionIndex = 0;
        countTimer = findViewById(R.id.textView2);
        countTimer.setEnabled(true);
        mediaPlayer = MediaPlayer.create(this,R.raw.testcompletion);
        countTimer.setVisibility(View.VISIBLE);
        playAgain = findViewById(R.id.button3);
        q.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);
        new CountDownTimer(time*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timeleft = (int) millisUntilFinished/1000;
                countTimer.setText(timeleft+" secs");
                displayQuestion(getIndex());
            }

            @Override
            public void onFinish() {
                countTimer.setText("Time Up");
                startAudio();
                playAgain.setVisibility(View.VISIBLE);
                playAgain.setEnabled(true);
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
                d.setEnabled(false);
            }
        }.start();
    }

    public void userWantsToPlay( View view ) {
        mediaPlayer.pause();
        playAgain.setVisibility(View.INVISIBLE);
        playAgain.setEnabled(false);
        countTimer.setVisibility(View.INVISIBLE);
        countTimer.setEnabled(false);
        button.setVisibility(View.VISIBLE);
        button.setEnabled(true);
        button.setText("Start");
        textView.setVisibility(View.VISIBLE);
        textView.setEnabled(true);
        editText.setVisibility(View.VISIBLE);
        editText.setEnabled(true);
        q.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
        editText.setText("");
        textView.setText("");
    }

    public void startQuiz( View view ) {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);

        final Button tempButton = findViewById(R.id.button);
        final TextView tempTextView = findViewById(R.id.textView);
        Boolean error = false;
        int value = 30;
        if(editText.getText().toString().length()>0) {
            try {
                value = Integer.parseInt(editText.getText().toString());
            } catch (Exception e) {
                textView.setText("Numbers only");
                error = true;
            }
        }
        if(value>300)
        {
            error = true;
            textView.setText("Maximum time limit is 300 seconds");
        }
        if(value<0) {
            error = true;
            textView.setText("Enter a positive number");
        }
        if(!error) {
            Toast.makeText(this, "The quiz duration is " + value+" secs", Toast.LENGTH_SHORT).show();
            button.setEnabled(false);
            textView.setText("Your Quiz Begins In");
            final int timeForTest = value;
            editText.setVisibility(View.INVISIBLE);
            editText.setEnabled(false);
            new CountDownTimer(5000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int time = (int) millisUntilFinished/1000 + 1;
                    tempButton.setText(time+" secs");
                }

                @Override
                public void onFinish() {
                    tempButton.setVisibility(View.INVISIBLE);
                    tempTextView.setVisibility(View.INVISIBLE);
                    tempTextView.setEnabled(false);
                    startTimer(timeForTest);
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questions.add("4+7");
        optionA.add("11");
        optionB.add("47");
        optionC.add("3");
        optionD.add("15");
        answer.add(0);

        questions.add("18-6");
        optionA.add("186");
        optionB.add("11");
        optionC.add("13");
        optionD.add("12");
        answer.add(3);

        questions.add("23*2");
        optionA.add("23");
        optionB.add("35");
        optionC.add("232");
        optionD.add("46");
        answer.add(3);

        questions.add("108/9");
        optionA.add("11");
        optionB.add("12");
        optionC.add("3");
        optionD.add("10");
        answer.add(1);

        questions.add("71+22");
        optionA.add("73");
        optionB.add("90");
        optionC.add("93");
        optionD.add("103");
        answer.add(2);

        correct = 0;
        attempted = 0;

        q = findViewById(R.id.textView3);
        a = findViewById(R.id.button8);
        b = findViewById(R.id.button9);
        c = findViewById(R.id.button10);
        d = findViewById(R.id.button11);

        questionIndex = 0;

        score = findViewById(R.id.textView4);
    }
}
