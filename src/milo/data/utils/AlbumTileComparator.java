package milo.data.utils;

import milo.gui.custom.AlbumTile;

import java.util.Comparator;

/**
 * Class name:  AlbumTileComparator
 * Description: This class is the comparator for comparing album data
 */
public class AlbumTileComparator implements Comparator<AlbumTile> {

    @Override
    public int compare(AlbumTile o1, AlbumTile o2) {
        String o1Key = o1.getAlbumData().getAlbumTitle() + o1.getAlbumData().getAlbumArtist();
        String o2Key = o2.getAlbumData().getAlbumTitle() + o2.getAlbumData().getAlbumArtist();
        return o1Key.compareToIgnoreCase(o2Key);
    }
}
