package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;

/**
 * Created by Alip on 4/29/2016.
 */
public class SliderLayoutRec extends SliderLayout {

    public SliderLayoutRec(Context context) {
        super(context);
    }

    public SliderLayoutRec(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderLayoutRec(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int adjustedHeight = (int) (View.MeasureSpec.getSize(widthMeasureSpec) * 0.50);
        int measureSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int adjustedHeightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(adjustedHeight, measureSpecMode);

        super.onMeasure(widthMeasureSpec, adjustedHeightMeasureSpec);
    }
}
