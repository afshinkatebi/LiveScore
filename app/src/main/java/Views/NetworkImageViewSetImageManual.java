package Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.afshin.livescore.R;


public class NetworkImageViewSetImageManual extends NetworkImageView {

    public Bitmap mLocalBitmap;

    private boolean mShowLocal;
    private ImageLoader.ImageContainer mImageContainer;

    public void setLocalImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mShowLocal = true;
        }
        this.mLocalBitmap = bitmap;
        requestLayout();
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        mShowLocal = false;
        super.setImageUrl(url, imageLoader);
    }

    public NetworkImageViewSetImageManual(Context context) {
        this(context, null);
    }

    public NetworkImageViewSetImageManual(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageViewSetImageManual(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (mShowLocal) {
            setImageBitmap(mLocalBitmap);
        }
    }

    public void setImageUrlWithAnim(String url, ImageLoader imageLoader) {
        mShowLocal = false;

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                mImageContainer = response;
                setVisibility(INVISIBLE);

                setLocalImageBitmap(response.getBitmap());
                Animation fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                fadein.setDuration(650);
                fadein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                startAnimation(fadein);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mImageContainer!=null){
            mImageContainer.cancelRequest();
            mImageContainer=null;
        }
        super.onDetachedFromWindow();

    }
}
