package com.orem.bashhub.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.orem.bashhub.R;
import com.orem.bashhub.interfaces.OnDatePick;
import com.orem.bashhub.interfaces.OnTimePick;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    public static void goToFragment(Context mContext, Fragment fragment, int container) {
        FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        /*transaction.replace(container, fragment);
        transaction.addToBackStack(fragment.getTag());
        transaction.commitAllowingStateLoss();*/
        Fragment oldFragment = ((FragmentActivity) mContext).getSupportFragmentManager().findFragmentById(container);
        transaction.add(container, fragment);
        transaction.addToBackStack(null);
        if (oldFragment != null) transaction.hide(Objects.requireNonNull(oldFragment));
        transaction.commit();
    }

    public static void removeAllFragment(Context mContext) {
        int count = ((FragmentActivity) mContext).getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            ((FragmentActivity) mContext).getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public static Dialog createDiallogs(Context context, int layout) {
        ColorDrawable dialogColor = new ColorDrawable(Color.TRANSPARENT);
        dialogColor.setAlpha(0); //(0-255) 0 means fully transparent, and 255 means fully opaque
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        // dialog.getWindow().setDimAmount(0.0f);

        return dialog;
    }

    public static String getRealPathFromURI(Context mContext, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]
                        {
                                split[1]
                        };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
//          return cursor.getString(column_index);
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static void intentToBrowser(Context mContext, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(Intent.createChooser(intent, "Go To : "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isStartDate(String selectedDate) {
        try {
            Calendar sCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date sDate = sdf.parse(selectedDate);
            return Objects.requireNonNull(sDate).after(sCalendar.getTime());
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

   /* public static ApiInterface requestApiDefault() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(logging);
        OkHttpClient client = clientBuilder.build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestInterface.BASE_URL_WEB).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiInterface apis = retrofit.create(ApiInterface.class);
        return apis;
    }
    public static void showLog(String message) {
        Log.e("LOG", "" + message);
    }*/

    /**
     * Check internet availabilty
     *
     * @param mContext Context of activity or fragment
     * @return Returns true is internet connected and false if no internet connected
     */
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @SuppressWarnings("ConstantConditions")
    public static Dialog createDialog(Context context, String message, String title, String ok, int layout) {
        ColorDrawable dialogColor = new ColorDrawable(Color.TRANSPARENT);
        dialogColor.setAlpha(0); //(0-255) 0 means fully transparent, and 255 means fully opaque
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(true);

        TextView msg = dialog.findViewById(R.id.message);
        AppCompatCheckBox mNotificationShow = dialog.findViewById(R.id.mNotificationShow);
        PrefStore prefStore = new PrefStore(context);
        prefStore.saveString("isNotificationShow", "0");
        mNotificationShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotificationShow.isChecked()) {
                    PrefStore prefStore = new PrefStore(context);
                    prefStore.saveString("isNotificationShow", "1");
                } else {
                    PrefStore prefStore = new PrefStore(context);
                    prefStore.saveString("isNotificationShow", "0");
                }
            }
        });
        msg.setText(message);
        TextView obtk = dialog.findViewById(R.id.ok);
        obtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        dialog.show();
        //  dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        // dialog.getWindow().setDimAmount(0.0f);

        return dialog;
    }


    /**
     * Show toast message
     *
     * @param mContext Context of activity or fragment
     * @param message  Message that show into the Toast
     */
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Show alert dialog
     *
     * @param mContext         Context of activity o fragment
     * @param message          Message that shows on Dialog
     * @param title            Title for dialog
     * @param positiveText     Set positive text
     * @param positiveListener Set functionality on positive button click
     * @param negativeListener Set functionality on negative button click
     * @param negativeText     Negative button text
     * @param neutralText      Neturat button text
     * @param neutralListener  Set Netural button listener
     * @param isCancelable     true -> Cancelable True ,false -> Cancelable False
     * @return dialog
     */
    public static AlertDialog.Builder showDialog(Context mContext, String message, String title, String positiveText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, String negativeText, String neutralText, DialogInterface.OnClickListener neutralListener, boolean isCancelable) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNegativeButton(negativeText, negativeListener);
        alert.setPositiveButton(positiveText, positiveListener);
        alert.setNeutralButton(neutralText, neutralListener);
        alert.setCancelable(isCancelable);
        try {
            alert.show();
        } catch (Exception e) {
        }
        return alert;
    }

    public static AlertDialog.Builder showMessageDialog(Context mContext, String title, String message) {
        return showDialog(mContext, message, title, "ok", null, null, null, null, null, false);
    }

    public static AlertDialog.Builder showHelpDialog(Context mContext, String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negtiveListener, DialogInterface.OnClickListener editList, String text) {
        return showDialog(mContext, message, title, "No", positiveListener, negtiveListener, "Yes", text, editList, false);
    }

    public static Dialog showCustomMessageDialog(Context mContext, String title, String message) {
        return createDialog(mContext, message, title, "ok", R.layout.alert_dialog_box);
    }

    public static AlertDialog.Builder showMessageDialog(Context mContext, String title, String message, DialogInterface.OnClickListener positiveListener) {
        return showDialog(mContext, message, title, "ok", positiveListener, null, null, null, null, false);
    }

    public static void downloadFile(String dwnload_file_path, String fileName, String pathToSave) {
        int downloadedSize = 0;
        int totalSize = 0;
        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            // connect
            urlConnection.connect();
            File myDir;
            myDir = new File(pathToSave);
            myDir.mkdirs();
            // create a new file, to save the downloaded file
            String mFileName = fileName;
            File file = new File(myDir, mFileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
            // this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            // runOnUiThread(new Runnable() {
            // public void run() {
            // pb.setMax(totalSize);
            // }
            // });
            // create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                // runOnUiThread(new Runnable() {
                // public void run() {
                // pb.setProgress(downloadedSize);
                // float per = ((float)downloadedSize/totalSize) * 100;
                // cur_val.setText("Downloaded " + downloadedSize + "KB / " +
                // totalSize + "KB (" + (int)per + "%)" );
                // }
                // });
            }
            // close the output stream when complete //
            fileOutput.close();
            // runOnUiThread(new Runnable() {
            // public void run() {
            // // pb.dismiss(); // if you want close it..
            // }
            // });
        } catch (final MalformedURLException e) {
            // showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            // showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            // showError("Error : Please check your internet connection " + e);
        }
    }
  /*  public static String getTimeString(Context context, String unformattedTime, SimpleDateFormat unformattedTimeFormat, TimeZone timezone) {
        Locale locale = Locale.ENGLISH;
        String time = unformattedTime;
        try {
            Date date = unformattedTimeFormat.parse(unformattedTime);
            Calendar messageDate = Calendar.getInstance();
            messageDate.setTime(date);
            Calendar todayDate = Calendar.getInstance();
            Calendar yesterdayDate = Calendar.getInstance();
            yesterdayDate.add(Calendar.DAY_OF_YEAR, -1);

            if (messageDate.get(Calendar.DAY_OF_YEAR) == todayDate.get(Calendar.DAY_OF_YEAR) &&
                    messageDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                long differenceInMillis = getTimeDifferenceInMillis(messageDate, todayDate);
                long seconds = differenceInMillis / 1000;
                int minutes = (int) (seconds / 60);
                int hours = (int) (minutes / 60);
                if (hours < 1) {
                    if (minutes < 1) {
                        time = String.format(locale, context.getString(R.string.seconds_ago_pattern), "" + seconds);
                    } else {
                        time = String.format(locale, context.getString(R.string.minutes_ago_pattern), "" + minutes);
                    }
                } else {
                    time = String.format(locale, context.getString(R.string.hours_ago_pattern), "" + hours);
                }
            } else if (messageDate.get(Calendar.DAY_OF_YEAR) == yesterdayDate.get(Calendar.DAY_OF_YEAR) &&
                    messageDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                time = String.format(locale, context.getString(R.string.one_ago_pattern), new SimpleDateFormat("hh:mm a", locale).format(date));
            } else if (messageDate.get(Calendar.WEEK_OF_MONTH) == todayDate.get(Calendar.WEEK_OF_MONTH) &&
                    messageDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                time = new SimpleDateFormat("EEE HH:mm", locale).format(date);
            } else if (messageDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                time = new SimpleDateFormat("dd MMM HH:mm", locale).format(date);
            } else {
                time = new SimpleDateFormat("dd MMM''yy HH:mm", locale).format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    private static long getTimeDifferenceInMillis(Calendar messageDate, Calendar todayDate) {
        return (todayDate.getTime().getTime() - messageDate.getTime().getTime());
    }*/

    /**
     * Show Log
     *
     * @param message Message that want to show into Log
     */
    public static void showLog(String message) {
        Log.e("Log Message", "" + message);
    }

    /**
     * To check that is Email is valid or not
     *
     * @param email Email ID that you want to check
     * @return True id Email is valid otherwise returns False
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Hide Soft Keyboard
     *
     * @param mContext Context of the Activity or Fragment.
     */
    public static void hideKeyboard(Context mContext) {
        View view = ((Activity) mContext).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * To get unique number
     *
     * @return Unique Integer
     */
    public static int getUniqueNumber() {
        Random random = new Random();
        return random.nextInt(999999999 - 100000000) + 100000000;
    }

    /**
     * Load image into imageview
     *
     * @param mContext    Context of Activity or Fragment
     * @param url         Url that want to load into Imageview
     * @param imageView   ImageView in which url loads
     * @param placeholder Drawable image while loading image from Url
     */
    public static void loadImage(Context mContext, Object url, final ImageView imageView, int placeholder) {
        Glide.with(mContext).load(url).apply(new RequestOptions().placeholder(placeholder)).into(imageView);
    }

    public static void underlineTextView(TextView view) {
        view.setPaintFlags(view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * To print HashKey in Logcat(Tag : MY KEY HASH)
     *
     * @param mContext context of activity or fragment
     */
    public static void printHashKey(Context mContext) {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * To show Date Picker and set selected Date in TextView
     *
     * @param mContext context of activity or fragment
     * @param listener Listener to listen picked date
     */
    public static void showDatePickerDialog(Context mContext, String selectedDate, final OnDatePick listener) {
        Calendar mcurrentDate = Calendar.getInstance();
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date sDate = inputPattern.parse(selectedDate);
            mcurrentDate.setTime(Objects.requireNonNull(sDate));
        } catch (Exception e) {
            Log.e("exp", "in catch : " + e.getMessage());
        }
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SetTextI18n") DatePickerDialog mDatePicker = new DatePickerDialog(mContext, (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            int month = selectedmonth + 1;
            String m = month > 9 ? "" + month : "0" + month;
            String d = selectedday > 9 ? "" + selectedday : "0" + selectedday;
            listener.OnDatePick(selectedyear + "-" + m + "-" + d);
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
        mDatePicker.show();
    }

    public static void showRepeatDatePickerDialog(Context mContext, String selectedDate, final OnDatePick listener) {
        Calendar mcurrentDate = Calendar.getInstance();
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date sDate = inputPattern.parse(selectedDate);
            mcurrentDate.setTime(Objects.requireNonNull(sDate));
            mcurrentDate.add(Calendar.DAY_OF_MONTH, 7);
        } catch (Exception e) {
            Log.e("exp", "in catch : " + e.getMessage());
        }
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SetTextI18n") DatePickerDialog mDatePicker = new DatePickerDialog(mContext, (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            int month = selectedmonth + 1;
            String m = month > 9 ? "" + month : "0" + month;
            String d = selectedday > 9 ? "" + selectedday : "0" + selectedday;
            listener.OnDatePick(selectedyear + "-" + m + "-" + d);
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTime().getTime());
        mDatePicker.show();
    }

    public static void showDatePickerDialogHost(Context mContext, final OnDatePick listener) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SetTextI18n") DatePickerDialog mDatePicker = new DatePickerDialog(mContext, (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            int month = selectedmonth + 1;
            String m = month > 9 ? "" + month : "0" + month;
            String d = selectedday > 9 ? "" + selectedday : "0" + selectedday;
            listener.OnDatePick(selectedyear + "-" + m + "-" + d);
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(mcurrentDate.getTime().getTime());
        mDatePicker.show();
    }

    public static void showDOBPickerDialog(Context mContext, final OnDatePick listener) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        @SuppressLint("SetTextI18n") DatePickerDialog mDatePicker = new DatePickerDialog(mContext, (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            int month = selectedmonth + 1;
            String m = month > 9 ? "" + month : "0" + month;
            String d = selectedday > 9 ? "" + selectedday : "0" + selectedday;
            listener.OnDatePick(selectedyear + "-" + m + "-" + d);
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(mcurrentDate.getTime().getTime());
        mDatePicker.show();
    }

    /**
     * To show Time Picker and set selected Time in TextView
     *
     * @param mContext context of activity or fragment
     */
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    public static void showTimePickerDialog(Context mContext, String selectedDate, String selectedTime, final OnTimePick listener) {
        Calendar mcurrentTime = Calendar.getInstance();
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
        try {
            Date sDate = inputPattern.parse(selectedDate + " " + selectedTime);
            mcurrentTime.setTime(Objects.requireNonNull(sDate));
        } catch (Exception e) {
            Log.e("exp", "in catch : " + e.getMessage());
        }
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, (timePicker, selectedHour, selectedMinute) -> {
            String AM_PM = selectedHour >= 12 ? "PM" : "AM";
            String min = selectedMinute > 9 ? "" + selectedMinute : "0" + selectedMinute;
            String hour1;
            if (selectedHour < 10) {
                hour1 = "0" + selectedHour;
            } else if (selectedHour < 13) {
                hour1 = "" + selectedHour;
            } else {
                int h = selectedHour - 12;
                hour1 = h > 9 ? "" + h : "0" + h;
            }
            String time = hour1 + ":" + min + " " + AM_PM;
            if (isCorrectDate(Calendar.getInstance(), selectedDate + " " + time)) {
                listener.OnTimePick(time);
            } else {
                showToast(mContext, mContext.getString(R.string.current_time_error));
            }
        }, hour, minute, false);
        mTimePicker.show();
    }

    /**
     * Get LatLng type from String lat lng
     *
     * @param latitude  String lattitude
     * @param longitude String longitude
     * @return LatLng type from String lat lng
     */
    public static LatLng getLatLng(String latitude, String longitude) {
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c);
    }

    public static String changeTimeFormat(String time) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("HH:mm:ss", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            Date prevDate = inputPattern.parse(time);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return time;
        }
    }

    public static String changeTimeFormat1(String time) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("hh:mm a", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("HH:mm:ss", Locale.US);
        try {
            Date prevDate = inputPattern.parse(time);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return time;
        }
    }

    public static String changeTimeFormat2(String time) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("HH:mm:ss", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            Date prevDate = inputPattern.parse(time);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return time;
        }
    }

    /**
     * Get two digits after decimal for any double number
     *
     * @param number Number that want to convert
     * @return String Number having two digits after decimal
     */
    public static String getTwoDigitsAfterDecimal(String number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.00");
        number = (number != null && !number.isEmpty()) ? number : "0";
        return "" + df.format(Double.parseDouble(number));
    }

    public static String changeDateFormat(String dateTime) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("MMM-dd-yyyy", Locale.US);
        try {
            Date prevDate = inputPattern.parse(dateTime);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String changeDateFormatHost(String dateTime) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("dd MMM,yyyy", Locale.US);
        try {
            Date prevDate = inputPattern.parse(dateTime);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String changeDateFormatHost1(String dateTime) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("EEE, MMM dd", Locale.US);
        try {
            Date prevDate = inputPattern.parse(dateTime);
            return outputPattern.format(prevDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String changeDateFormatHostTime(String startTime, String endTime) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat startPattern = new SimpleDateFormat("h", Locale.US);
        SimpleDateFormat endPattern = new SimpleDateFormat("h a", Locale.US);
        try {
            Date startDate = inputPattern.parse(startTime);
            Date endDate = inputPattern.parse(endTime);
            return (startPattern.format(startDate) + "-" + endPattern.format(endDate)).replace(" ", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isCorrectDate(Calendar currentDate, String selectedDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
            Date sDate = sdf.parse(selectedDate);
            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sDate);
            return sCalendar.after(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCorrectTime(String startTime, String endTime) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            Calendar cStart = Calendar.getInstance();
            Calendar cEnd = Calendar.getInstance();
            cStart.setTime(startDate);
            cEnd.setTime(endDate);
            return cEnd.after(cStart);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidEndDate(String start, String end) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
//            Calendar cStart = Calendar.getInstance();
//            Calendar cEnd = Calendar.getInstance();
//            cStart.setTime(startDate);
//            cEnd.setTime(endDate);
            if (startDate.after(endDate)) {
                return false;
            } else {
                return true;
            }
            /*if (cEnd.get(Calendar.MONTH) < cStart.get(Calendar.MONTH))
                return false;
            if (cEnd.get(Calendar.MONTH) > cStart.get(Calendar.MONTH))
                return true;
            else
                return cEnd.get(Calendar.DAY_OF_MONTH) >= cStart.get(Calendar.DAY_OF_MONTH);*/
        } catch (Exception e) {
            return false;
        }
    }

    public static String addOneDay(String selectedDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date sDate = sdf.parse(selectedDate);
            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sDate);
            sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            return sdf.format(sCalendar.getTime());
        } catch (Exception e) {
            return selectedDate;
        }
    }

    public static String addSevenDay(String selectedDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date sDate = sdf.parse(selectedDate);
            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(sDate);
            sCalendar.add(Calendar.DAY_OF_MONTH, 7);
            return sdf.format(sCalendar.getTime());
        } catch (Exception e) {
            return selectedDate;
        }
    }

    public static boolean isDateEquals(Date currentDate, Date selectedDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return sdf.format(currentDate).equals(sdf.format(selectedDate));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDOB(String selectedDate) {
        try {
            Calendar sCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date sDate = sdf.parse(selectedDate);
            return Objects.requireNonNull(sDate).before(sCalendar.getTime());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidMonth(Date currentDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date startDate = sdf.parse(sdf.format(currentDate));
            Calendar current = Calendar.getInstance();
            Calendar selected = Calendar.getInstance();
            current.setTime(startDate);
            if (current.get(Calendar.YEAR) >= selected.get(Calendar.YEAR)) {
                return current.get(Calendar.YEAR) != selected.get(Calendar.YEAR) || current.get(Calendar.MONTH) > selected.get(Calendar.MONTH);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDay(Date currentDate) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date startDate = sdf.parse(sdf.format(currentDate));
            Calendar current = Calendar.getInstance();
            Calendar selected = Calendar.getInstance();
            current.setTime(startDate);
            if (current.get(Calendar.YEAR) >= selected.get(Calendar.YEAR)) {
                return current.get(Calendar.YEAR) != selected.get(Calendar.YEAR) ||
                        current.get(Calendar.DAY_OF_YEAR) >= selected.get(Calendar.DAY_OF_YEAR);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDayName(Date date) {
        SimpleDateFormat pattern = new SimpleDateFormat("EEE", Locale.US);
        try {
            return pattern.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return "";
        }
    }

    public static String getDayNo(Date date) {
        SimpleDateFormat pattern = new SimpleDateFormat("dd", Locale.US);
        try {
            return pattern.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return "";
        }
    }

    public static String getFullDate(Date date) {
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            return pattern.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("exp", "in catch");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * To get Arraylist from comma seprated String
     *
     * @param text To convert into Arraylist
     * @return Arraylist return that get from String
     */
    public static ArrayList<String> getArrayFromString(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(Arrays.asList(text.split("\\s*,\\s*")));
    }

    /**
     * To get comma seprated string from Arraylist
     *
     * @param list Arraylist that you want to convert into comma seprated String
     * @return String Comma seprated
     */
    public static String getStringFromArray(ArrayList<String> list) {
        String vaule = "";
        for (int i = 0; i < list.size(); i++) {
            vaule = vaule.isEmpty() ? list.get(i) : (vaule + "," + list.get(i));
        }
        return vaule;
    }

    /**
     * To convert string to Base64Encode
     *
     * @param text string
     * @return Base64 encoded string
     */
    public static String convertBase64Encode(String text) {
        return Base64.encodeToString(text.getBytes(), Base64.NO_WRAP);
    }

    public static String convertBase64Decode(String text) {
        byte[] data = Base64.decode(text, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * Simple Share Intent
     *
     * @param mContext Context of the Activity or Fragment.
     * @param text     Text that you want to share with intent
     */
    public static void shareContent(Context mContext, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, "Go To : "));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getTempFilePath(Context mContext, Bitmap bm) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            File f = new File(mContext.getCacheDir(), "" + System.currentTimeMillis() + ".png");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f.getAbsolutePath();
        } catch (Exception e) {
            Utils.showLog("Ecp : " + e.getMessage());
            return "";
        }
    }

    public static Bitmap getImageInPortrait(String file) {
        try {
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file, bounds);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(file, opts);
            ExifInterface exif;
            exif = new ExifInterface(file);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        } catch (Exception e) {
            showLog("Exp : " + e.getMessage());
            return BitmapFactory.decodeFile(file);
        }
    }

    public static String getCompressFile(Context mContext, Bitmap bm) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            byte[] bitmapdata = bos.toByteArray();
            File f = new File(mContext.getCacheDir(), "" + System.currentTimeMillis() + ".png");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f.getAbsolutePath();
        } catch (Exception e) {
            Utils.showLog("Ecp : " + e.getMessage());
            return "";
        }
    }

    /**
     * To make dialog transparent
     *
     * @param dialog Dialog you want to make transparent
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void makeDialogTransparent(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    /**
     * Intent to Google Map
     *
     * @param mContext Context of the Activity or Fragment.
     * @param mLat     Latitude
     * @param mLong    Longitude
     */
    public static void intentToMap(Context mContext, String mLat, String mLong) {
        String label = mContext.getString(R.string.app_name);
        String uriBegin = "geo:" + mLat + "," + mLong;
        String query = mLat + "," + mLong + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=10";
        Utils.showLog(uriString);
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(Intent.createChooser(intent, "Go To : "));
    }

    /**
     * Get Time from Timestamp
     *
     * @param TimeInMilis Timestamp
     * @return Returns Time according to given Timestamp
     */
    @SuppressWarnings("WeakerAccess")
    public static String getTimeFromTimeStamp(Context mContext, String TimeInMilis) {
        return DateUtils.formatDateTime(mContext, Long.parseLong(TimeInMilis), DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * Get dominant color from bitmap
     *
     * @param bitmap Image Bitmap
     * @return Returns int color code from image bitmap
     */
    @SuppressWarnings("unchecked")
    public static int getDominantColor(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Map m = new HashMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bitmap.getPixel(i, j);
                Integer counter = (Integer) m.get(rgb);
                if (counter == null)
                    counter = 0;
                counter++;
                m.put(rgb, counter);
            }
        }
        List list = new LinkedList(m.entrySet());
        Collections.sort(list, (Comparator) (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue()));
        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArr((Integer) me.getKey());
        String color = Integer.toHexString(rgb[0]) + " " + Integer.toHexString(rgb[1]) + " " + Integer.toHexString(rgb[2]);
        return Color.parseColor(color.length() == 8 ? "#" + color.replaceAll("\\s", "") : "#000000");
    }

    private static int[] getRGBArr(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};
    }
}