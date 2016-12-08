package com.sarmento.mitchell.gradesaver2.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ImageAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;

import java.util.List;

public class AssignmentImagesActivity extends AppCompatActivity {
    public static final int IMAGE_CAPTURE = 0;

    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private ImageAdapter adapter;
    private Assignment assignment;
    private List<Bitmap> images;
    private ImageView imageMain;
    private RecyclerView imageScroll;

    private int imageMainPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_images);

        termPosition       = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition    = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);
        assignmentPosition = getIntent().getIntExtra(Academics.ASSIGNMENT_POSITION, -1);


        if (academics.inArchive()) {
            assignment = academics.getArchivedTerms().get(termPosition)
                    .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
        } else {
            assignment = academics.getCurrentTerms().get(termPosition)
                    .getSections().get(sectionPosition).getAssignments().get(assignmentPosition);
        }
        setTitle(assignment.getAssignmentName());

        images      = assignment.getImages();
        imageMain   = (ImageView) findViewById(R.id.image_main);
        imageScroll = (RecyclerView) findViewById(R.id.image_scroll);
        setViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!academics.inArchive()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_assignment_images, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_picture:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_CAPTURE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            assignment.addImage(imageBitmap);
            setViews();
        }
    }

    private void setViews() {
        int numImages = images.size();

        if (numImages > 0) {
            if (adapter == null) {
                setImageMain(0);
                imageScroll.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false));
                adapter = new ImageAdapter(images, termPosition, sectionPosition,
                        assignmentPosition);
                imageScroll.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void deleteView(int deletedPosition) {
        int numImages = images.size();

        if (numImages > 0) {
            adapter.notifyDataSetChanged();
            if (deletedPosition == imageMainPosition) {
                setImageMain(0);
            }
        } else {
            adapter.notifyDataSetChanged();
            imageMain.setImageResource(R.drawable.ic_photo_gray_24dp);
        }
    }

    public void setImageMain(int imagePosition) {
        imageMain.setImageBitmap(images.get(imagePosition));
        imageMainPosition = imagePosition;
    }
}
