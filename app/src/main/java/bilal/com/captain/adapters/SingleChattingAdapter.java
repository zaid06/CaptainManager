package bilal.com.captain.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import bilal.com.captain.ChatGlobal;
import bilal.com.captain.R;
import bilal.com.captain.chatActivity.StartOneToOneChatting;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.galleryActivity.GalleryActivity;
import bilal.com.captain.models.SingleChatModel;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class SingleChattingAdapter extends ArrayAdapter<SingleChatModel> {

    ArrayList<SingleChatModel> arrayList;

    BoldCustomTextView chatTitle;

    Context context;

    LayoutInflater inflater;

    public SingleChattingAdapter(@NonNull Context context, ArrayList<SingleChatModel> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;

        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        SingleChatModel singleChatModel = getItem(position);


        if(singleChatModel.isFlag()){

            convertView = inflater.inflate(R.layout.send_message_layout,parent,false);

            final ViewHolder viewHolder = new ViewHolder(convertView);

            viewHolder.name_bBoldCustomTextView.setText(singleChatModel.getUser());

            viewHolder.message_reRegularCustomTextView.setText(singleChatModel.getMsgs());

            if(singleChatModel.getSelectDownloadUrls() != null && singleChatModel.getSelectDownloadUrls().size()>0){

                viewHolder.linearLayout.setVisibility(View.VISIBLE);

                ImageShowAdapter imageShowAdapter = new ImageShowAdapter(context,singleChatModel.getSelectDownloadUrls());

                viewHolder.gridView.setAdapter(imageShowAdapter);

                viewHolder.gridView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });



            }else {

                viewHolder.linearLayout.setVisibility(View.GONE);

            }



        }else {

            convertView = inflater.inflate(R.layout.recieve_msg_layout,parent,false);

            final ViewHolder viewHolder = new ViewHolder(convertView);

            viewHolder.name_bBoldCustomTextView.setText(singleChatModel.getUser());

            viewHolder.message_reRegularCustomTextView.setText(singleChatModel.getMsgs());

            if(singleChatModel.getSelectDownloadUrls() != null && singleChatModel.getSelectDownloadUrls().size()>0){

                viewHolder.linearLayout.setVisibility(View.VISIBLE);

                ImageShowAdapter imageShowAdapter = new ImageShowAdapter(context,singleChatModel.getSelectDownloadUrls());

                viewHolder.gridView.setAdapter(imageShowAdapter);

                viewHolder.gridView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });


                viewHolder.gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                       String url = (String
                               ) adapterView.getItemAtPosition(i);
                        openAlert(position,url);
                        return false;

                    }
                });


            }else {

                viewHolder.linearLayout.setVisibility(View.GONE);

            }

        }




        return convertView;
    }

    private void openAlert(final int position, final  String url) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.imagedownload,null,false);

        final LinearLayout yes = (LinearLayout) view.findViewById(R.id.downloadyes);
        final LinearLayout no = (LinearLayout) view.findViewById(R.id.downloadno);

        alert.setView(view);
        final AlertDialog dialog = alert.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                downloadImages(url);
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);


               Uri imageuri = url;

                DownloadManager.Request request = new DownloadManager.Request(imageuri);

                request.setTitle("Download Images");

                request.setDescription("Downloading..");

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                request.setDestinationUri(Uri.parse(Environment.getExternalStorageDirectory() + "/Captain/Download Images"));

                downloadManager.enqueue(request);
            }
        });
    }

    private void downloadImages(String imageUrl){

        try {
//            java.net.URL url = new URL(imageUrl);

            // TODO Bakhtiyar: I create this because some images are correct in server but Java think its corrupt

            boolean success = false;

            int pos = imageUrl.lastIndexOf('/') + 1;

            URI uri = null;

            try {

                uri = new URI(imageUrl.substring(0, pos) + Uri.encode(imageUrl.substring(pos)));

                Log.d("uri", "onClick: " + uri);

            } catch (Exception e) {
                Log.d("exc", "onClick: " + e + uri);
            }


            java.net.URL url = new URL(String.valueOf(uri));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            String name = imageUrl.substring(String.valueOf(imageUrl).lastIndexOf("/") + 1);

            File file = null;

            File myDir = new File(Environment.getExternalStorageDirectory() + "/Captain/Download Images" );
            if (myDir.exists()) {
                success = true;
            } else {
                success = myDir.mkdirs();
            }

            if (success) {

                file = new File(myDir, name);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    static class ViewHolder{

        BoldCustomTextView name_bBoldCustomTextView;

        RegularCustomTextView message_reRegularCustomTextView;

        GridView gridView;

        LinearLayout linearLayout;

        public ViewHolder(View v){

            this.linearLayout = (LinearLayout) v.findViewById(R.id.parent_grid);

            this.gridView = (GridView) v.findViewById(R.id.gridview);

            this.name_bBoldCustomTextView = (BoldCustomTextView) v.findViewById(R.id.name);

            this.message_reRegularCustomTextView = (RegularCustomTextView) v.findViewById(R.id.message);
        }

    }

    class ImageShowAdapter extends ArrayAdapter<String>{

        Context context;

        LayoutInflater inflater;

        DisplayMetrics metrics;

        LinearLayout.LayoutParams layoutParams;


        public ImageShowAdapter(@NonNull Context context, ArrayList<String> arrayList) {
            super(context, 0,arrayList);
            this.context = context;

            inflater = LayoutInflater.from(context);

            metrics = context.getResources().getDisplayMetrics();
            float dp = 100f;
            float fpixels = metrics.density * dp;
            int pixels = (int) (fpixels + 0.5f);

            layoutParams = new LinearLayout.LayoutParams(pixels, pixels);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            String url = getItem(position);

            Log.d("URL", url);

            ImageView imageView = new ImageView(context);

            imageView.setLayoutParams(layoutParams);

            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.loader)
                    .into(imageView);

//            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bike));
//            try {
//
//
//                Glide.with(context)
//                        .load(arrayList.get(position))
//                        .into(imageView);
//            }catch (Exception e){
//
//                Log.d("Error", "getView: "+e);
//
//            }
            return imageView;
        }
    }

}
