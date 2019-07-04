package org.pcov.pcovannouncements;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class GalleryExtendActivity extends AppCompatActivity {

    private static final String IMAGE_SHARE_HASHTAG = " #PCOVApp";

    ImageView mImageView;
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView) findViewById(R.id.galleryImageView);
        mTextView = (TextView) findViewById(R.id.galleryTextView);

        int imageID = getIntent().getIntExtra("imageID", 0);
        String imageText = getIntent().getStringExtra("imageText");

        mTextView.setText(imageText);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        mImageView.setImageResource(imageID);
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
                startActivity(shareIntent);
                return true;
            case R.id.action_download:
                Intent downloadIntent = downloadImage();
                sendBroadcast(downloadIntent);
                Toast.makeText(getBaseContext(), "Button Clicked",
                        Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getBaseContext(), "Error. Please Try Again",
                    Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getExternalCacheDir() + "/image.jpg")));
        return Intent.createChooser(intent, "Share Image");
    }


    private Intent downloadImage() {
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

}
