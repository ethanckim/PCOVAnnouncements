package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class AnnouncementsTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView txtContext = findViewById(R.id.txtContext);

        String context = getIntent().getStringExtra("newsCardContext");
        context = context.replace("\\r", "\n");
        context = context.replace("\\t", "    ");
        String date = getIntent().getStringExtra("newsCardDate");

        getSupportActionBar().setTitle(date);
        txtContext.setText(context);
        txtContext.setMovementMethod(new ScrollingMovementMethod());
    }
}
