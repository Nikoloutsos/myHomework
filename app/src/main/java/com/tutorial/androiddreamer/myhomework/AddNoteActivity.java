package com.tutorial.androiddreamer.myhomework;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_SUBJECT = "com.tutorial.androiddreamer.myhomework.EXTRA_SUBJECT";
    public static final String EXTRA_NOTE = "com.tutorial.androiddreamer.myhomework.EXTRA_NOTE";
    public static final String EXTRA_IMPORTANCE = "com.tutorial.androiddreamer.myhomework.EXTRA_IMPORTANCE";
    public static final String EXTRA_ID = "com.tutorial.androiddreamer.myhomework.EXTRA_ID";
    public static final int MODE_ADD_NOTE = 0;
    public static final int MODE_EDIT_NOTE = 1;
    private int id;

    private EditText etSubject, etNote;
    private NumberPicker npImportance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etNote = findViewById(R.id.et_activity_add_note_note);
        etSubject = findViewById(R.id.et_activity_add_note_subject);
        npImportance = findViewById(R.id.np_activity_add_note);





        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        changeStatusBarColor("#c77800");
        Intent data = getIntent();
        int mode = data.getIntExtra(MainActivity.EXTRA_MODE,0);

        npImportance.setMinValue(1);
        npImportance.setMaxValue(10);

        if (mode==MODE_ADD_NOTE){
            setTitle("Add note");
        } else{
            setTitle("Edit note");
            etSubject.setText(data.getStringExtra(MainActivity.EXTRA_SUBJECT));
            etNote.setText(data.getStringExtra(MainActivity.EXTRA_NOTE));
            npImportance.setValue(data.getIntExtra(MainActivity.EXTRA_IMPORTANCE, 1));
            Log.d("TESTTTTT", "number is : " +
                    data.getIntExtra(MainActivity.EXTRA_IMPORTANCE, 1) );

            id = data.getIntExtra(MainActivity.EXTRA_ID, -1);

        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_add_note_activity_save_note:
                //TODO save data and send it back to mainactivity to create the note and update the db.
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }


    private void saveNote() {
        String subject = etSubject.getText().toString();
        String note = etNote.getText().toString();
        int importance = npImportance.getValue();

        if (subject.trim().isEmpty() || note.trim().isEmpty()) {
            if(subject.trim().isEmpty() && note.trim().isEmpty()){
                shakeView(etNote);
                shakeView(etSubject);
            }else if(subject.trim().isEmpty()) shakeView(etSubject);
            else if(note.trim().isEmpty()) shakeView(etNote);



            Toast.makeText(this, "Please insert a subject and note", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SUBJECT, subject)
                .putExtra(EXTRA_NOTE, note)
                .putExtra(EXTRA_IMPORTANCE, importance)
                .putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, intent);
        finish();


    }




    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    private void shakeView(View view){
        YoYo.with(Techniques.Shake)
                .duration(500)
                .repeat(0)
                .playOn(view);
    }

}
