package com.sarmento.mitchell.gradesaver2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.model.Term;

import java.util.List;

public class TermAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    private final List<Term> terms;

    public TermAdapter(Context context, List<Term> terms) {
        super(context, R.layout.list_terms, terms);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.terms = terms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_terms, parent, false);
            holder = new ViewHolder();
            holder.button = (Button) convertView.findViewById(R.id.button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.button.setText(terms.get(position).getTermName());

        return convertView;
    }

    private static class ViewHolder {
        public Button button;
    }
}
