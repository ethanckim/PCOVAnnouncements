package org.pcov.pcovannouncements;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Random;

public class GalleryExtendActivity extends AppCompatActivity {

    private static final String IMAGE_SHARE_HASHTAG = " #PCOVApp";

    private PhotoView mImageView;
    private TextView mTextView;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent mIntent = getIntent();
        int imageID;

        mImageView = findViewById(R.id.galleryImageView);
        mTextView = findViewById(R.id.galleryImageTag);

        final String imageUrl = getIntent().getStringExtra("imageUrl");
        final String imageTag = getIntent().getStringExtra("imageTag");

        Picasso.with(this.getApplicationContext())
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        //Use internet.
                        Picasso.with(GalleryExtendActivity.this.getApplicationContext())
                                .load(imageUrl)
                                .into(mImageView);
                    }
                });

        mTextView.setText(imageTag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.gallery_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the back button on the main android toolbar.
                super.onBackPressed();
                return true;
            case R.id.action_share:
                Intent shareIntent = createShareImageIntent();

                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                    return true;
                }

                Toast.makeText(getBaseContext(), this.getString(R.string.sharing_error_toast), Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            case R.id.action_download:
                @Nullable
                Intent downloadIntent = downloadImage();
                if (downloadIntent != null) {
                    sendBroadcast(downloadIntent);
                    Toast.makeText(getBaseContext(), this.getString(R.string.image_downloaded_toast), Toast.LENGTH_LONG).show();

                    //Notification
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PCOV");
                    Uri imageUri = Uri.parse(dir.toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, imageUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setType("image/*");
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, FirebaseNotifications.NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getText(R.string.galleryNotificationTitle))
                            .setContentText(getText(R.string.galleryNotificationContext))
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(new Random().nextInt(), builder.build());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Intent createShareImageIntent() {
        Drawable mDrawable = mImageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            File file = new java.io.File(getExternalCacheDir() + "/image.jpg");
            OutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), this.getString(R.string.error_toast),
                    Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getExternalCacheDir() + "/image.jpg")));
        return Intent.createChooser(intent, "Share Image");
    }


    private Intent downloadImage() {
        //Check if storage permission is on, and request permissions
        final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.storage_permission_dialog_message)
                    .setTitle(R.string.storage_permission_dialog_title);
            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(GalleryExtendActivity.this, PERMISSIONS, 2);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        BitmapDrawable draw = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/PCOV");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outFile));

        return intent;
    }

    public Uri getImageUri(Context context, Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmapImage, "Title", null);
        return Uri.parse(path);
    }

}
