package br.com.raelis.testeenvixo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.ArrayList;

public class ActivityHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ArrayList<CoursifyCategory> categories = AppData.categories;

        LinearLayoutCompat nested = findViewById(R.id.linearLayoutCompat);

        for(CoursifyCategory category : categories) {
            View inflater = getLayoutInflater().inflate(R.layout.home_page_cat, null, true);
            AppCompatTextView textView = inflater.findViewById(R.id.appCompatTextView2);
            textView.setText(category.getName());

            LinearLayoutCompat postsContainer = inflater.findViewById(R.id.linearLayoutCompat2);
            for(CoursifyPost post : category.getPosts()) {
                View inflater2 = getLayoutInflater().inflate(R.layout.home_page_cat_post, null, true);

                ImageView imageView = inflater2.findViewById(R.id.imageView2);
                imageView.setImageDrawable(post.getMedia().getImage());

                AppCompatTextView textViewTitle = inflater2.findViewById(R.id.textView4);
                textViewTitle.setText(post.getTitleRendered());

                AppCompatTextView textViewDescription = inflater2.findViewById(R.id.textView5);
                textViewDescription.setText(Html.fromHtml(post.getContentRendered()));

                Button leiaMais = inflater2.findViewById(R.id.button2);
                leiaMais.setOnClickListener(view -> {
                    Intent i = new Intent(ActivityHomePage.this,
                            ActivityPostPage.class);
                    AppData.post = post;
                    startActivity(i);
                });

                postsContainer.addView(inflater2);
            }
            nested.addView(inflater);
        }

    }
}