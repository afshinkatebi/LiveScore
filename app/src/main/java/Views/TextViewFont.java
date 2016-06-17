package Views;

/**
 * Created by Ali on 11/17/2015.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.afshin.livescore.R;

import Helpers.TypeFaceHelper;


public class TextViewFont extends TextView {

    private Typeface mTypeface;
    private static ColorStateList defaultColor;


    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public TextViewFont(Context context) {
        super(context);
//        if (!isInEditMode()) {
            mTypeface = TypeFaceHelper.get(context,0);
            this.setTypeface(mTypeface, Typeface.NORMAL);
//        }
    }

    public void init(Context context, AttributeSet attrs) {
//        if (!isInEditMode()) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewFont, 0, 0);
        int font_number = a.getInteger(R.styleable.TextViewFont_font, 0);
        mTypeface = TypeFaceHelper.get(context,font_number);
        this.setTypeface(mTypeface, Typeface.NORMAL);
//    }
    }

    public void setError(Boolean status) {
        if (defaultColor == null) defaultColor = getTextColors();
        if (status) {
            setTextColor(Color.RED);
        } else {
            setTextColor(defaultColor);
        }
    }

}