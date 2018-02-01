package bilal.com.captain.imageViewActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.FloatMath;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import bilal.com.captain.R;

import static android.icu.lang.UCharacter.BidiPairedBracketType.NONE;
import static java.security.AccessController.getContext;

public class ImageViewActivity extends AppCompatActivity{

    private String TAG = "TAG";

    Bundle bundle,b,p;

    String url;

    String check;

    int position;

    Context context;


    Matrix matrix = new Matrix(); Matrix savedMatrix = new Matrix(); PointF startPoint = new PointF(); PointF midPoint = new PointF(); float oldDist = 1f; static final int NONE = 0; static final int DRAG = 1; static final int ZOOM = 2; int mode = NONE;

    ImageView imageView;
    private Matrix matrix1 = new Matrix();

    ImageView download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        bundle = getIntent().getExtras();

        url = bundle.getString("url");

        b = getIntent().getExtras();

        check = b.getString("check");

        p = getIntent().getExtras();

        position = p.getInt("position");

        download = (ImageView)findViewById(R.id.downloadimage);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view,check,url);
            }
        });

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewActivity.super.onBackPressed();
            }
        });

        imageView = (ImageView) findViewById(R.id.image);

        Glide.with(ImageViewActivity.this)
                .load(url)
                .placeholder(R.drawable.loader)
                .error(R.drawable.loader)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;

//                Drawable d = imageView.getDrawable();
//                // TODO: check that d isn't null
//
//                RectF imageRectF = new RectF(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                RectF viewRectF = new RectF(0, 0, imageView.getWidth(), imageView.getHeight());
//                matrix1.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
//                imageView.setImageMatrix(matrix1);
//                ((ImageView) v).setImageMatrix(matrix1);
                System.out.println("matrix=" + savedMatrix.toString());
                switch (event.getAction() & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix); startPoint.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f){
                            savedMatrix.set(matrix);
                            midPoint(midPoint, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = NONE;
                            break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event); if (newDist > 10f) { matrix.set(savedMatrix); float scale = newDist / oldDist; matrix.postScale(scale, scale, midPoint.x, midPoint.y); } } break; } view.setImageMatrix(matrix); return true;



            }

            @SuppressLint("FloatMath") private float spacing(MotionEvent event)
            {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(x * x + y * y);
            }
            private void midPoint(PointF point, MotionEvent event)
            {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }


        });


    }

    private void showMenu(final View v, final String check, final String url) {

        PopupMenu show = new PopupMenu(this, v);

        show.inflate(R.menu.download_image_menu);

        show.show();

        show.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.download:
                        downloadImage(url,check);
                        return true;
                    default:
                        return false;
                }

            }
        });


    }

    private void downloadImage(String url, String stakeHolders) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Activity.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);

        File ff = new File(String.valueOf(url.lastIndexOf('/') + 1));

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setDescription("Downloading.. " + ff.getName()).setTitle("Captain Manager");

        Toast.makeText(this, "Downloading" , Toast.LENGTH_SHORT).show();

        request.setDestinationInExternalPublicDir(returnDestination(stakeHolders), ff.getName() + ".jpg");

        request.setVisibleInDownloadsUi(true);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

    private String returnDestination(String stakeHolders) {
        if(stakeHolders.equals("reciever")) {
            return "/Captain/Recieved";
        }else {
            return "/Captain/Sent";
        }
    }


}