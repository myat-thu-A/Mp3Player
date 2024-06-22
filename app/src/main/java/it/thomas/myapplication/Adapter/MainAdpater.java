package it.thomas.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.thomas.myapplication.SongData;
import it.thomas.myapplication.databinding.ItemSongBinding;

public class MainAdpater extends RecyclerView.Adapter<MainAdpater.MainViewHolder> {

    List<SongData> songDatas;
    SongClickListener songClickListener;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        SongData songData = songDatas.get(position);
        holder.bind(songData);
        String songName = holder.binding.tvSong.getText().toString();

        holder.binding.getRoot().setOnClickListener(v -> {
            songClickListener.onClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return songDatas.size();
    }

    public void setSongDatas(List<SongData> songDatas) {
        this.songDatas = songDatas;
        notifyDataSetChanged();
    }

    public void setSongClickListener(SongClickListener songClickListener) {
        this.songClickListener = songClickListener;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {
        ItemSongBinding binding;

        public MainViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SongData songData) {
            binding.ivArtist.setImageResource(songData.artistPhoto());
            binding.tvArtist.setText(songData.artistName());
            binding.tvSong.setText(songData.songName());
        }
    }
}
