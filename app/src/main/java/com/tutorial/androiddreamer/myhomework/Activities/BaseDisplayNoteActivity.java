package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.DisplayNoteActivityViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseDisplayNoteActivity extends AppCompatActivity {

    protected TextToSpeech tts;
    protected boolean isTtsOkay = false;
    protected DisplayNoteActivityViewModel viewModel;
    @BindView(R.id.tv_description_activity_display_note)
    TextView tvDescription;
    @BindView(R.id.cl_parent_layout_activity_display_note)
    ConstraintLayout parentConstraintLayout;
    @BindView(R.id.fab_activity_display_note)
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(DisplayNoteActivityViewModel.class);
        super.onCreate(savedInstanceState);
        setUITheme();
        setContentView(R.layout.activity_display_note);
        ButterKnife.bind(this);
        setUIThemeForElements();

        /**
         * Add onClick method in fab
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoiceToText(tvDescription.getText().toString());
            }
        });


        /**
         * Extract information from intent
         */

        Intent receivedIntent = getIntent();

        viewModel.setTitle(receivedIntent.getStringExtra(MainActivity.EXTRA_SUBJECT));
        viewModel.setDescription(receivedIntent.getStringExtra(MainActivity.EXTRA_NOTE));
        viewModel.setImportance(receivedIntent.getIntExtra(MainActivity.EXTRA_IMPORTANCE, 0));
        viewModel.setId(receivedIntent.getIntExtra(MainActivity.EXTRA_ID, 0));


        /**
         * Update UI state
         */
        setTitle(viewModel.getTitle());
        tvDescription.setText(viewModel.getDescription());
        tvDescription.setMovementMethod(new ScrollingMovementMethod());


        /**
         * Enable the guard so as to prevent loss of data due to configuration changes.
         */
        viewModel.setGuard(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * Initializing text-to-speech object
         */
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported!");
                    } else {
                        isTtsOkay = true;
                    }
                } else {
                    Log.e("TTS", "TTS initialization failed!");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    private void setUITheme() {
        if (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.DarkTheme);
        }
    }

    private void setUIThemeForElements() {
        if (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0) {
            parentConstraintLayout.setBackground(getDrawable(R.drawable.veneer_repeating));
        } else if (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1) {
            parentConstraintLayout.setBackground(getDrawable(R.drawable.stardust_repeating));
        }
    }


    private void playVoiceToText(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
