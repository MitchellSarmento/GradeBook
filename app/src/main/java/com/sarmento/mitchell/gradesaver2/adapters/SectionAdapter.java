package com.sarmento.mitchell.gradesaver2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.buttons.SectionButton;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private List<Section> sections;

    public SectionAdapter(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_sections, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SectionAdapter.ViewHolder holder, int position) {
        SectionButton button = holder.button;
        button.init(sections.get(position), position);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SectionButton button;

        public ViewHolder(View v) {
            super(v);
            button = (SectionButton) v.findViewById(R.id.button_section);
        }
    }
}