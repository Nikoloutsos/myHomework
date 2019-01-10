package com.tutorial.androiddreamer.myhomework.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tutorial.androiddreamer.myhomework.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableLastSlideAlphaExitTransition(true);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.pink_red)
                .buttonsColor(R.color.blue)
                .image(R.drawable.ic_blackbox)
                .title(getString(R.string.intro_add_note_title))
                .description(getString(R.string.intro_add_note_description))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.blue)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_add)
                .title(getString(R.string.intro_delete_note_title))
                .description(getString(R.string.intro_delete_note_description))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.green)
                .buttonsColor(R.color.orange)
                .image(R.drawable.ic_finish)
                .title(getString(R.string.intro_archive_note_title))
                .description(getString(R.string.intro_archive_note_description))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.orange)
                .buttonsColor(R.color.green)
                .image(R.drawable.ic_inbox)
                .title(getString(R.string.intro_statistics_note_title))
                .description(getString(R.string.intro_statistics_note_description))
                .build());
    }


}
