package bilal.com.captain.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.models.ComplainModel;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class NewsFeedAdapter extends ArrayAdapter<ComplainModel> {

    ArrayList<ComplainModel> arrayList;

    Context context;

    LayoutInflater inflater;

    final MediaPlayer mediaPlayer = new MediaPlayer();

    public NewsFeedAdapter(@NonNull Context context, ArrayList<ComplainModel> arrayList) {
        super(context, 0, arrayList);

        this.arrayList = arrayList;

        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = inflater.inflate(R.layout.news_feed_item,parent,false);

        }


        final ComplainModel complainModel = getItem(position);

        final ViewHolder viewHolder = new ViewHolder(convertView);

        try {

//            viewHolder.boldCustomTextView_name.setText("");

            Glide.with(context)
                    .load(complainModel.getImageUrl())
                    .into(viewHolder.imageView_image);

            viewHolder.play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mediaPlayer.isPlaying()){

                        viewHolder.play_pause.setImageResource(R.drawable.play);

                        mediaPlayer.pause();

                        mediaPlayer.release();

                    }else {

                        viewHolder.play_pause.setImageResource(R.drawable.pause);

                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(complainModel.getRecordingUrl());
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                            mediaPlayer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }catch (Exception e){

            Log.d("Exception", "getView: "+e);

        }

        return convertView;
    }

    static class ViewHolder{

        ImageView imageView_image;

        ImageView play_pause;

        BoldCustomTextView boldCustomTextView_name;

        SeekBar seekBar;

        public ViewHolder(View v) {
            this.imageView_image = (ImageView) v.findViewById(R.id.image);
            this.play_pause = (ImageView) v.findViewById(R.id.play_pause);
            this.boldCustomTextView_name = (BoldCustomTextView) v.findViewById(R.id.name);
            this.seekBar = (SeekBar) v.findViewById(R.id.seek_bar);
        }
    }
}
