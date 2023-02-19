package com.github.rsoi.service;

import com.github.rsoi.domain.Song;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PlaylistGenerate {
    public Map<Song, List<LocalDate>>  generatePlaylist(Map<Song, List<LocalDate>> history);
}
