package com.spandr.meme.core.activity.notepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;

/**
 *  Defines main logic for handling of notepad editor activity needs
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class NotepadEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad_editor);
    }

}
