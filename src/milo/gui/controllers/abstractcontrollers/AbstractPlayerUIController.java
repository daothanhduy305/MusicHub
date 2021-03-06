package milo.gui.controllers.abstractcontrollers;

import javafx.scene.media.MediaPlayer;
import milo.data.SongData;
import milo.gui.utils.Constants.VIEWS_ID;

import java.util.List;

/**
 * Class name:  AbstractPlayerUIController
 * Description: This is an abstract class serves as a template for a the player controller. A player controller would
 *              control all things on the player bar, such as album art, seek bar, buttons...
 */

public abstract class AbstractPlayerUIController extends AbstractUIController {
    protected List<SongData> currentPlaylist;
    protected List<SongData> previousPlaylist;
    protected MediaPlayer player;
    protected SongData currentPlayingSong;
    protected volatile boolean isPlaying = false;
    protected VIEWS_ID viewId;

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
     * Function name:   stopPlaying
     * Usage:   this function would stop the music being played and all the relevant threads
     */
    public abstract void stopPlaying();

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

    public MediaPlayer getPlayer() {
        return player;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public SongData getCurrentPlayingSong() {
        return currentPlayingSong;
    }

    public void setViewId(VIEWS_ID viewId) {
        this.viewId = viewId;
    }

    public VIEWS_ID getViewId() {
        return viewId;
    }
}
