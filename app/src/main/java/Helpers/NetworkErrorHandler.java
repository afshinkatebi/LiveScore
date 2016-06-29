package Helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.ir.irdevelopers.Tamashachi.MainActivity;
import com.ir.irdevelopers.Tamashachi.MyApplication;
import com.ir.irdevelopers.Tamashachi.R;

/**
 * Created by Alip on 6/28/2016.
 */
public class NetworkErrorHandler {
    private static boolean isErrorShowing=false;

    public static void handleThisError(final Context context, VolleyError error) {


        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            if (isErrorShowing)return;
            final EPAlertDialog dialog = new EPAlertDialog(context);
            dialog.setCancelable(false);
            dialog.setTitle("خطای شبکه");
            dialog.setMessage("خطایی در برقراری ارتباط با سرور رخ داد. لطفا کنترل نمایید که تلفن همراه شما به اینترنت متصل باشد و دوباره تلاش نمایید");
            dialog.setPositiveButton("تلاش مجدد", new EPAlertDialog.OnPositiveButtonClickListener() {
                @Override
                public void onClick() {
//                    System.exit(0);
                    isErrorShowing=false;

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    context.startActivity( intent );
                }
            });
            dialog.setNegativeButton("خروج از برنامه", new EPAlertDialog.OnNegativeButtonClickListener() {
                @Override
                public void onClick() {
                    isErrorShowing=false;
                    System.exit(0);
                }
            });
            isErrorShowing = true ;
            dialog.show();
        }
    }
}
