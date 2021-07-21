package com.orem.bashhub.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.orem.bashhub.R;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

/**
 * Created by Dawinder on 09/02/2018.
 */

public class MyImagePicker {
    static Context mContexts;
    private static String cameraClickPath = "";
    private static int REQUEST_CODE_GALLERY = 1001;
    private static int REQUEST_CODE_CAMERA = 1002;

    @SuppressLint("NewApi")
    public static void selectImage(final Context mContext, boolean isGalleryOnly, String message) {
        mContexts = mContext;
        if (isGalleryOnly) {
            getGalleryPermission(mContext);
        } else {
            final Dialog dialog = new Dialog(mContext, R.style.themeDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_image_picker);
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            TextView tvMessage = dialog.findViewById(R.id.tvMessage);
            TextView tvGallery = dialog.findViewById(R.id.tvGallery);
            tvMessage.setText(message);
            tvMessage.setVisibility(message.isEmpty() ? View.GONE : View.VISIBLE);
            tvGallery.setText(mContext.getString(message.isEmpty() ? R.string.upload_profile_picture : R.string.choose_image));
            dialog.findViewById(R.id.llCamera).setOnClickListener(v -> {
                dialog.dismiss();
                getCameraPermission(mContext);
            });

            dialog.findViewById(R.id.llGallery).setOnClickListener(v -> {
                dialog.dismiss();
                getGalleryPermission(mContext);
            });

            dialog.show();
        }
    }

    private static void getGalleryPermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ((AppCompatActivity) mContext).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            } else {
                gotoGallery(mContext);
            }
        } else {
            gotoGallery(mContext);
        }
    }

    private static void getCameraPermission(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ((AppCompatActivity) mContext).requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_CAMERA);
            } else {
                gotoCamera(mContext);
            }
        } else {
            gotoCamera(mContext);
        }
    }

    public static void onRequestPermissionsResult(Context mContext, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                gotoGallery(mContext);
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.gallery_permission_required));
            }
        }
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    gotoCamera(mContext);
                } else {
                    Utils.showToast(mContext, mContext.getString(R.string.camera_permission_required));
                }
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.camera_permission_required));
            }
        }
    }

    @SuppressLint("IntentReset")
    private static void gotoGallery(Context mContext) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((AppCompatActivity) mContext).startActivityForResult(Intent.createChooser(intent, "Go To : "), REQUEST_CODE_GALLERY);
    }

    private static void gotoCamera(Context mContext) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile(mContext);
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(mContext, mContext.getPackageName(), photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    ((AppCompatActivity) mContext).startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            } catch (Exception ex) {
                Utils.showLog("Ex : " + ex.getMessage());
                Utils.showToast(mContext, mContext.getString(R.string.failed_to_capture_image));
            }
        }
    }

    private static File createImageFile(Context mContext) throws IOException {
        String imageFileName = "Image_" + System.currentTimeMillis() + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".png", storageDir);
        cameraClickPath = image.getAbsolutePath();
        return image;
    }

    public static void onActivityResult(Context mContext, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri chosenImageUri = data.getData();
                Log.e("imagessss_getScheme", chosenImageUri.getScheme());
                try {
                    String realPath = Utils.getRealPathFromURI(mContext, chosenImageUri);
//                    String realPath = getPath(chosenImageUri);
                    Log.e("imagessss", realPath);

                    assert realPath != null;
                    final File imageFile = new File(realPath);
                    if (imageFile.exists()) {
                        EventBus.getDefault().post(new Events.ImagePickerResult(realPath));
                    } else {
                        Utils.showToast(mContext, mContext.getString(R.string.failed_to_fetch));
                    }
                } catch (Exception e) {
                    Utils.showToast(mContext, mContext.getString(R.string.failed_to_fetch));
                }
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.failed_to_fetch));
            }
        }

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                EventBus.getDefault().post(new Events.ImagePickerResult(cameraClickPath));
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.failed_to_capture_image));
            }
        }
    }

    public static String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContexts.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }
}
