package com.fanya.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.fanya.test.activity.CommonActivityFindIdActivity;
import com.fanya.test.activity.CommonActivityNoFindIdActivity;
import com.frame.project.abstractview.activity.AbstractByCommonActivity;
import com.frame.project.constrants.Constants;
import com.frame.project.cropview.modle.UCrop;
import com.frame.project.cropview.view.UCropView;
import com.frame.project.listener.PermissionListener;
import com.frame.project.modle.ResponseModle;
import com.frame.project.util.IntentUtil;
import com.frame.project.util.PermissionUtil;
import com.frame.project.util.SavePicUtil;
import com.frame.project.util.ToastUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AbstractByCommonActivity implements PermissionListener {

    public Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.main_test_common;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        commonHolder.setOnClickListener(this, R.id.one, R.id.two,
                R.id.three, R.id.four, R.id.five, R.id.six);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.one:
                IntentUtil.INSTRANSE.openActivity(this, CommonActivityNoFindIdActivity.class, null);
                break;
            case R.id.two:
                IntentUtil.INSTRANSE.openActivity(this, CommonActivityFindIdActivity.class, null);
                break;
            case R.id.three:
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("USERCODE", "traffic");
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("userJSON ", jsonObject.toString());
                    activityPresenter.postByJson(Constants.requestUrl.LOGINURL, jsonObject1, Constants.requestUrl.LOGINURL);
                } catch (Exception ex) {

                }
                break;
            case R.id.four:

                break;
            case R.id.five:
                PermissionUtil.INSTRANSE.responsePermission(this, this, new String[]{Constants.permissionStr.CALL_PHONE});
                break;
            case R.id.six:
                pickFromGallery();
                break;
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String falMessage, String responseTag) {

    }

    @Override
    public void onNext(ResponseModle responseModle, String responseTag) {

    }


    @Override
    public void hasPermission() {
        ToastUtil.INSTRANSE.showSuccess("给你了", this);
        selectPic();
    }

    @Override
    public void hasNoPermission() {
        ToastUtil.INSTRANSE.showError("没给你", this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PermissionUtil.INSTRANSE.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //打开相册
    private void pickFromGallery() {
        PermissionUtil.INSTRANSE.responsePermission(this, this, new String[]{Constants.permissionStr.WRITE_EXTERNAL_STORAGE
        });
    }
    private void selectPic(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择图片"),
                0X11);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x11) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    ToastUtil.INSTRANSE.showError("无法裁剪当前选中的图片",this);
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = "hdd.png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop.start(MainActivity.this);
    }
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            ToastUtil.INSTRANSE.showSuccess("裁剪成功",this);

            String filepath = Constants.picturePath + File.separator + Calendar.getInstance().getTimeInMillis() + ".jpg";
            final File filePic = new File(filepath);
            // 保存图片至手机
            Bitmap bitmap = getBitmapFromUri(resultUri);
         int code =  SavePicUtil.INSTARNS.savePic(bitmap, filePic);
            if(code == 1){
                commonHolder.setImageByUrl(this , R.id.main_iv_crop ,filePic , R.drawable.smile);
                if(!bitmap.isRecycled()){
                    bitmap.recycle();
                    bitmap = null ;
                }
//                commonHolder.setImageByBitmap(this ,  R.id.main_iv_crop, bitmap);
            }else{

            }

            try {
                ((UCropView)commonHolder.getView(R.id.main_uc_crop)).getCropImageView().setImageUri(resultUri, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.INSTRANSE.showError("无法裁剪当前选中的图片",this);
        }
    }
    private Bitmap getBitmapFromUri(Uri uri)
    {
        try {;
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }catch (Exception e){
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            ToastUtil.INSTRANSE.showError(cropError.getMessage(),this);
        } else {
            ToastUtil.INSTRANSE.showError("你大爷",this);
        }
    }

}
