package com.sarmento.mitchell.gradesaver2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.buttons.SectionButton;
import com.sarmento.mitchell.gradesaver2.model.Section;

import java.util.List;

public class SectionAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    private final List<Section> sections;

    public SectionAdapter(Context context, List<Section> sections) {
        super(context, R.layout.list_sections, sections);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sections = sections;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_sections, parent, false);
            holder = new ViewHolder();
            holder.button = (SectionButton) convertView.findViewById(R.id.button_section);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.button.init(sections.get(position), position);

        return convertView;
    }

    private static class ViewHolder {
        public SectionButton button;
    }
}
