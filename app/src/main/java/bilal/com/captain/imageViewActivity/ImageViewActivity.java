package bilal.com.captain.imageViewActivity;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import bilal.com.captain.R;

import static android.icu.lang.UCharacter.BidiPairedBracketType.NONE;

public class ImageViewActivity extends AppCompatActivity{

    private String TAG = "TAG";

    Bundle bundle;

    String url;

    Matrix matrix = new Matrix(); Matrix savedMatrix = new Matrix(); PointF startPoint = new PointF(); PointF midPoint = new PointF(); float oldDist = 1f; static final int NONE = 0; static final int DRAG = 1; static final int ZOOM = 2; int mode = NONE;

    ImageView imageView;
    private Matrix matrix1 = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        bundle = getIntent().getExtras();

        url = bundle.getString("url");

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




}