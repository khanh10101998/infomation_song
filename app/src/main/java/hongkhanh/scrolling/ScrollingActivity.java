package hongkhanh.scrolling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ScrollingActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerview;
    TextView tv_song, tv_singer, name_song_activity_information_song;
    String API_KEY = "AIzaSyDyBMy6dGSUbm0InyheF5AVhIo9EVyBEkM";
    String song = "Khong the noi";
    String singer = "Trieu Le Dinh";
    String YOUTUBE_ID = "oJa2AJiXdNI";
    int REQUEST_VIDEO = 123;
    TextView tv_lyrics;
    ShareButton shareButton;
    ShareDialog shareDialog;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    LinearLayout line_lyrics;
    CallbackManager callbackManager;
    CollapsingToolbarLayout collapsing_toolbar;
    AppBarLayout appBarLayout;
    AppBarLayout.OnOffsetChangedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFacebookTracker();
        setContentView(R.layout.activity_information_song);
        initControl();
        initData();
        initEvent();
        initDisplay();

        shareDialog = new ShareDialog(ScrollingActivity.this);


        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ScrollingActivity.this, "facebook ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void initDisplay() {

    }

    private void initEvent() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("https://www.youtube.com/watch?v=" + YOUTUBE_ID))
                            .build();
                    shareDialog.show(content);
                }
            }
        });

        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Display display = getWindowManager().getDefaultDisplay();
                if (collapsing_toolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsing_toolbar)) {
                    name_song_activity_information_song.animate().alpha(1).setDuration(600);



                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(display.getWidth(),dpToPx(200));
                    line_lyrics.setLayoutParams(param);
                } else {
                    name_song_activity_information_song.animate().alpha(0).setDuration(600);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(display.getWidth(),dpToPx(100));
                    line_lyrics.setLayoutParams(param);
                }
            }
        };
        appBarLayout.addOnOffsetChangedListener(mListener);


    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = ScrollingActivity.this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void initData() {
        tv_lyrics.setText("Trái tim của em rất đau\nChỉ muốn buông tình ta ở đây\nVì cho đến giờ chẳng có ai biết em tồn tại\nNhững lần chào nhau bối rối\n Người ở bên cạnh anh chẳng nghi ngờ\n Lòng em lại chẳng nhẹ nhàng\n Chorus:\n Lời biệt ly buồn đến mấy cũng không thể nào làm cho em gục ngã đến mức tuyệt vọng\n Chỉ là vết thương sâu một chút thôi anh àh\n Ngày mà anh tìm đến, em tin anh thật lòng\n Và yêu em bằng những cảm xúc tự nguyện\n Làm em quá yêu nên mù quáng đến yếu lòng\n Là ngày chúng ta bắt đầu những sai lầm\n ");
        name_song_activity_information_song.setText(song);
        tv_singer.setText(singer);
        tv_song.setText(song);
    }

    private void initControl() {
        name_song_activity_information_song = (TextView) findViewById(R.id.name_song_activity_information_song);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout       = (AppBarLayout) findViewById(R.id.appbar);
        line_lyrics        = (LinearLayout) findViewById(R.id.line_lyrics);
        tv_lyrics          = (TextView) findViewById(R.id.tv_lyrics);
        tv_song            = (TextView) findViewById(R.id.tv_song);
        tv_singer          = (TextView) findViewById(R.id.tv_singer);
        youTubePlayerview  = (YouTubePlayerView) findViewById(R.id.youtube);
        shareButton        = (ShareButton) findViewById(R.id.fb_share_button);
        youTubePlayerview.initialize(API_KEY, this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void initFacebookTracker() {

        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(YOUTUBE_ID);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(ScrollingActivity.this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO) {
            youTubePlayerview.initialize(API_KEY, ScrollingActivity.this);
        }
    }
}

