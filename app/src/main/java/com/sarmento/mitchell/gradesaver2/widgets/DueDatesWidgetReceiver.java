package com.sarmento.mitchell.gradesaver2.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DueDatesWidgetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DueDatesWidgetProvider.updateWidget(context, true);
    }
}
