package com.ir.irdevelopers.Tamashachi;

/**
 * Created by Alip on 6/27/2016.
 */
import android.app.Application;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.sender.HttpSender;

//@ReportsCrashes(formUri = "http://www.epasazh.com/LiveScore/reporter.php",
//        httpMethod = HttpSender.Method.POST,
//reportType = HttpSender.Type.JSON)


@ReportsCrashes(
        formUri = "http://www.epasazh.com/LiveScore/reporter.php",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.STACK_TRACE,
                ReportField.BRAND,
                ReportField.DEVICE_ID,
                ReportField.FILE_PATH,
                ReportField.PHONE_MODEL,
                ReportField.APPLICATION_LOG,
                ReportField.BUILD_CONFIG,
                ReportField.LOGCAT,
                ReportField.USER_EMAIL,
                ReportField.SHARED_PREFERENCES},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash
)

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // The following line triggers the initialization of ACRA
        super.onCreate();
        ACRA.init(this);
    }


}
