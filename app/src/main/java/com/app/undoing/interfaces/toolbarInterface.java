package com.app.undoing.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.app.undoing.BillActivity;
import com.app.undoing.EducationActivity;
import com.app.undoing.MainActivity;
import com.app.undoing.R;

public interface toolbarInterface {
    default void setupBackListener(Activity activity) {
        activity.findViewById(R.id.toolbar_back).setOnClickListener(view -> {
            activity.finish();
        });
    }
    default void setEducationListener(Activity activity) {
        activity.findViewById(R.id.toolbar_education).setOnClickListener(view -> {
            Intent i = new Intent(activity, EducationActivity.class);
            activity.startActivity(i);
        });
    }

    default void setTitleListener(Activity activity) {
        activity.findViewById(R.id.toolbar_title).setOnClickListener(view -> {
            Intent i = new Intent(activity, MainActivity.class);
            activity.startActivity(i);
        });
    }
}
