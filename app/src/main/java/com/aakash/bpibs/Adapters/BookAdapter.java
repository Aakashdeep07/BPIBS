package com.aakash.bpibs.Adapters;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.ModelClass.BookHandler;
import com.aakash.bpibs.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BookAdapter extends FirebaseRecyclerAdapter<BookHandler, BookAdapter.BookViewHolder> {
    Context context;

    public BookAdapter(FirebaseRecyclerOptions<BookHandler> options, Context applicationContext) {
        super(options);
        this.context = applicationContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull final BookHandler model) {
        holder.bookTitle.setText(model.bookTitle);
        holder.bookDescription.setText(model.bookDescription);

        holder.bookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = model.getBookTitle() + model.getBookDescription() + ".pdf";
                String url = model.getBookLink();
                downloadBook(filename, url);

            }
        });
    }

    private void downloadBook(String filename, String url) {
        Uri downloadURI = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            if (downloadManager != null) {
                DownloadManager.Request request = new DownloadManager.Request(downloadURI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                            .setTitle(filename)
                            .setDescription("Downloading " + filename)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                }
                downloadManager.enqueue(request);
                Toast.makeText(context, "Download Started", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, downloadURI);
                context.getApplicationContext().startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "Something Went Wrong\nPlease Allow Storage Permissions to save File.", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
        }

    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.placeholder_book_layout, parent, false);
        return new BookViewHolder(view);

    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookDescription;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookDescription = itemView.findViewById(R.id.book_description);

        }
    }
}
