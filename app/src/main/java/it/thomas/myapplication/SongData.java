package it.thomas.myapplication;

import java.io.Serializable;

public record SongData(
        String artistName,
        String songName,
        Integer artistPhoto,
        Integer artistSong
) implements Serializable {
}
