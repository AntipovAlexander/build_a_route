package com.antipov.buildaroute.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by Antipov on 20.08.2017.
 */

public class DialogUtils {

    public static void showSnackbar(AppCompatActivity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    /**
     * Method to show single choice dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createBasicDialog(Context context,
                                                           int title,
                                                           String content,
                                                           boolean cancelable,
                                                           MaterialDialog.SingleButtonCallback positiveOnClickListener,
                                                           String positiveText,
                                                           String negativeText) {
        return new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .title(title)
                .content(content)
                .cancelable(cancelable)
                .onPositive(positiveOnClickListener)
                .positiveText(positiveText)
                .negativeText(negativeText);
    }
}

