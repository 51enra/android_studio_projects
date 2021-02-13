package com.arne.tictactoe_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private String currentPlayer = "x";
    private String gameState = "playing";
    private TextView[] allFields = new TextView[9];
    private TextView statusText;
    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView[] allFields = new TextView[9];

        mShowCount = findViewById(R.id.show_count);

        statusText = findViewById(R.id.statusText);

        allFields[0] = findViewById(R.id.f0);
        allFields[1] = findViewById(R.id.f1);
        allFields[2] = findViewById(R.id.f2);
        allFields[3] = findViewById(R.id.f3);
        allFields[4] = findViewById(R.id.f4);
        allFields[5] = findViewById(R.id.f5);
        allFields[6] = findViewById(R.id.f6);
        allFields[7] = findViewById(R.id.f7);
        allFields[8] = findViewById(R.id.f8);


        for (TextView field : allFields) {
            field.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gameState == "ended") {
                        resetGame();
                        return;
                    }

                    if (((TextView) v).getText() == "") {
                        ((TextView) v).setText(currentPlayer);
                        if (checkWin()) {
                            statusText.setText("Spieler " + currentPlayer + " hat gewonnen!");
                            gameState = "ended";
                        } else {
                            boolean freeField = false;
                            for (TextView field : allFields) {
                                if ("".equals(field.getText())) {
                                    freeField = true;
                                }
                            }
                            if (!freeField) {
                                statusText.setText("Unentschieden!");
                                gameState = "ended";
                                return;
                            }
                            currentPlayer = (currentPlayer == "x") ? "o" : "x";
                            statusText.setText("Spieler " + currentPlayer + " ist an der Reihe");
                        }
                    }
                }
            });
        }
    }


    private void resetGame() {
        currentPlayer = "x";
        statusText.setText("Spieler " + currentPlayer + " ist an der Reihe.");

        for (TextView field : allFields) {
            field.setText("");
        }
        gameState = "playing";
    }

    private boolean checkWin() {

        boolean horizontal = false;
        boolean vertical = false;
        boolean diagonal = false;

        if (!"".equals(allFields[0].getText())) {
            if (allFields[0].getText().equals(allFields[1].getText()) && allFields[0].getText().equals(allFields[2].getText())) {
                horizontal = true;
            }
            if (allFields[0].getText().equals(allFields[3].getText()) && allFields[0].getText().equals(allFields[6].getText())) {
                vertical = true;
            }
            if (allFields[0].getText().equals(allFields[4].getText()) && allFields[0].getText().equals(allFields[8].getText())) {
                diagonal = true;
            }
        }

        if (!"".equals(allFields[1].getText())) {
            if (allFields[1].getText().equals(allFields[4].getText()) && allFields[1].getText().equals(allFields[7].getText())) {
                vertical = true;
            }
        }

        if (!"".equals(allFields[2].getText())) {
            if (allFields[2].getText().equals(allFields[5].getText()) && allFields[2].getText().equals(allFields[8].getText())) {
                vertical = true;
            }
            if (allFields[2].getText().equals(allFields[4].getText()) && allFields[2].getText().equals(allFields[6].getText())) {
                diagonal = true;
            }
        }

        return horizontal || vertical || diagonal;
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCount++;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }
}
