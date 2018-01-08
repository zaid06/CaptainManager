package bilal.com.captain.resideMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bilal.com.captain.R;
import bilal.com.captain.interfaces.UpdateSyncDateInterface;

/**
 * Created by BILAL on 11/15/2017.
 */

public class ResideMenuItem extends LinearLayout implements UpdateSyncDateInterface {

    /** menu item  title */
    private TextView tv_title;
    private ImageView imageView;
    private int direction;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int title) {
        super(context);
        initViews(context);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, String title) {
        super(context);
        initViews(context);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context,  String title, int direction) {
        super(context);
        this.direction = direction;
        initViews(context);
//        imageView.setImageDrawable(context.getResources().getDrawable(image));
        imageView.setVisibility(View.INVISIBLE);
        tv_title.setText(title);

        tv_title.setWidth(120);
    }


    public ResideMenuItem(Context context, int image, String title, int direction) {
        super(context);
        this.direction = direction;
        initViews(context);
        imageView.setImageDrawable(context.getResources().getDrawable(image));
        tv_title.setText(title);
    }
    private void initViews(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (direction == ResideMenu.DIRECTION_LEFT){
            inflater.inflate(R.layout.reside_menu_left_item, this);
            tv_title = (TextView) findViewById(R.id.textView);
            imageView = (ImageView) findViewById(R.id.imageView);
        }else{
//            inflater.inflate(R.layout.reside_menu_right_item, this);
//            tv_title = (TextView) findViewById(R.id.textView);
//            imageView = (ImageView) findViewById(R.id.imageView);
        }
    }

    /**
     * set the title with resource
     * ;
     * @param title
     */
    public void setTitle(int title){
        tv_title.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }

    public void setIcon(int icon){
        imageView.setImageResource(icon);
    }

    @Override
    public void updateDate(String date) {
        tv_title.setText(date);
    }
}
