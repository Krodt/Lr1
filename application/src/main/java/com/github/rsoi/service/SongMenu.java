package com.github.rsoi.service;

import com.github.rsoi.domain.Song;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SongMenu {
    private Map<Song, List<LocalDate>> history;

    public SongMenu(){
        history = new HashMap<>();
    }

    public void showMenu(){
        Scanner sc = new Scanner(System.in);
        boolean menuTrue = true;
        while(menuTrue){
            System.out.println("Playlist generator!");
            System.out.println("________________________________________________________________");
            System.out.println("1. Add a new song to the listen history");
            System.out.println("2. Delete a song from the listen history");
            System.out.println("3. View all songs from the listen history");
            System.out.println("4. Show playlist with most popular songs this month");
            System.out.println("5. Show playlist with most popular songs previous month");
            System.out.println("6. Show playlist with songs from other playlists adding new ones");
            System.out.println("7. Close");
            System.out.println("________________________________________________________________");

            String menuChoice;
            menuChoice = sc.nextLine();
            try{
                int menuChoiceValue = Integer.parseInt(menuChoice);
                if (menuChoiceValue < 1 || menuChoiceValue > 7){
                    throw new NumberFormatException();
                }

                switch(menuChoiceValue){
                    case 1:{
                        addSong();
                        break;
                    }
                    case 2:{
                        deleteSong();
                        break;
                    }
                    case 3:{
                        printListInfo(history);
                        break;
                    }
                    case 4:{
                        printListInfo(new PlaylistGenerateThisMonth().generatePlaylist(history));
                        break;
                    }
                    case 5:{
                        printListInfo(new PlaylistGeneratePrevMonth().generatePlaylist(history));
                        break;
                    }
                    case 6:{
                        printListInfo(new PlaylistGenerateCombineMonths().generatePlaylist(history));
                        break;
                    }
                    case 7:{
                        menuTrue = false;
                        break;
                    }
                }

            }catch (NumberFormatException e){
                System.out.println("Wrong choice! No such menu variant!");
            }

        }
    }

    public void addSong(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Song name:");
        String name = sc.nextLine();
        System.out.println("Song author:");
        String author = sc.nextLine();
        Song song = new Song(name, author);

        if(history.containsKey(song)){
            System.out.println("This song is already in the history!");
        }
        else{
            List<LocalDate> list = new ArrayList<>();

            boolean isFullHistory = false;
            do{
                System.out.println("Input date in format 00.00.0000");
                String dateString = sc.nextLine();

                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                try {
                    LocalDate date = LocalDate.parse(dateString, dateFormat);
                    list.add(date);
                }
                catch (DateTimeParseException e){
                    System.out.println("Wrong input!");
                }

                System.out.println("Do you want to continue input listen dates? (print 'y' to continue / print anything to finish)");
                dateString = sc.nextLine();
                if(dateString.charAt(0) == 'y')
                    isFullHistory = false;
                if (dateString.charAt(0) != 'y') {
                    isFullHistory = true;
                    System.out.println("Done!");
                }
            }while (!isFullHistory);

            history.put(song, list);
        }
    }

    public void deleteSong(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Input song name to delete");
        String name = sc.nextLine();
        for(var sound: history.keySet()){
            if(sound.getSongName().equals(name)){
                history.remove(sound);
                break;
            }
        }
    }

    private void printSongInfo(Song sound, List<LocalDate> history){
        System.out.print(sound.getSongName() + " - " + sound.getSongAuthor() + "( ");

        for(var date: history){
            System.out.print(date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear() + " / ");
        }

        System.out.println(")");
    }


    private void printListInfo(Map<Song, List<LocalDate>> list){
        Map<Song, List<LocalDate>> alreadyPrinted = new HashMap<>();

        if(list.size() == 0){
            System.out.println("Songlist is empty!");
            return;
        }

        for(int i=0; i<list.size();i++){
            int max = 0;
            Song maxSong = null;
            for(var song: list.keySet()){

                if(max < list.get(song).size() && !alreadyPrinted.containsKey(song)){
                    max = list.get(song).size();
                    maxSong = song;
                }
            }

            System.out.print((i+1) + ". ");
            printSongInfo(maxSong, list.get(maxSong));
            alreadyPrinted.put(maxSong, list.get(maxSong));
        }
    }


}
