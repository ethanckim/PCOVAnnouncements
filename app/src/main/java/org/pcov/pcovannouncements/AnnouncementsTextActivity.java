package org.pcov.pcovannouncements;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class AnnouncementsTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_text);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView txtContext = findViewById(R.id.txtContext);

        String context = getIntent().getStringExtra("newsCardContext");
        context = context.replace("\\r", "\n");
        context = context.replace("\\t", "    ");
        String date = getIntent().getStringExtra("newsCardDate");

        getSupportActionBar().setTitle(date);
        txtContext.setText(context);
        txtContext.setMovementMethod(new ScrollingMovementMethod());
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
