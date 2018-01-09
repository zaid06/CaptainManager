package bilal.com.captain.complainActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bilal.com.captain.R;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.ComplainModel;

public class ComplainActivity extends Activity implements Camera.PictureCallback, SurfaceHolder.Callback, View.OnClickListener {

    public static final String EXTRA_CAMERA_DATA = "camera_data";

    private static final String KEY_IS_CAPTURING = "is_capturing";
    private static final String TAG = "Tag";

    LinearLayout doneButton, recording;

    private Camera mCamera;
    private ImageView mCameraImage;
    private SurfaceView mCameraPreview;
    private LinearLayout mCaptureImageButton;
    private ImageView flash;
    private byte[] mCameraData;
    private boolean mIsCapturing;

    int rotationField = -1;

    RegularCustomTextView sizeView;

    BoldCustomTextView title;

//    ImageView reset;

    public static Uri uri;

    public static String stringUri;

//    ImageView doneButton;

    ProgressDialog dialogSave;

    private MediaRecorder mRecorder;

    Camera.Parameters parameters;

    float mDist = 0;

    int secondsCounter = 0;

    ImageView record_image;

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Captain/takePictures/");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private File getRecordtMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Captain/recording/");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "Re" + timeStamp + ".3gp";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    private View.OnClickListener mCaptureImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            captureImage();
        }
    };

    boolean isRecord = true;

    File recordFile = null;

    private View.OnClickListener mRecaptureImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            mCaptureImageButton.setVisibility(View.VISIBLE);

//            setupImageCapture();


            if (isRecord) {
                recordFile = getRecordtMediaFile();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(recordFile.getPath().toString());
                mRecorder.setMaxDuration(180000);

                try {
                    mRecorder.prepare();
                } catch (Exception e) {
                    Log.e("Error", "prepare() failed" + e);
                }
                mRecorder.start();

                mRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                    @Override
                    public void onInfo(MediaRecorder mr, int what, int extra) {

                        Log.d("InfoListners", "onInfo: " + what);
                        mr.stop();
                        mr.release();
                    }
                });

                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                isRecord = false;
                record_image.setImageResource(R.drawable.record_stop);
            } else {
                mRecorder.stop();
                mRecorder.release();
                isRecord = true;
                recording.setVisibility(View.INVISIBLE);
                doneButton.setVisibility(View.VISIBLE);
                record_image.setImageResource(R.drawable.record_button);
                Toast.makeText(getApplicationContext(), "Recording Stoped", Toast.LENGTH_LONG).show();
            }

        }
    };

    private View.OnClickListener mDoneButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complain);

        dialogSave = new ProgressDialog(this);

//        check = (TextView) findViewById(R.id.check);

        dialogSave.setMessage("Sending Complain...");

//        check.setVisibility(View.GONE);

        dialogSave.setCanceledOnTouchOutside(false);


        title = (BoldCustomTextView) findViewById(R.id.title);

        title.setText("Complain");

        sizeView = (RegularCustomTextView) findViewById(R.id.sizeView);

        flash = (ImageView) findViewById(R.id.flash);

        mRecorder = new MediaRecorder();

        record_image = (ImageView) findViewById(R.id.record_image);

        OrientationEventListener orientationListener = new OrientationEventListener(ComplainActivity.this, SensorManager.SENSOR_DELAY_NORMAL) {
            public void onOrientationChanged(int orientation) {

                Log.d("orientation", "" + orientation);

//                if(canShow(orientation)){
//                    show();
//                } else if(canDismiss(orientation)){
//                    dismiss();
//                }
            }
        };

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (parameters.getFlashMode() == Camera.Parameters.FLASH_MODE_ON) {

                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                    flash.setImageDrawable(getResources().getDrawable(R.drawable.flash_on));

                    mCamera.setParameters(parameters);

                    mCamera.startPreview();

                } else {

                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);

                    flash.setImageDrawable(getResources().getDrawable(R.drawable.flash_off));

                    mCamera.setParameters(parameters);

                    mCamera.startPreview();
                }
//                Toast.makeText(CameraActivity.this, "On Mode", Toast.LENGTH_SHORT).show();


            }
        });


        mCameraImage = (ImageView) findViewById(R.id.camera_image_view);
        mCameraImage.setVisibility(View.GONE);
        mCameraImage.setScaleType(ImageView.ScaleType.MATRIX);


        mCameraPreview = (SurfaceView) findViewById(R.id.preview_view);
        final SurfaceHolder surfaceHolder = mCameraPreview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setKeepScreenOn(true);
        mCaptureImageButton = (LinearLayout) findViewById(R.id.capture_image_button);
        recording = (LinearLayout) findViewById(R.id.record_button);
//        ok = (ImageView) findViewById(R.id.done);
        mCaptureImageButton.setOnClickListener(mCaptureImageButtonClickListener);

        doneButton = (LinearLayout) findViewById(R.id.ok_button);

        recording.setOnClickListener(mRecaptureImageButtonClickListener);

        doneButton.setOnClickListener(this);

        mIsCapturing = true;

        sizeView.setVisibility(View.GONE);

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                ComplainActivity.this.onBackPressed();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Camera.Parameters params = mCamera.getParameters();
        int action = event.getAction();

        if (event.getPointerCount() > 1) {

            sizeView.setVisibility(View.VISIBLE);
            // handle multi-touch events
            if (action == MotionEvent.ACTION_POINTER_DOWN) {
                mDist = getFingerSpacing(event);
            } else if (action == MotionEvent.ACTION_MOVE
                    && params.isZoomSupported()) {
                mCamera.cancelAutoFocus();
                handleZoom(event, params);
            }
        } else {
            // handle single touch events
            if (action == MotionEvent.ACTION_UP) {
                handleFocus(event, params);

                sizeView.setVisibility(View.GONE);
            }
        }
        return true;
    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;

        Log.d("zooming", "handleZoom: " + (float) zoom / maxZoom * 100);

        float mag = (float) zoom / maxZoom * 100;

        if (mag < 33.3) {

            sizeView.setText("1 X");

        } else if (mag >= 33.3 && mag < 66.6) {

            sizeView.setText("2 X");


        } else if (mag >= 66.6 && mag < 99.9) {

            sizeView.setText("3 X");

        } else {

            sizeView.setText("4 X");

        }


        params.setZoom(zoom);
        mCamera.setParameters(params);
    }

    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        Log.d("fingerSpacing", "getFingerSpacing: " + (float) x * x + y * y);

        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_IS_CAPTURING, mIsCapturing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mIsCapturing = savedInstanceState.getBoolean(KEY_IS_CAPTURING, mCameraData == null);
        if (mCameraData != null) {
            setupImageDisplay();
        } else {
            setupImageCapture();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                mCamera.setPreviewDisplay(mCameraPreview.getHolder());

                parameters = mCamera.getParameters();

                Display display = ((WindowManager)
                        getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                int screenOrientation = display.getRotation();

                int rotation = 0;

                switch (screenOrientation) {

                    case Surface.ROTATION_0:

                        findViewById(R.id.toplayout).setVisibility(View.VISIBLE);
                        rotationField = Surface.ROTATION_0;
                        rotation = Surface.ROTATION_0;
                        mCamera.setDisplayOrientation(90);
                        break;

                    case Surface.ROTATION_90:
                        findViewById(R.id.toplayout).setVisibility(View.GONE);
                        rotationField = Surface.ROTATION_90;
                        rotation = Surface.ROTATION_90;
                        mCamera.setDisplayOrientation(0);
                        break;

                    case Surface.ROTATION_270:
                        findViewById(R.id.toplayout).setVisibility(View.GONE);
                        rotationField = Surface.ROTATION_270;
                        rotation = Surface.ROTATION_270;
                        mCamera.setDisplayOrientation(180);
                        break;

                    case Surface.ROTATION_180:
                        rotationField = Surface.ROTATION_180;
                        rotation = Surface.ROTATION_180;
                        mCamera.setDisplayOrientation(270);
                        break;


                }

                Log.d("rotaion", "onResume: " + rotation);

//                mCamera.setDisplayOrientation(90);
                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (Exception e) {
                Toast.makeText(ComplainActivity.this, "Unable to open camera.", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

//        if (mRecorder != null) {
//            try {
//                mRecorder.stop();
//                mRecorder.release();
//            } catch (Throwable e) {
//
//                Log.d("StopError", "onStop: "+e);
//
//            }
//
//        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCameraData = data;
        setupImageDisplay();
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
                Toast.makeText(ComplainActivity.this, "Unable to start camera preview.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void captureImage() {


        mCamera.takePicture(null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                if (data != null) {
                    int screenWidth = getResources().getDisplayMetrics().widthPixels;
                    int screenHeight = getResources().getDisplayMetrics().heightPixels;
                    Bitmap bm = BitmapFactory.decodeByteArray(data, 0, (data != null) ? data.length : 0);

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && rotationField == 0) {
                        // Notice that width and height are reversed
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, screenHeight, screenWidth, true);
                        int w = scaled.getWidth();
                        int h = scaled.getHeight();
                        // Setting post rotate to 90
                        Matrix mtx = new Matrix();
                        mtx.postRotate(90);
                        // Rotating Bitmap
                        bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);


                    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && rotationField == 1) {// LANDSCAPE MODE
                        //No need to reverse width and height
                        Bitmap scaled = Bitmap.createScaledBitmap(bm, screenWidth, screenHeight, true);
                        int w = scaled.getWidth();
                        int h = scaled.getHeight();
                        // Setting post rotate to 90
                        Matrix mtx = new Matrix();
                        mtx.postRotate(0);
                        // Rotating Bitmap


                        bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);
                    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && rotationField == 3) {

                        Bitmap scaled = Bitmap.createScaledBitmap(bm, screenWidth, screenHeight, true);
                        int w = scaled.getWidth();
                        int h = scaled.getHeight();
                        // Setting post rotate to 90
                        Matrix mtx = new Matrix();
                        mtx.postRotate(180);


                        bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);


                    }
                    bitmapGlobal = bm;
//                    photoPreview.setImageBitmap(bm);
                }
//                isImageCaptured = true;
//                photoPreview.setVisibility(View.VISIBLE);
//                surfaceView.setVisibility(View.GONE);
            }
        });
        mCaptureImageButton.setVisibility(View.INVISIBLE);
//        doneButton.setVisibility(View.VISIBLE);
        recording.setVisibility(View.VISIBLE);

    }

    private void setupImageCapture() {


        try {

            recording.setVisibility(View.GONE);
            doneButton.setVisibility(View.GONE);
            mCameraImage.setVisibility(View.INVISIBLE);

            mCameraPreview.setVisibility(View.VISIBLE);
            mCamera.stopPreview();
            mCamera.startPreview();
            mCaptureImageButton.setVisibility(View.VISIBLE);
//        mCaptureImageButton.setText(R.string.capture_image);
            mCaptureImageButton.setOnClickListener(mCaptureImageButtonClickListener);

        } catch (Throwable e) {

            Log.d(TAG, "" + e);

        }
    }

    private void setupImageDisplay() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
        mCameraImage.setImageBitmap(bitmap);
        bitmapGlobal = bitmap;
//        this.setResult(100);
        mCamera.stopPreview();
        mCameraPreview.setVisibility(View.INVISIBLE);
        mCameraImage.setVisibility(View.VISIBLE);
//        mCaptureImageButton.setText(R.string.recapture_image);
        mCaptureImageButton.setOnClickListener(mRecaptureImageButtonClickListener);
    }

    public static Bitmap bitmapGlobal = null;

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ok_button) {


            new SavingFile().execute();
        }


    }


    class SavingFile extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogSave.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            if (bitmapGlobal != null) {

                File pictureFile = getOutputMediaFile();
                if (pictureFile == null) {
                    Log.d(TAG,
                            "Error creating media file, check storage permissions: ");// e.getMessage());
                }
                try {

                    bitmapGlobal = Bitmap.createScaledBitmap(bitmapGlobal, 480, 640, false);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmapGlobal.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);


                    FileOutputStream fos = new FileOutputStream(pictureFile);

                    fos.write(byteArrayOutputStream.toByteArray());


                    fos.close();

                    stringUri = String.valueOf(Uri.parse(pictureFile.getPath()));

                    uri = Uri.parse(pictureFile.getPath());

                    return uri.toString();
//                    finish();

                } catch (FileNotFoundException e) {

                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String uri) {
            super.onPostExecute(uri);

            uploadImageOnFireBase(uri);

//            Intent intent = new Intent();
//            intent.putExtra(EXTRA_CAMERA_DATA, mCameraData);
//            setResult(RESULT_OK, intent);
//
//            finish();


        }
    }

    private void uploadImageOnFireBase(final String uri) {

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        FirebaseStorage.
                getInstance().
                getReference().
                child("Files").
                child(timeStamp).
                putFile(Uri.fromFile(new File(uri))).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                new File(uri).delete();

                uploadRecording(String.valueOf(taskSnapshot.getDownloadUrl()));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e);
                CustomToast.showToast(ComplainActivity.this, "Please Check Your Connection", MDToast.TYPE_ERROR);

            }
        });

    }

    private void uploadRecording(final String downloadUriForImage) {

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        FirebaseStorage.
                getInstance().
                getReference().
                child("Files").
                child(timeStamp).
                putFile(Uri.fromFile(recordFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String key = FirebaseDatabase.
                        getInstance().
                        getReference().
                        child("Complain").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        push().
                        getKey();

                ComplainModel complainModel = new ComplainModel(key, downloadUriForImage, String.valueOf(taskSnapshot.getDownloadUrl()));

                FirebaseDatabase.
                        getInstance().
                        getReference().
                        child("Complain").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(complainModel.getKey()).
                        setValue(complainModel);

                dialogSave.dismiss();

                CustomToast.showToast(ComplainActivity.this, "Your Complain Is Sent Successfully", MDToast.TYPE_SUCCESS);

                recordFile.delete();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e);
                CustomToast.showToast(ComplainActivity.this, "Please Check Your Connection", MDToast.TYPE_ERROR);
            }
        });

    }


}



