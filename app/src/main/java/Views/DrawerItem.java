package Views;

/**
 * Created by Ali on 11/17/2015.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.afshin.livescore.R;


/**
 * Created by afshinkatebi on 10/28/2015.
 */
public class DrawerItem extends RelativeLayout {
    Context context;
    TextView textView;
    ImageView imageView;

    public DrawerItem(Context context) {
        super(context);
        this.context = context;

    }

    public DrawerItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawerItem, 0, 0);
        String title = a.getString(R.styleable.DrawerItem_drawer_title);
        Integer image = a.getResourceId(R.styleable.DrawerItem_drawer_icon, 0);
        Integer background_image_color = a.getColor(R.styleable.DrawerItem_drawer_icon_background_color, 0);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout drawerItem = (RelativeLayout) inflater.inflate(R.layout.drawer_item, null);
        textView = (TextView) drawerItem.findViewById(R.id.drawer_title);
        imageView = (ImageView) drawerItem.findViewById(R.id.drawer_image);
        textView.setText(title);
        imageView.setImageResource(image);

        GradientDrawable bgShape = (GradientDrawable) imageView.getBackground();
        //bgShape.setStroke(15,background_image_color);
        bgShape.setColor(background_image_color);
        //        imageView.setBackgroundColor(background_image_color);
        this.addView(drawerItem);
        a.recycle();
    }

    public DrawerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawerItem, 0, 0);
        String title = a.getString(R.styleable.DrawerItem_drawer_title);
        Integer image = a.getResourceId(R.styleable.DrawerItem_drawer_icon, 0);
        Integer background_image_color = a.getColor(R.styleable.DrawerItem_drawer_icon_background_color, 0);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout drawerItem = (RelativeLayout) inflater.inflate(R.layout.drawer_item, null);
        textView = (TextView) drawerItem.findViewById(R.id.drawer_title);
        imageView = (ImageView) drawerItem.findViewById(R.id.drawer_image);
        textView.setText(title);
        imageView.setImageResource(image);
//        imageView.setBackgroundColor(background_image_color);
        GradientDrawable bgShape = (GradientDrawable) imageView.getBackground();
        //bgShape.setStroke(15,background_image_color);
        bgShape.setColor(background_image_color);

        this.addView(drawerItem);

        a.recycle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //performClick();
            callOnClick();
        }
        return super.dispatchTouchEvent(event);
    }


}