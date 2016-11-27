package com.sarmento.mitchell.gradesaver2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.buttons.DueDateButton;
import com.sarmento.mitchell.gradesaver2.model.DueDate;

import java.util.List;

public class DueDateAdapter extends RecyclerView.Adapter<DueDateAdapter.ViewHolder> {
    private List<DueDate> dueDates;
    private int termPosition;
    private int sectionPosition;

    public DueDateAdapter(List<DueDate> dueDates, int termPosition, int sectionPosition) {
        this.dueDates        = dueDates;
        this.termPosition    = termPosition;
        this.sectionPosition = sectionPosition;
    }

    @Override
    public DueDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_due_dates, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DueDateAdapter.ViewHolder holder, int position) {
        DueDateButton button = holder.button;
        button.init(dueDates.get(position), termPosition, sectionPosition, position);
    }

    @Override
    public int getItemCount() {
        return dueDates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private DueDateButton button;

        public ViewHolder(View v) {
            super(v);
            button = (DueDateButton) v.findViewById(R.id.button_due_date);
        }
    }
}
