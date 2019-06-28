package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryExtendActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView mImageView = (ImageView) findViewById(R.id.galleryImageView);
        TextView mTextView = (TextView) findViewById(R.id.galleryTextView);

        int imageID = getIntent().getIntExtra("imageID", 0);
        String imageText = getIntent().getStringExtra("imageText");

        mTextView.setText(imageText);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        mImageView.setImageResource(imageID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the back button on the main android toolbar.
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
