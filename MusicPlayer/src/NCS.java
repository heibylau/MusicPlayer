import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NCS extends JFrame{
	private final JPanel contentPanel = new JPanel();
	private boolean isPaused = false;
    private boolean isLooped = false;
    private boolean isResumed = false;
    private boolean previousCalled = false;
    private boolean nextCalled = false;
    private long clipPosition = 0;
    private String fileName = "";
    private String musicName = "";
    Thread playThread;
    
	//files
	MusicTrack Hope = new MusicTrack("music/NCS/Hope.wav", "Hope - Tobu");
	MusicTrack Infectious = new MusicTrack("music/NCS/Infectious.wav", "Infectious - Tobu");
	MusicTrack Cloud9 = new MusicTrack("music/NCS/Cloud9.wav", "Cloud 9 - Itro & Tobu");

	//button
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnResume;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLoop;
	
	
	//label
	private JLabel lblTitle;
	private JLabel lblImage;
	
	//clip
	private Clip currentClip;
	
	//LinkedList
	CircularLinkedList playlist = new CircularLinkedList();
	CircularLinkedList descriptionList = new CircularLinkedList();

	public NCS() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.WHITE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//JButton
		ImageIcon play = new ImageIcon("graphics/PlayResumeButton.png");
		btnPlay = new JButton(play);
		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPlay_mouseClicked(e);
			}
		});
		btnPlay.setBounds(175, 460, 50, 50);
		btnPlay.setBackground(Color.WHITE);
		btnPlay.setBorderPainted(false);
		contentPanel.add(btnPlay);
		
		ImageIcon pause = new ImageIcon("graphics/PauseButton.png");
		btnPause = new JButton(pause);
		btnPause.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPause_mouseClicked(e);
			}
		});
		btnPause.setBounds(190, 460, 20, 50);
		btnPause.setBackground(Color.WHITE);
		btnPause.setBorderPainted(false);
		btnPause.setVisible(false);
		contentPanel.add(btnPause);
		
		ImageIcon resume = new ImageIcon("graphics/PlayResumeButton.png");
		btnResume = new JButton(resume);
		btnResume.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnResume_mouseClicked(e);
			}
		});
		btnResume.setBounds(175, 460, 50, 50);
		btnResume.setBackground(Color.WHITE);
		btnResume.setBorderPainted(false);
		btnResume.setVisible(false);
		contentPanel.add(btnResume);
		
		ImageIcon previous = new ImageIcon("graphics/PreviousButton.png");
		btnPrevious = new JButton(previous);
		btnPrevious.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPrevious_mouseClicked(e);
			}
		});
		btnPrevious.setBounds(50, 460, 50, 50);
		btnPrevious.setBackground(Color.WHITE);
		btnPrevious.setBorderPainted(false);
		contentPanel.add(btnPrevious);
		
		ImageIcon next = new ImageIcon("graphics/NextButton.png");
		btnNext = new JButton(next);
		btnNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnNext_mouseClicked(e);
			}
		});
		btnNext.setBounds(300, 460, 50, 50);
		btnNext.setBackground(Color.WHITE);
		btnNext.setBorderPainted(false);
		contentPanel.add(btnNext);
		
		btnLoop = new JButton("Loop");
		btnLoop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnLoop_mouseClicked(e);
			}
		});
		btnLoop.setBounds(250, 400, 89, 23);
		contentPanel.add(btnLoop);
		
		//label
		lblTitle = new JLabel("");
		lblTitle.setBounds(50, 380, 300, 50);
		contentPanel.add(lblTitle);
		
		ImageIcon image = new ImageIcon();
		lblImage = new JLabel(image);
		lblImage.setBounds(50, 50, 300, 300);
		contentPanel.add(lblImage);
		
		
		//add files
		playlist.add(Hope.getFileName());
		playlist.add(Infectious.getFileName());

		
		//add descriptions
		descriptionList.add(Hope.getDescription());
		descriptionList.add(Infectious.getDescription());


		//clip
		currentClip = null;


	}

	protected void btnPlay_mouseClicked(MouseEvent arg0) {
		playMusic();
		btnPlay.setVisible(false);
		btnPause.setVisible(true);
		 
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
                    descriptionList.loop();
            		isLooped = true;
            		System.out.println("looping");
            	} else {
            		playlist.unLoop();
            		descriptionList.unLoop();
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

        playThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
                    while (!isPaused && playlist.hasNext()) {
                    	if (fileName == "") {
                    		fileName = playlist.getHead();
                    		musicName = descriptionList.getHead();
                        	System.out.println(musicName);
                        	lblTitle.setText(musicName);
                            currentClip = toWAV(fileName);
                            currentClip.start();
                            getImage();
                    	} else {
                    		System.out.println(musicName);
                    		lblTitle.setText(musicName);
                    		currentClip = toWAV(fileName);
                            currentClip.start();
                            getImage();
                    	}
                    	
                        while (currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                        	getImage();
                        	if(isPaused) {
                                if (currentClip != null && currentClip.isRunning()) {
                                    currentClip.stop();
                                }
                                clipPosition = currentClip.getMicrosecondPosition();
                                isPaused = false;
                        	}
                        	if (isResumed) {
                        		currentClip.setMicrosecondPosition(clipPosition);
                        		currentClip.start();
                        		isResumed = false;
                        	}
                        	if (nextCalled) {
                        		if (currentClip != null && currentClip.isRunning()) {
                                    currentClip.stop();
                                }
                                if (playlist.hasNext()) {
                                    clipPosition = 0;
                                    fileName = (String) playlist.next();
                                    musicName = (String) descriptionList.next();
                                    System.out.println(musicName);
                                    lblTitle.setText(musicName);
                                    currentClip = toWAV(fileName);
                                    currentClip.start();
                                    nextCalled = false;
                                }
                        	}
                        	if (previousCalled) {
                        		if (currentClip != null && currentClip.isRunning()) {
                                    currentClip.stop();
                                }
                        		if (playlist.hasPrevious()) {
                                    clipPosition = 0;
                                    fileName = (String) playlist.previous();
                                    musicName = (String) descriptionList.previous();
                                    System.out.println(musicName);
                                    lblTitle.setText(musicName);
                                    currentClip = toWAV(fileName);
                                    currentClip.start();
                                    previousCalled = false;
                                }
                        	}
                            try {
                                Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                        }
                        if (currentClip.getMicrosecondLength() == currentClip.getMicrosecondPosition()) {
                    		fileName = (String) playlist.next();
                    		musicName = (String) descriptionList.next();
                        }

                    }
            	} catch (Exception e) {
            		System.out.println("no music playing");
            	}
            }
        });
        playThread.start();

    }
	
	public void getImage() {
		ImageIcon hope = new ImageIcon("graphics/Hope.jpg");
		ImageIcon infectious = new ImageIcon("graphics/Infectious.jpg");
		if (musicName == "Hope - Tobu") {
			lblImage.setIcon(hope);
		}
		if (musicName == "Infectious - Tobu") {
			lblImage.setIcon(infectious);
		}
	}

    public void pause() {
        isPaused = true;
    }

	public void resume() {
		isResumed = true; 
	}
	
	public void previous(){
		previousCalled = true;
    }

    public void next(){
    	nextCalled = true;
    }
}