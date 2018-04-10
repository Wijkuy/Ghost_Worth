package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private String wordFragment = "";
    private Button challenge;
    private Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        final Button restart = (Button) findViewById(R.id.restart);

        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                wordFragment = "";
                userTurn = random.nextBoolean();
                Log.i("WordFragment", Boolean.toString(userTurn));
                TextView text = (TextView) findViewById(R.id.ghostText);
                text.setText("");
                TextView label = (TextView) findViewById(R.id.gameStatus);
                if (userTurn) {
                    label.setText(USER_TURN);
                } else {
                    label.setText(COMPUTER_TURN);
                    computerTurn();
                }
                Log.i("WordFragment", "reset");
            }
        });
        final Button challenge = (Button) findViewById(R.id.challengeOpponent);

        challenge.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View arg0) {

                                             TextView label = (TextView) findViewById(R.id.gameStatus);
                                             if (wordFragment.length() > 3 && dictionary.isWord(wordFragment)) {

                                                 label.setText("You Win");

                                             } else if (wordFragment.length() > 3 && !dictionary.isWord(wordFragment) && dictionary.getAnyWordStartingWith(wordFragment) != null) {
                                                 label.setText("Computer Wins, this word is a prefix of: " + dictionary.getAnyWordStartingWith(wordFragment));
                                             } else if (wordFragment.length() > 3 && !dictionary.isWord(wordFragment) && dictionary.getAnyWordStartingWith(wordFragment) == null) {
                                                 label.setText("You Win");
                                             }
                                         }
                                     }

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);
        char key = (char) event.getUnicodeChar();
        String keyString = key + "";
        if (Arrays.toString(alphabet).contains(keyString.toLowerCase())) {
            wordFragment += key;
            text.append(key + "");
            return true;
        }
        if (event.getAction() == KeyEvent.ACTION_UP) {
            Log.i("WordFragment", "Computers Turn");
            userTurn = false;
            computerTurn();
        }

        return super.onKeyUp(keyCode, event);

    }


    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);

        if (wordFragment.equals("")) {
            wordFragment = dictionary.getAnyWordStartingWith("");
            text.append(wordFragment);
            userTurn = true;
            label.setText(USER_TURN);
        }

        if (wordFragment.length() < 4) {
            label.setText("Computer Wins");
        }

        if (wordFragment.length() > 3 && dictionary.getAnyWordStartingWith(wordFragment) != null) {
            Log.i("WordFragment", dictionary.getAnyWordStartingWith(wordFragment));
            wordFragment += dictionary.getAnyWordStartingWith(wordFragment).substring(wordFragment.length(), wordFragment.length() + 1);
            text.setText(wordFragment);
        } else {
            label.setText("Computer Wins");
        }


    }


    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    public boolean onStart(View view) {

        userTurn = random.nextBoolean();
        Log.i("WordFragment", Boolean.toString(userTurn));
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

}
