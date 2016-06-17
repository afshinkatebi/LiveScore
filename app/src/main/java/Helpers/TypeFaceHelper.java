package Helpers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Alip on 3/9/2016.
 */
public class TypeFaceHelper {

    private static Typeface typeface_iranSans;
    private static Typeface typeface_iranSansBold;

    public static Typeface iranSans(Context context){
        if(typeface_iranSans !=null)
            return typeface_iranSans;
            else {
            typeface_iranSans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans-edited.ttf");
            return typeface_iranSans;
        }
    }

    private static Typeface iranSansBold(Context context) {
        if (typeface_iranSansBold != null)
            return typeface_iranSansBold;
        else {
            typeface_iranSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans-Bold-edited.ttf");
            return typeface_iranSansBold;
        }
    }

    public static Typeface get(Context context,int font_number) {
        switch (font_number)
        {
            case 0:
                return iranSans(context);
            case 1:
                return iranSansBold(context);
            default:
                return iranSans(context);
        }
    }


}
