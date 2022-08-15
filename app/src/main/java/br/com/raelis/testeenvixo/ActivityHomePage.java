package br.com.raelis.testeenvixo;

import android.content.Intent;
import android.net.Uri;
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
        // Recupera dados do singleton
        ArrayList<CoursifyCategory> categories = AppData.categories;

        // Infla o layout para colocar os Posts e Categorias
        LinearLayoutCompat nested = findViewById(R.id.linearLayoutCompat);

        // Arruma o destino do botão no footer
        Button footerButton = findViewById(R.id.button);
        footerButton.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://coursify.me");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // Add dados na página
        for(CoursifyCategory category : categories) {
            // Infla uma categoria
            View inflater = getLayoutInflater().inflate(R.layout.home_page_cat, null, true);

            // Add texto da categoria
            AppCompatTextView textView = inflater.findViewById(R.id.appCompatTextView2);
            textView.setText(category.getName());

            LinearLayoutCompat postsContainer = inflater.findViewById(R.id.linearLayoutCompat2);
            for(CoursifyPost post : category.getPosts()) {
                // Infla um post
                View inflater2 = getLayoutInflater().inflate(R.layout.home_page_cat_post, null, true);

                // Add imagem no post
                ImageView imageView = inflater2.findViewById(R.id.imageView2);
                imageView.setImageDrawable(post.getMedia().getImage());

                // Add título do post
                AppCompatTextView textViewTitle = inflater2.findViewById(R.id.textView4);
                textViewTitle.setText(post.getTitleRendered());

                // Add texto do post
                AppCompatTextView textViewDescription = inflater2.findViewById(R.id.textView5);
                textViewDescription.setText(Html.fromHtml(post.getContentRendered()));

                // Add a ação do botão leiaMais
                Button leiaMais = inflater2.findViewById(R.id.button2);
                leiaMais.setOnClickListener(view -> {
                    Intent i = new Intent(ActivityHomePage.this,
                            ActivityPostPage.class);
                    AppData.post = post;
                    startActivity(i);
                });

                // Add o post na categoria
                postsContainer.addView(inflater2);
            }
            // Add a categoria com seus posts no layout
            nested.addView(inflater);
        }

    }
}