package milo.data.utils;

import milo.data.AlbumData;

import java.util.Comparator;

/**
 * Class name:  AlbumDataComparator
 * Description: This class is the comparator for comparing album data
 */
public class AlbumDataComparator implements Comparator<AlbumData> {

    @Override
    public int compare(AlbumData o1, AlbumData o2) {
        return o1.getAlbumTitle().compareToIgnoreCase(o2.getAlbumTitle());
    }
}
