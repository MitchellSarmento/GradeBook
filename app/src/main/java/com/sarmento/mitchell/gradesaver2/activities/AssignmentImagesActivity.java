package com.sarmento.mitchell.gradesaver2.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarmento.mitchell.gradesaver2.R;
import com.sarmento.mitchell.gradesaver2.adapters.ImageAdapter;
import com.sarmento.mitchell.gradesaver2.model.Academics;
import com.sarmento.mitchell.gradesaver2.model.Assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.PhotoView;

public class AssignmentImagesActivity extends AppCompatActivity {
    public static final int IMAGE_CAPTURE               = 0;
    private static final int NO_IMAGE                   = -1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.sarmento.mitchell.gradesaver2";

    private Academics academics = Academics.getInstance();
    private int termPosition;
    private int sectionPosition;
    private int assignmentPosition;

    private ImageAdapter adapter;
    private Assignment assignment;
    private List<String> imagePaths;
    private PhotoView imageMain;
    private RecyclerView imageScroll;
    private String currentPicturePath;
    private int imageMainPosition = NO_IMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_images);

        termPosition       = getIntent().getIntExtra(Academics.TERM_POSITION, -1);
        sectionPosition    = getIntent().getIntExtra(Academics.SECTION_POSITION, -1);
        assignmentPosition = getIntent().getIntExtra(Academics.ASSIGNMENT_POSITION, -1);

        assignment = (academics.inArchive()) ?
                academics.getArchivedTerms().get(termPosition).getSections().get(sectionPosition)
                    .getAssignments().get(assignmentPosition) :
                academics.getCurrentTerms().get(termPosition).getSections().get(sectionPosition)
                    .getAssignments().get(assignmentPosition);
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
                Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY,
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
                return true;
            case R.id.action_rotate_left:
                if (imageMainPosition != NO_IMAGE) {
                    imageMain.setRotationBy(-90);
                }
                return true;
            case R.id.action_rotate_right:
                if (imageMainPosition != NO_IMAGE) {
                    imageMain.setRotationBy(90);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            correctRotation();
            assignment.addImagePath(this, currentPicturePath, termPosition,
                    sectionPosition, assignmentPosition);
            setViews();
        }
    }

    /*
     * Correct the rotation of the image based on device orientation at the time of capture.
     */
    private void correctRotation() {
        try {
            ExifInterface exif   = new ExifInterface(currentPicturePath);
            Bitmap originalImage = BitmapFactory.decodeFile(currentPicturePath);
            int orientation      = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            int correctionValue = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    correctionValue = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    correctionValue = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    correctionValue = 270;
                    break;
                default:
                    break;
            }

            if (correctionValue != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(correctionValue);

                // create the corrected image
                Bitmap newImage = Bitmap.createBitmap(originalImage, 0, 0, originalImage.getWidth(),
                        originalImage.getHeight(), matrix, true);

                // delete the existing image to replace it with the corrected version
                File file = new File(currentPicturePath);
                if (file.exists()) {
                    if (file.delete()) {
                        FileOutputStream out = new FileOutputStream(file);
                        newImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Create an image file for use with Camera Intent.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getFilesDir();
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPicturePath = image.getAbsolutePath();
        return image;
    }

    /*
     * Set the main image and assign thumbnails to RecyclerView adapter.
     */
    private void setViews() {
        int numImages = imagePaths.size();

        // set the views only if there are images to use
        if (numImages > 0) {
            // perform initial adapter attachment and set the main image
            if (adapter == null) {
                setImageMain(0);

                imageScroll.setLayoutManager(new LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new ImageAdapter(imagePaths, termPosition, sectionPosition,
                        assignmentPosition);
                imageScroll.setAdapter(adapter);
            // notify the adapter of changes and set the main image if it's empty
            } else {
                adapter.notifyDataSetChanged();
                if (imageMainPosition == NO_IMAGE) {
                    setImageMain(0);
                }
            }
        // disable zoom functionality while the main image is empty
        } else {
            imageMain.setZoomable(false);
        }
    }

    /*
     * Delete the selected image.
     */
    public void deleteView(int deletedPosition) {
        int numImages = imagePaths.size();

        // reset the main image if the image deleted was being viewed and more images exist
        if (numImages > 0) {
            adapter.notifyDataSetChanged();
            if (deletedPosition == imageMainPosition) {
                setImageMain(0);
            }
        // set the main image placeholder and disable zoom functionality if there are no more images
        } else {
            adapter.notifyDataSetChanged();
            imageMainPosition = NO_IMAGE;
            imageMain.setImageResource(R.drawable.ic_photo_gray_24dp);
            imageMain.setZoomable(false);
        }
    }

    /*
     * Set the main image bitmap.
     */
    public void setImageMain(int imagePosition) {
        currentPicturePath = imagePaths.get(imagePosition);
        imageMain.setImageBitmap(BitmapFactory.decodeFile(currentPicturePath));
        imageMainPosition = imagePosition;

        if (!imageMain.canZoom()) {
            imageMain.setZoomable(true);
        }
    }
}
