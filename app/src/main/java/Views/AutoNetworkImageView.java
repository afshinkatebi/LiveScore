package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by afshin on 11/30/2015.
 */
public class AutoNetworkImageView extends NetworkImageViewSetImageManual {
    public AutoNetworkImageView(Context context) {
        super(context);
    }

    public AutoNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int adjustedHeight = (int) (View.MeasureSpec.getSize(widthMeasureSpec) * 0.50);
        int measureSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int adjustedHeightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(adjustedHeight, measureSpecMode);

        super.onMeasure(widthMeasureSpec, adjustedHeightMeasureSpec);    }
}
