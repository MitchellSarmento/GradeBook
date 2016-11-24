package com.sarmento.mitchell.gradesaver2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.buttons.AssignmentButton;
import com.sarmento.mitchell.gradesaver2.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> assignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_assignments, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(AssignmentAdapter.ViewHolder holder, int position) {
        AssignmentButton button = holder.button;
        button.init(assignments.get(position), position);
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AssignmentButton button;

        public ViewHolder(View v) {
            super(v);
            button = (AssignmentButton) v.findViewById(R.id.button_assignment);
        }
    }
}
