package com.github.rsoi.service;

import com.github.rsoi.domain.Song;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistGeneratePrevMonth implements PlaylistGenerate{
    @Override
    public Map<Song, List<LocalDate>> generatePlaylist(Map<Song, List<LocalDate>> history) {
        var currentMonth = new PlaylistGenerateThisMonth().generatePlaylist(history);

        Map<Song, List<LocalDate>> prevMonth = new HashMap<>();

        for(int i=0; i<3; i++)
        {
            Song maxSong = null;
            int max = 0;
            for(var song: history.keySet()){

                LocalDate now = LocalDate.now();
                int count = 0;

                for(var date: history.get(song)){
                    if(date.getMonth() == now.getMonth() && date.getYear() == now.getYear())
                        count++;
                }

                if(count > max && !prevMonth.containsKey(song) && !currentMonth.containsKey(song)){
                    max = count;
                    maxSong = song;
                }
            }

            if(maxSong != null)
                prevMonth.put(maxSong, history.get(maxSong));
        }

        return prevMonth;
    }
}
