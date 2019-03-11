package com.spandr.meme.core.activity.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

/**
*  Defines main logic for handling of notepad category activity needs
*
* @author  Spayker
* @version 1.0
* @since   3/10/2019
*/
public class NotepadCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad_category);
    }

    public void clickOnBackButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
