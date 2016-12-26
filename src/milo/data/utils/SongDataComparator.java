package milo.data.utils;

import milo.data.SongData;

import java.util.Comparator;

/**
 * Created by Ebolo on 26/12/2016.
 */
public class SongDataComparator implements Comparator<SongData> {
    @Override
    public int compare(SongData o1, SongData o2) {
        if (o1.getTitle().equals(" ")) {
            return Integer.MAX_VALUE;
        } else if (o2.getTitle().equals(" ")) {
            return Integer.MIN_VALUE;
        } else {
            if (o1.getTitle().compareToIgnoreCase(o2.getTitle()) != 0)
                return (o1.getTitle().compareToIgnoreCase(o2.getTitle()));
            else
                return (o1.getArtist().compareToIgnoreCase(o2.getArtist()));
        }
    }
}
