package it.thomas.myapplication;


import static it.thomas.myapplication.MainActivity.songDatas;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.RawResourceDataSource;
import androidx.media3.exoplayer.ExoPlayer;

import it.thomas.myapplication.databinding.ActivitySongPlayBinding;

public class SongPlay extends AppCompatActivity {
    private ActivitySongPlayBinding binding;
    private SongData data;
    private ExoPlayer exoPlayer;
    private Handler handler;
    private Runnable updateSeekbar;
    private int artistIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataFromIntent();
        initPlayer();
        initSeekbar();
        initListener();
    }

    private void initListener() {
        binding.btNext.setOnClickListener(v -> {
            if (artistIndex == songDatas.length -1) {
                artistIndex = 0;
            } else {
                artistIndex++;
            }
            data = songDatas[artistIndex];
            updatePlayer();
            binding.seekbar.setProgress(0);
        });

        binding.btPause.setOnClickListener(v -> {
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
                binding.btPause.setText("play");
                handler.removeCallbacks(updateSeekbar);
            } else {
                exoPlayer.play();
                binding.btPause.setText("Pause");
                handler.postDelayed(updateSeekbar, 1000);
            }
        });

        binding.btPrev.setOnClickListener(v -> {
            if (artistIndex == 0) {
                artistIndex = songDatas.length - 1;
            } else {
                artistIndex--;
            }
            data = songDatas[artistIndex];
            updatePlayer();
            binding.seekbar.setProgress(0);
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) (exoPlayer.getDuration() * progress /100);
                    exoPlayer.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (exoPlayer.isPlaying())
                    exoPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                exoPlayer.play();
            }
        });
    }

    private void initSeekbar() {
        handler = new Handler();
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(exoPlayer != null) {
                    int currentPosition = (int) exoPlayer.getCurrentPosition();
                    int totalDuration = (int) exoPlayer.getDuration();
                    binding.seekbar.setProgress(currentPosition * 100 / totalDuration);
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void initPlayer() {
        if (exoPlayer == null) {
            exoPlayer = new ExoPlayer.Builder(this).build();
        }
        updatePlayer();
    }

    private void updatePlayer() {
        binding.tvArtist.setText(data.artistName());
        binding.tvSong.setText(data.songName());
        binding.ivArtist.setImageResource(data.artistPhoto());
        @OptIn(markerClass = UnstableApi.class)
        Uri song = RawResourceDataSource.buildRawResourceUri(data.artistSong());

        exoPlayer.setMediaItem(MediaItem.fromUri(song));
        exoPlayer.prepare();
        exoPlayer.play();
        exoPlayer.seekTo(0);
    }

    private void initDataFromIntent() {
        if (getIntent() != null) {
            artistIndex = getIntent().getIntExtra("artist_index", 0);
            data = (SongData) getIntent().getSerializableExtra("artist");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
        handler.removeCallbacks(updateSeekbar);
    }
}