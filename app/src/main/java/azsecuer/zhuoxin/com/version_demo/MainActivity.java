package azsecuer.zhuoxin.com.version_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    String mobile="1111111";
    private TextView textView;
    String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};//需要用的的权限数组
    //注意  manifests里面也要添加相对应的权限才行
    List<String> permissionlis=new ArrayList<>();//添加未被授权的权限
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.text);
        onCall(mobile);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDirectly(mobile);//拨打电话的操作
            }
        });

    }
    private void callDirectly(String mobile){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
    }

    public void onCall(String mobile){
        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.CALL_PHONE);

            for (int i = 0; i <permissions.length ; i++) {
                if(ContextCompat.checkSelfPermission(this,permissions[i])!=PackageManager.PERMISSION_GRANTED){
                    permissionlis.add(permissions[i]);
                }
            }
            if(!permissionlis.isEmpty()){
             String [] permission=permissionlis.toArray(new String[permissionlis.size()]);//将未被授权的权限转为String数组
                ActivityCompat.requestPermissions(this,permission,REQUEST_CODE_ASK_CALL_PHONE);
            }else {
               //上面已经写好的拨号方法
               //allDirectly(mobile);//拨打电话的操作
            }

//            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
//                return;
//            }else{
//                //上面已经写好的拨号方法
////                callDirectly(mobile);//拨打电话的操作
//            }
        } else {
            //上面已经写好的拨号方法
//            callDirectly(mobile);//拨打电话的操作
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
////                    callDirectly(mobile);//拨打电话的操作
//                } else {
//                    // Permission Denied
//                    Toast.makeText(MainActivity.this, "CALL_PHONE Denied", Toast.LENGTH_SHORT)
//                            .show();
//                }
                if(grantResults.length>0){
                    for (int i = 0; i <grantResults.length ; i++) {//遍历
                      if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                          Toast.makeText(this, "不成功", Toast.LENGTH_SHORT).show();

                      }else {
                          Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                      }
                    }
                }else {
                    Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
