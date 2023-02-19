package com.github.rsoi.service;

import com.github.rsoi.domain.Song;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistGenerateCombineMonths implements PlaylistGenerate {
    @Override
    public Map<Song, List<LocalDate>> generatePlaylist(Map<Song, List<LocalDate>> history) {
        var thisMonth = new PlaylistGenerateThisMonth().generatePlaylist(history);
        var prevMonth = new PlaylistGeneratePrevMonth().generatePlaylist(history);

        Map<Song, List<LocalDate>> combMonths = new HashMap<>();

        int i=0;
        for(var song: history.keySet()){
            if(!combMonths.containsKey(song) && !thisMonth.containsKey(song) && !prevMonth.containsKey(song)){
                combMonths.put(song, history.get(song));
                i++;
            }

            if(i >= 3)
                break;
        }
        Song max = null;
        int count = 0;
        for(var song: thisMonth.keySet()){
            if(count < thisMonth.get(song).size()){
                count = thisMonth.get(song).size();
                max = song;
            }
        }
        if(max != null)
            combMonths.put(max, thisMonth.get(max));

        max = null;
        count = 0;
        for(var song: prevMonth.keySet()){
            if(count < prevMonth.get(song).size()){
                count = prevMonth.get(song).size();
                max = song;
            }
        }
        if(max != null)
            combMonths.put(max, prevMonth.get(max));

        return combMonths;
    }
}
