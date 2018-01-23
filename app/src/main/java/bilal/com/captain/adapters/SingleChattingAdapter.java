package bilal.com.captain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.galleryActivity.GalleryActivity;
import bilal.com.captain.models.SingleChatModel;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class SingleChattingAdapter extends ArrayAdapter<SingleChatModel> {

    ArrayList<SingleChatModel> arrayList;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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


            }else {

                viewHolder.linearLayout.setVisibility(View.GONE);

            }

        }



        return convertView;
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
