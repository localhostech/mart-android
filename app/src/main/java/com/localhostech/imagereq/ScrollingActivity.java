package com.localhostech.imagereq;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.metrica.YandexMetrica;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static java.net.Proxy.Type.HTTP;
import static java.security.AccessController.getContext;

public class ScrollingActivity extends AppCompatActivity  {
    private AppBarLayout relativeLayout;
    CollapsingToolbarLayout toolbarLayout = null;
    TextView descriptionText = null;
    ImageView mainImage;
    ImageView fullImage;
    Drawable defaultBackground;
    ProgressBar progressBar;
    Boolean isImageOpened;
    FloatingActionButton fab;
    Animation animation;
    Boolean isBlockedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        defaultBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.background, null);
        relativeLayout= (AppBarLayout)findViewById(R.id.app_bar);
        mainImage = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fullImage = (ImageView) findViewById(R.id.full_image);
        //new LoadBackground("http://image.grishazhdanov.ru/%D0%91%D0%B0%D1%80%20%D0%B2%20%C2%AB%D0%A4%D0%BE%D0%BB%D0%B8-%D0%91%D0%B5%D1%80%D0%B6%D0%B5%D1%80%C2%BB@%D0%AD%D0%B4%D1%83%D0%B0%D1%80%20%D0%9C%D0%B0%D0%BD%D0%B5.jpg",
                //"androidfigure").execute();
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //toolbarLayout.setTitle("Бар в Фоли Бержер");
        descriptionText = (TextView) findViewById(R.id.description_text);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto(view);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });
        animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        isImageOpened = false;
        toolbarLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return isBlockedScrollView;
            }
        });
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isImageOpened) {
                    fullImage.setVisibility(View.VISIBLE);
                    fullImage.setImageDrawable(mainImage.getDrawable());
                    fab.setVisibility(View.INVISIBLE);

                    fullImage.startAnimation(animation);
                    isImageOpened = true;
                    isBlockedScrollView = true;
                } else {
                    fullImage.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    isImageOpened = false;
                    isBlockedScrollView = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl , imageName;

        public LoadBackground(String url, String file_name) {
            this.imageUrl = url;
            this.imageName = file_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... urls) {

            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        private Object fetch(String address) throws MalformedURLException,IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);

            mainImage.setImageDrawable(result);
            Log.d("MY_LOG", "Aplplied");
            //relativeLayout.setBackground(result);
        }
    }
    private Uri imageUri;
    private static final int TAKE_PICTURE = 1;

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        //imageView.setImageBitmap(bitmap);
                        toolbarLayout.setTitle("Подождите...");
                        descriptionText.setText("");
                        Log.d("MY_LOG",  defaultBackground.toString());
                        mainImage.setImageDrawable(defaultBackground);
                        progressBar.setVisibility(View.VISIBLE);
                        //Toast.makeText(this, selectedImage.toString(),
                                //Toast.LENGTH_LONG).show();
                        new SendImageTask().execute();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }
    class SendImageTask extends AsyncTask<String, Void, String> {

        private Exception exception;
        String charset = "UTF-8";
        String requestURL = "http://image.grishazhdanov.ru/";
        protected String doInBackground(String... urls) {
            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                multipart.addFilePart("file-0", new File(Environment.getExternalStorageDirectory(),  "pic.jpg"));
                String response = multipart.finish(); // response from server.
                return response;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }
        public String stripHtml(String html) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                return Html.fromHtml(html).toString();
            }
        }
        protected void onPostExecute(String response) {
            // TODO: check this.exception
            // TODO: do something with the feed
            Log.d("MY_LOG", response);

            try {
                JSONObject jObject = new JSONObject(response);
                String pictureName = jObject.getString("name");
                String picturePainter = jObject.getString("painter");
                String pictureDescription = jObject.getString("wiki");
                toolbarLayout.setTitle(pictureName+", "+picturePainter);
                if (pictureDescription != "null") {
                    descriptionText.setText(Html.fromHtml(pictureDescription));
                    descriptionText.setMovementMethod(LinkMovementMethod.getInstance());
                }
                relativeLayout= (AppBarLayout)findViewById(R.id.app_bar);
                try {
                    String url = pictureName+"@"+picturePainter+".jpg";
                    String encodedurl = URLEncoder.encode(url,"UTF-8");
                    encodedurl = encodedurl.replace("+", "%20");
                    Log.d("TEST", requestURL+encodedurl);
                    new LoadBackground(requestURL+encodedurl,
                            "androidfigure").execute();
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
