package com.ir.irdevelopers.Tamashachi;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MaterialIntroActivity extends IntroActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        setButtonBackVisible(false);
        setButtonNextVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .fragment(R.layout.fragment_slider1,R.style.AppThemeDark2)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .fragment(R.layout.fragment_slider2, R.style.AppThemeDark2)
                .build());


    }

}
