import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NCS extends JFrame{
	private final JPanel contentPanel = new JPanel();
    private boolean isPaused = false;
    private boolean isLooped = false;
    
    //files
    private final String HOPE = "music/Hope.wav";
    private final String INFECTIOUS = "music/Infectious.wav";
    
    //button
  	private JButton btnPlay;
  	private JButton btnPause;
  	private JButton btnResume;
  	private JButton btnPrevious;
  	private JButton btnNext;
  	private JButton btnLoop;
  	
	//clip
	private Clip currentClip;
	
	//LinkedList
	CircularLinkedList playlist = new CircularLinkedList();
	
	public NCS() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//JButton
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
		btnResume.setBounds(106, 170, 89, 23);
		btnResume.setVisible(false);
		contentPanel.add(btnResume);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPrevious_mouseClicked(e);
			}
		});
		btnPrevious.setBounds(106, 230, 89, 23);
		contentPanel.add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnNext_mouseClicked(e);
			}
		});
		btnNext.setBounds(106, 270, 89, 23);
		contentPanel.add(btnNext);
		
		btnLoop = new JButton("Loop");
		btnLoop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnLoop_mouseClicked(e);
			}
		});
		btnLoop.setBounds(106, 320, 89, 23);
		contentPanel.add(btnLoop);
		
		
		//add files
		playlist.addMusic(HOPE);
		playlist.addMusic(INFECTIOUS);
		
		currentClip = null;


	}
	
	protected void btnPlay_mouseClicked(MouseEvent arg0) {
		playMusic();
		btnPlay.setVisible(false);
		btnResume.setVisible(true);
		 
	}
	
	protected void btnPause_mouseClicked(MouseEvent arg0) {
		pause();
		btnPause.setVisible(false);
		btnResume.setVisible(true);
	}
	
	protected void btnResume_mouseClicked(MouseEvent arg0) {
		resume();
		btnResume.setVisible(false);
		btnPause.setVisible(true);
		 
	}
	
	protected void btnPrevious_mouseClicked(MouseEvent arg0) {
		previous();
		 
	}
	
	protected void btnNext_mouseClicked(MouseEvent arg0) {
		next();
		 
	}
	
	protected void btnLoop_mouseClicked(MouseEvent arg0) {
		Thread loopThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	if (!isLooped) {
                    playlist.loop(); 
            		isLooped = true;
            		System.out.println("looping");
            	} else {
            		playlist.unLoop();
            		isLooped = false;
            		System.out.println("unlooped");
            	}

            }
        });
        loopThread.start();
	}

	
	public static Clip toWAV(String location) {
		try {
			File music = new File(location);
			if (music.exists()) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(music);
				Clip musicClip = AudioSystem.getClip();
				musicClip.open(audio);
				return musicClip;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
	
    public void playMusic() {
        isPaused = false;

        Thread playThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
                    while (!isPaused && playlist.hasNext()) {
                    	String fileName = (String) playlist.next();
                    	System.out.println(fileName);
                        currentClip = toWAV(fileName);
                        currentClip.start();
                        while (currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                            try {
                                Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                        }
                    }
            	} catch (Exception e) {
            		System.out.println("no music playing");
            	}
            }
        });
        playThread.start();
    }
	

    public void pause() {
        isPaused = true;
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

	public void resume() {
	    if (currentClip != null) {
	        currentClip.start();
	    }

	}
	
	public void previous() {
        pause();
		if (playlist.hasPrevious()) {
            currentClip = toWAV((String) playlist.previous());
		}
        resume();

    }

    public void next() {
        pause();
		if (playlist.hasNext()) {
            currentClip = toWAV((String) playlist.next());
		}
		resume();
    }
}