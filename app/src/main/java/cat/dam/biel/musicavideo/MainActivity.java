package cat.dam.biel.musicavideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Button btnSound, btnVideo, btnRemote;
    VideoView videoView;//video
    MediaPlayer mediaPlayer; //audio
    TextView filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView);
        btnSound = (Button) findViewById(R.id.btnSound);
        btnVideo = (Button) findViewById(R.id.btnVideo);
        btnRemote = (Button) findViewById(R.id.btnRemote);

        mediaPlayer = MediaPlayer.create(this, R.raw.audio);
        filename = (TextView) findViewById(R.id.filename);

        mediaPlayer.start(); //nose per que aixo nomes em sona com menys d'un segon

        controlMusica(); //audio
        controlVideo();  //bryant
        //mostrarVideoRemot();//gori (gorila) nose perque no em va aquest
    }

    private void controlMusica() {
        mediaPlayer = MediaPlayer.create(this,R.raw.audio);
        filename.setText("audio.mp3");
        mediaPlayer.start();

        //control botó
        btnSound.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer = null; //evita que es pugui clicar quan s'està alliberant de memòria
                btnSound.setAlpha(1f); //sense trasparència
            }
            else {
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.audio);
                mediaPlayer.start();
                btnSound.setAlpha(.5f); // amb transparència
            }
        });
    }

    private void controlVideo() {
        Uri video = Uri.parse("android.resource://" +
                getPackageName() + "/"
                + R.raw.video);
        videoView.setVideoURI(video);
        videoView.setMediaController(new MediaController(this));
        // control quan finalitza el video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnVideo.setAlpha(1f); // no transparència
            }
        });
        //control botó
        btnVideo.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause(); // per pausa
                btnVideo.setAlpha(1f); // no transparència
            }
            else {
                videoView.requestFocus();
                videoView.start();
                btnVideo.setAlpha(.5f); // amb transparència
            }
        });
    }

    private void mostrarVideoRemot() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        String link = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
        Uri uri = Uri.parse(link);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnRemote.setAlpha(1f); // no transparència
            }
        });
        //control botó
        btnRemote.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause(); // per pausa
                btnRemote.setAlpha(1f); // no transparència
            } else {
                videoView.requestFocus();
                videoView.start();
                btnRemote.setAlpha(.5f); // amb transparència
            }
        });
    }
}