package Helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ir.irdevelopers.Tamashachi.R;

import Views.TextViewFont;

/**
 * Created by Alip on 4/11/2016.
 */
public class EPAlertDialog {
    private final Context context;
    private String title;
    private String message;
    private OnPositiveButtonClickListener posetiveButtonClickListener;
    private OnNegativeButtonClickListener negativeButtonClickListener;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String posetiveButtonText;
    private String negativeButtonText;
    private TextViewFont tv_title;
    private TextViewFont tv_message;
    private TextViewFont tv_positive_button;
    private TextViewFont tv_negative_button;
    private boolean cancelable;
    private int positiveButtonColor=-1;
    private int negativeButtonColor=-1;

    public EPAlertDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPositiveButton(String positiveButtonText, OnPositiveButtonClickListener positiveButtonClickListener) {
        this.posetiveButtonText = positiveButtonText;
        this.posetiveButtonClickListener = positiveButtonClickListener;
    }

    public void setNegativeButton(String negativeButtonText, OnNegativeButtonClickListener negativeButtonClickListener) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButtonClickListener = negativeButtonClickListener;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.ep_dialog_alert, null);
        tv_title = (TextViewFont) view.findViewById(R.id.title);
        tv_message = (TextViewFont) view.findViewById(R.id.message);
        tv_positive_button = (TextViewFont) view.findViewById(R.id.positive_button);
        tv_negative_button = (TextViewFont) view.findViewById(R.id.negative_button);


        if (negativeButtonColor != -1)
            tv_negative_button.setTextColor(negativeButtonColor);
        if (positiveButtonColor != -1)
            tv_positive_button.setTextColor(positiveButtonColor);

        if (title != null) tv_title.setText(title);
        if (message != null) tv_message.setText(message);
        if (posetiveButtonClickListener != null) {
            tv_positive_button.setText(posetiveButtonText);
            tv_positive_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posetiveButtonClickListener.onClick();
                }
            });
        } else {
            tv_positive_button.setVisibility(View.GONE);
        }

        if (negativeButtonClickListener != null) {
            tv_negative_button.setText(negativeButtonText);
            tv_negative_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick();
                }
            });
        } else {
            tv_negative_button.setVisibility(View.GONE);
        }
        builder.setView(view);
        alertDialog = builder.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }

    public interface OnPositiveButtonClickListener {
        void onClick();
    }

    public interface OnNegativeButtonClickListener {
        void onClick();
    }

    public void setPositiveButtonColor(String color) {
        this.positiveButtonColor = Color.parseColor(color);
    }

    public void setPositiveButtonColor(int color) {
        this.positiveButtonColor = color;
    }

    public void setNegativeButtonColor(String color) {
        this.negativeButtonColor = Color.parseColor(color);
    }

    public void setNegativeButtonColor(int color) {
        this.negativeButtonColor = color;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        builder.setCancelable(cancelable);
    }


}
