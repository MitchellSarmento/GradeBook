package com.sarmento.mitchell.gradesaver2.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.views.ScrollImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Bitmap> images;
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    public ImageAdapter(List<Bitmap> images, int termPosition, int sectionPosition,
                        int assignmentPosition) {
        this.images             = images;
        this.termPosition       = termPosition;
        this.sectionPosition    = sectionPosition;
        this.assignmentPosition = assignmentPosition;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_assignment_images, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int imagePosition) {
        ScrollImageView view = holder.imageView;
        view.init(termPosition, sectionPosition, assignmentPosition, imagePosition);
        view.setImageBitmap(images.get(imagePosition));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ScrollImageView imageView;

        private ViewHolder(View v) {
            super(v);
            imageView = (ScrollImageView) v.findViewById(R.id.view_image);
        }
    }
}