package Helpers;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import Views.SpotlightView;

/**
 * Created by afshin on 6/26/2016.
 */
public class IntroCreator {

    public static void showIntro(Activity activity,View view, String usageId,String title,String description) {
        new SpotlightView.Builder(activity)
                .introAnimationDuration(400)
                .enableRevalAnimation(false)
                .performClick(true)
                .fadeinTextDuration(400)
                //.setTypeface(FontUtil.get(this, "RemachineScript_Personal_Use"))
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(24)
                .headingTvText(title)
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(14)
                .subHeadingTvText(description)
                .maskColor(Color.parseColor("#dc000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .enableDismissAfterShown(true)
                .usageId(usageId)
                .show();
    }
}
