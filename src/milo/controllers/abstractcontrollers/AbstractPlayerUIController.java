package milo.controllers.abstractcontrollers;

import milo.data.SongData;

import java.util.List;

/**
 * Class name:  AbstractPlayerUIController
 * Description: This is an abstract class serves as a template for a the player controller. A player controller would
 *              control all things on the player bar, such as album art, seek bar, buttons...
 */

public abstract class AbstractPlayerUIController extends AbstractUIController {
    protected List<SongData> currentPlaylist;
    protected List<SongData> previousPlaylist;

    /**
     * Function name:   playSong
     * Usage:   this function would play the music and do all the things need to do to play the music
     *          including preparation for the next track, time tracking, artwork setup
     * @param songData the data of the song to be played
     */
    public abstract void playSong(SongData songData);

    /**
     * Function name:   pausePlaying
     * Usage:   this function would play the music and do all the things need to do to pause the music
     *          including stopping any thread relevant to the play, pausing the music
     */
    public abstract void pausePlaying();

    /**
     * Function name:   resumePlaying
     * Usage:   this function would resume the music being paused and restore the state like playSong function
     */
    public abstract void resumePlaying();

    /**
     * Function name:   playNextSong
     * Usage:   this function would play the next track on call
     */
    public abstract void playNextSong();

    /**
     * Function name:   playPreviousSong
     * Usage:   this function would play the previous track on call
     */
    public abstract void playPreviousSong();

    public List<SongData> getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(List<SongData> currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public List<SongData> getPreviousPlaylist() {
        return previousPlaylist;
    }

    public void setPreviousPlaylist(List<SongData> previousPlaylist) {
        this.previousPlaylist = previousPlaylist;
    }
}
