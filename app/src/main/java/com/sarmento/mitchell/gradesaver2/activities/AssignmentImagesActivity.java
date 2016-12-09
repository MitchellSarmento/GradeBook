package com.sarmento.mitchell.gradesaver2.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ImageAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.PhotoView;

public class AssignmentImagesActivity extends AppCompatActivity {
    public static final int IMAGE_CAPTURE = 0;

    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;
    private ImageAdapter adapter;
    private Assignment assignment;
    private List<String> imagePaths;
    private PhotoView imageMain;
    private RecyclerView imageScroll;

    private int imageMainPosition = 0;
    private String currentPicturePath;

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

        imagePaths  = assignment.getImagePaths();
        imageMain   = (PhotoView) findViewById(R.id.image_main);
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
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.sarmento.mitchell.gradesaver2", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getFilesDir();
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPicturePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            assignment.addImagePath(this, currentPicturePath, termPosition, sectionPosition,
                    assignmentPosition);
            setViews();
        }
    }

    private void setViews() {
        int numImages = imagePaths.size();

        if (numImages > 0) {
            if (adapter == null) {
                setImageMain(0);

                imageScroll.setLayoutManager(new LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new ImageAdapter(imagePaths, termPosition, sectionPosition,
                        assignmentPosition);
                imageScroll.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            imageMain.setZoomable(false);
        }
    }

    public void deleteView(int deletedPosition) {
        int numImages = imagePaths.size();

        if (numImages > 0) {
            adapter.notifyDataSetChanged();
            if (deletedPosition == imageMainPosition) {
                setImageMain(0);
            }
        } else {
            adapter.notifyDataSetChanged();
            imageMain.setImageResource(R.drawable.ic_photo_gray_24dp);
            imageMain.setZoomable(false);
        }
    }

    public void setImageMain(int imagePosition) {
        imageMain.setImageBitmap(BitmapFactory.decodeFile(imagePaths.get(imagePosition)));
        imageMainPosition = imagePosition;
        if (!imageMain.canZoom()) {
            imageMain.setZoomable(true);
        }
    }
}
