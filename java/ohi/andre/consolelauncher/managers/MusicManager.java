package ohi.andre.consolelauncher.managers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import ohi.andre.consolelauncher.tuils.Storage;
import ohi.andre.consolelauncher.tuils.interfaces.Disposeable;
import ohi.andre.consolelauncher.tuils.interfaces.OnHeadsetStateChangedListener;

public class MusicManager implements OnCompletionListener, Disposeable, OnHeadsetStateChangedListener {

	private ArrayList<String> songs;
	
	private MediaPlayer mp;
	private int currentSongIndex = 0; 
	
	public MusicManager(Activity context) {
		this.mp = new MediaPlayer();
		this.mp.setOnCompletionListener(this);
		
		init(context.getPreferences(0));
	}
	
	public void init(SharedPreferences prefs) {
		boolean randomActive = Storage.readRandom(prefs);

		File f = new File(Storage.readSongPath(prefs));
		try {
			fillSongs(f, randomActive);
		} catch(Exception e) {}
	}
	
	private void fillSongs(File f, boolean shuffle) throws Exception {
		songs = new ArrayList<>();
		if (f.listFiles(new FileExtensionFilter()).length > 0) 
	         for (File file : f.listFiles(new FileExtensionFilter())) 
	        	 songs.add(file.getAbsolutePath());
		
		verifyRandom(shuffle);
	}
	
	private void verifyRandom(boolean random) {
		if(random)
			Collections.shuffle(songs);
		else
			Collections.sort(songs);
	}
	
	private boolean prepareSong(int songIndex){
		if(songs == null || songs.size() == 0) 
			return false;
		
		try {
			mp.reset();
			mp.setDataSource(songs.get(songIndex));
			mp.prepare();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public MusicManager.Status play() throws IllegalStateException{
		if(mp != null) {
			boolean playing;
			if(mp.isPlaying()) {
				mp.pause();
				playing = false;
			}
			else {
				mp.start();
				playing = true;
			}
			return new Status(new File(songs.get(currentSongIndex)).getName(), playing);
		}
		return null;
	}

	public String next() throws IllegalStateException{
		if(currentSongIndex < songs.size() - 1)
			currentSongIndex += 1;
		else 
			currentSongIndex = 0;
		prepareSong(currentSongIndex);
		mp.start();
		return new File(songs.get(currentSongIndex)).getName();
	}

	public String prev() throws IllegalStateException{
		if(currentSongIndex > 0){
			currentSongIndex -= 1;
			prepareSong(currentSongIndex);
			mp.start();
			return new File(songs.get(currentSongIndex)).getName();
		}
		return null;
	}
	
	public String restart() throws IllegalStateException{
		prepareSong(currentSongIndex);
		mp.start();
		return new File(songs.get(currentSongIndex)).getName();
	}

	public boolean initPlayer() {
		return prepareSong(currentSongIndex);
	}
	
	public void stop() throws IllegalStateException{
		mp.stop();
	}
	
	public String trackInfo() {
		int total = mp.getDuration() / 1000;
		int position = mp.getCurrentPosition() / 1000;
		File f = new File(songs.get(currentSongIndex));
		return f.getName() + 
				"\n" + (total / 60) + "." + (total % 60)  + " / " + (position / 60) + "." + (position % 60)  +
				" (" + (100 * position / total) + "%)";
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		next();
	}
	
	@Override
	public void onHeadsetUnplugged() {
		if(mp != null && mp.isPlaying())
			mp.pause();
	}
	
	@Override
	public void dispose() {
		mp.release();
	}
	
	
	public static class Status {
		public String song;
		public boolean playing;
		
		public Status(String s, boolean b) {
			this.song = s;
			this.playing = b;
		}
	}
	
	private class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}

}
