package com.sarmento.mitchell.gradesaver2.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class DueDatesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DueDatesWidgetViewsFactory(getApplicationContext(), intent);
    }
}
