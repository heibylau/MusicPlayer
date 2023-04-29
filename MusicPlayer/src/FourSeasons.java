import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FourSeasons extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private static Thread musicThread = null;
    private static boolean paused = false;
    private static boolean stopRequested = false;
	public static long pausedPosition;
	//files
	private static final String SPRING1 = "music/Spring_1st_movement.wav";
	private static final String SPRING2 = "music/Spring_2nd_movement.wav";
	private static final String SPRING3 = "music/Spring_3rd_movement.wav";
	private static final String SUMMER1 = "music/Summer_1st_movement.wav";
	private static final String SUMMER2 = "music/Summer_2nd_movement.wav";
	private static final String SUMMER3 = "music/Summer_3rd_movement.wav";
//	private static final String AUTUMN1 = "music/Autumn_1st_movement.wav";
//	private static final String AUTUMN2 = "music/Autumn_2nd_movement.wav";
//	private static final String AUTUMN3 = "music/Autumn_3rd_movement.wav";
//	private static final String WINTER1 = "music/Winter_1st_movement.wav";
//	private static final String WINTER2 = "music/Winter_2nd_movement.wav";
//	private static final String WINTER3 = "music/Winter_3rd_movement.wav";

	//button
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnResume;
	
	
	//clip
	public static Clip currentClip;
	
//	static ArrayList<String> playlist = new ArrayList<> ();
	// private Node head;
	static LinkedList<String> playlist = new LinkedList();

	/**
	 * Create the dialog.
	 */
	public FourSeasons() {
		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
//		{
//			JPanel buttonPane = new JPanel();
//			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
//			getContentPane().add(buttonPane, BorderLayout.SOUTH);
//			{
//				JButton okButton = new JButton("OK");
//				okButton.setActionCommand("OK");
//				buttonPane.add(okButton);
//				getRootPane().setDefaultButton(okButton);
//			}
//			{
//				JButton cancelButton = new JButton("Cancel");
//				cancelButton.setActionCommand("Cancel");
//				buttonPane.add(cancelButton);
//			}
//		}
		btnPlay = new JButton("Play");
		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPlay_mouseClicked(e);
			}
		});
		btnPlay.setBounds(106, 135, 89, 23);
		contentPanel.add(btnPlay);
		
		btnPause = new JButton("Pause");
		btnPause.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPause_mouseClicked(e);
			}
		});
		btnPause.setBounds(106, 170, 89, 23);
		contentPanel.add(btnPause);
		
		btnResume = new JButton("Resume");
		btnResume.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnResume_mouseClicked(e);
			}
		});
		btnResume.setBounds(106, 200, 89, 23);
		contentPanel.add(btnResume);
		
		
		//add files
		playlist.add(SPRING1);
		playlist.add(SPRING2);
		playlist.add(SPRING3);
		playlist.add(SUMMER1);
		playlist.add(SUMMER2);
		playlist.add(SUMMER3);

//		playlist.add(AUTUMN1);
//		playlist.add(AUTUMN2);
//		playlist.add(AUTUMN3);
//		playlist.add(WINTER1);
//		playlist.add(WINTER2);
//		playlist.add(WINTER3);
	}

	protected void btnPlay_mouseClicked(MouseEvent arg0) {
		playMusic();
		 
	}
	
	protected void btnPause_mouseClicked(MouseEvent arg0) {
		pause();
		 
	}
	
	protected void btnResume_mouseClicked(MouseEvent arg0) {
		resume();
		 
	}

	
	public static Clip toWAV(String location) {
		try {
			File music = new File(location);
			if (music.exists()) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(music);
				Clip musicClip = AudioSystem.getClip();
				musicClip.open(audio);
				musicClip.start();
//				while(musicClip.isRunning()) {
//					Thread.sleep(100);
//				}
				return musicClip;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
	
    public static void playMusic() {
        stopRequested = false;

        Thread playThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Iterator<String> iterator = playlist.iterator();
                while (!stopRequested && iterator.hasNext()) {
                    if (!paused) {
                        String fileName = iterator.next();
                        currentClip = toWAV(fileName);
                        while (currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        playThread.start();
    }
	
//	public static void playMusic() {
//	    stopRequested = false;
//
//	    for (String fileName : playlist) {
//	        if (stopRequested) {
//	            break;
//	        }
//
//	        if (!paused) {
//	            currentClip = toWAV(fileName);
//	            while (currentClip.isRunning()) {
//	                try {
//	                    Thread.sleep(100);
//	                } catch (InterruptedException e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        } else {
//	            try {
//	                Thread.sleep(100);
//	            } catch (InterruptedException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}

    public static void pause() {
        paused = !paused;
        if (paused) {
            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop();
            }
        } else {
            if (currentClip != null) {
                currentClip.start();
            }
        }
    }

    public static void stop() {
        stopRequested = true;
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

	public static void resume() {
	    if (currentClip != null) {
	        currentClip.start();
	    }

	}
	

}
