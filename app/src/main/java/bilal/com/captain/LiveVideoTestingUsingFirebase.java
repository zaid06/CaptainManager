package bilal.com.captain;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import bilal.com.captain.complainActivity.ComplainActivity;

public class LiveVideoTestingUsingFirebase extends AppCompatActivity implements Camera.PreviewCallback, SurfaceHolder.Callback, View.OnClickListener {

    SurfaceView surfaceView;

    private Camera mCamera;

    private boolean mIsCapturing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_video_testing_using_firebase);

        surfaceView = (SurfaceView) findViewById(R.id.myVideo);
        final SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setKeepScreenOn(true);
        mIsCapturing = true;

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

        Log.d("size", "onPreviewFrame: "+data.length);

        String str = new String(data, StandardCharsets.UTF_8);

        FirebaseDatabase.getInstance().getReference().child("Temp").child("call").setValue(str);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }

        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                mCamera.setPreviewDisplay(surfaceView.getHolder());
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
            } catch (Exception e) {

                Log.d("Error", ""+e);

            }
        }
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (IOException e) {
                Toast.makeText(LiveVideoTestingUsingFirebase.this, "Unable to start camera preview.", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mCamera != null){

            mCamera.stopPreview();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mCamera != null){
            mCamera.release();

            mCamera = null;
        }
    }
}
