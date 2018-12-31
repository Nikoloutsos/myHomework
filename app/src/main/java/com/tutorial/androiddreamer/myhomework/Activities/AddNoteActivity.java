package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tutorial.androiddreamer.myhomework.Helpers.ActivityMethods;
import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.AddNoteActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddNoteActivity";
    public static final int MODE_ADD_NOTE = 0;
    public static final int MODE_EDIT_NOTE = 1;
    private AddNoteActivityViewModel viewModel;
    private int id;
    private long time;


    @BindView(R.id.tv_importance) TextView tv_importance;
    @BindView(R.id.cl_activity_add_note_parentview) ConstraintLayout cl_parentLayout;
    @BindView(R.id.np_activity_add_note) NumberPicker npImportance;
    @BindView(R.id.et_activity_add_note_subject) TextInputEditText etSubject;
    @BindView(R.id.et_activity_add_note_description) TextInputEditText etNote;
    @BindView(R.id.et_activity_add_note_description_layout) TextInputLayout etNoteLayout;
    @BindView(R.id.et_activity_add_note_subject_layout) TextInputLayout etSubjectLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(AddNoteActivityViewModel.class);
        setUITheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        setUIThemeForElements();
        npImportance.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                viewModel.setPrioritySelected(newVal);
            }
        });

        etSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) viewModel.setUserHasInteracted(true);
            }
        });

        Intent data = getIntent();
        int mode = data.getIntExtra(MainActivity.EXTRA_MODE,0);

        npImportance.setMinValue(1);
        npImportance.setMaxValue(10);

        if (mode==MODE_ADD_NOTE){
            setTitle("Add note");
            npImportance.setValue(viewModel.getPrioritySelected());
        } else{
            setTitle("Edit note");
            etSubject.setText(data.getStringExtra(MainActivity.EXTRA_SUBJECT));
            etNote.setText(data.getStringExtra(MainActivity.EXTRA_NOTE));
            npImportance.setValue(data.getIntExtra(MainActivity.EXTRA_IMPORTANCE, 1));
            id = data.getIntExtra(MainActivity.EXTRA_ID, -1);
            time = data.getLongExtra(MainActivity.EXTRA_TIME, 0);

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

                saveNote();

                return true;
            case android.R.id.home:
                onBackPressed();
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
                shakeView(etNoteLayout);
                shakeView(etSubjectLayout);
            }else if(subject.trim().isEmpty()) shakeView(etSubject);
            else if(note.trim().isEmpty()) shakeView(etNote);



            Toast.makeText(this, "Please insert a subject and note", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_SUBJECT, subject)
                .putExtra(MainActivity.EXTRA_NOTE, note)
                .putExtra(MainActivity.EXTRA_IMPORTANCE, importance)
                .putExtra(MainActivity.EXTRA_ID, id)
                .putExtra(MainActivity.EXTRA_TIME, time);

        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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

    @Override
    public void onBackPressed() {
        if(!viewModel.getUserHasInteracted()){ //If user hasn't interacted with the UI
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }else{
            showAlertDialog();
        }

    }
    private void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.discard_changes_msg);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.discard_btn, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with discard
                viewModel.setUserHasInteracted(false);
                onBackPressed();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.keep_editing_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        ActivityMethods.hideKeyboard(this);
        alertDialogBuilder.show();
    }

    private void setUITheme(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
    private void setUIThemeForElements(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
            cl_parentLayout.setBackgroundColor(Color.WHITE);
            tv_importance.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            cl_parentLayout.setBackgroundColor(getResources().getColor(R.color.DarkGrayBackground));
            tv_importance.setTextColor(Color.WHITE);
            etNote.setTextColor(Color.WHITE);
            etSubject.setTextColor(Color.WHITE);
            etNote.setHintTextColor(Color.WHITE);
            ActivityMethods.setNumberPickerTextColor(npImportance, Color.WHITE);
        }
    }
}
