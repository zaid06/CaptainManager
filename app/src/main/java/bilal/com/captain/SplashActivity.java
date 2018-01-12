package bilal.com.captain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import bilal.com.captain.Util.SaveInSharedPreference;

public class SplashActivity extends AppCompatActivity {

    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECORD_AUDIO};

    private static final int REQUEST = 1;

    Thread thread;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            while(!hasCheck()){
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, REQUEST);
            }
        }


        thread = new Thread(){
            @Override
            public void run() {
                try {
                    thread.sleep(3000);
                }catch (Exception ex){
                    ex.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();


    }



    public boolean hasCheck(){

        for(String permission : permissions){

            if(ActivityCompat.checkSelfPermission(SplashActivity.this,permission) != PackageManager.PERMISSION_GRANTED){

                return false;
            }

        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(SplashActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();





                }


        }
    }

}
