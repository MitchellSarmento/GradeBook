package com.sarmento.mitchell.gradesaver2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.buttons.TermButton;
import com.sarmento.mitchell.gradesaver2.model.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {
    private List<Term> terms;

    public TermAdapter(List<Term> terms) {
        this.terms = terms;
    }

    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_terms, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TermAdapter.ViewHolder holder, int position) {
        TermButton button = holder.button;
        button.init(terms.get(position), position);
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TermButton button;

        public ViewHolder(View v) {
            super(v);
            button = (TermButton) v.findViewById(R.id.button_term);
        }
    }
}