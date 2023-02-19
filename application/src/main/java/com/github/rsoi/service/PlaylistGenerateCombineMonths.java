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
        for(var sound: history.keySet()){
            if(!combMonths.containsKey(sound) && !thisMonth.containsKey(sound) && prevMonth.containsKey(sound)){
                combMonths.put(sound, history.get(sound));
                i++;
            }

            if(i >= 3)
                break;
        }
        Song max = null;
        int count = 0;
        for(var sound: thisMonth.keySet()){
            if(count < thisMonth.get(sound).size()){
                count = thisMonth.get(sound).size();
                max = sound;
            }
        }
        if(max != null)
            combMonths.put(max, thisMonth.get(max));

        max = null;
        count = 0;
        for(var sound: prevMonth.keySet()){
            if(count < prevMonth.get(sound).size()){
                count = prevMonth.get(sound).size();
                max = sound;
            }
        }
        if(max != null)
            combMonths.put(max, prevMonth.get(max));

        return combMonths;
    }
}
