import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Pop extends JFrame{
	private final JPanel contentPanel = new JPanel();
	private boolean isPaused = false;
    private boolean isLooped = false;
    private boolean isResumed = false;
    private boolean isNightMode = false;
    private boolean previousCalled = false;
    private boolean nextCalled = false;
    private long clipPosition = 0;
    private String fileName = "";
    private String musicName = "";
    Thread playThread;
    
	//files
	MusicTrack track1 = new MusicTrack("MusicPlayer/music/Pop/Thunder.wav", "Thunder - Imagine Dragons");
	MusicTrack track2 = new MusicTrack("MusicPlayer/music/Pop/Gotye.wav", "Somebody That I Used To Know - Gotye");
	MusicTrack track3 = new MusicTrack("MusicPlayer/music/Pop/Late_night_talking.wav", "Late Night Talking - Harry Styles");
	MusicTrack track4 = new MusicTrack("MusicPlayer/music/Pop/Chasing_cars.wav", "Chasing Cars - Snow Patrol");

	//button
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnResume;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLoop;
	private JButton btnNightMode;
	
	
	//label
	private JLabel lblTitle;
	private JLabel lblImage;
	
	//clip
	private Clip currentClip;
	
	//LinkedList
	CircularLinkedList playlist = new CircularLinkedList();
	CircularLinkedList descriptionList = new CircularLinkedList();
	CircularLinkedList imageList = new CircularLinkedList();

	//Images
	ImageIcon play = new ImageIcon("MusicPlayer/graphics/PlayResumeButton.png");
	ImageIcon pause = new ImageIcon("MusicPlayer/graphics/PauseButton.png");
	ImageIcon resume = new ImageIcon("MusicPlayer/graphics/PlayResumeButton.png");
	ImageIcon previous = new ImageIcon("MusicPlayer/graphics/PreviousButton.png");
	ImageIcon next = new ImageIcon("MusicPlayer/graphics/NextButton.png");
	ImageIcon nightMode = new ImageIcon("MusicPlayer/graphics/NightModeButton.png");
	ImageIcon nightModeOff = new ImageIcon("MusicPlayer/graphics/NightModeOffButton.png");
	ImageIcon loopOn = new ImageIcon("MusicPlayer/graphics/LoopOnButton.png");
	ImageIcon loopOff = new ImageIcon("MusicPlayer/graphics/LoopOffButton.png");
	ImageIcon play_white = new ImageIcon("MusicPlayer/graphics/PlayResumeButton_white.png");
	ImageIcon resume_white = new ImageIcon("MusicPlayer/graphics/PlayResumeButton_white.png");
	ImageIcon pause_white = new ImageIcon("MusicPlayer/graphics/PauseButton_white.png");
	ImageIcon previous_white = new ImageIcon("MusicPlayer/graphics/PreviousButton_white.png");
	ImageIcon next_white = new ImageIcon("MusicPlayer/graphics/NextButton_white.png");
	ImageIcon image1 = new ImageIcon("MusicPlayer/graphics/Pop/Thunder.jpg");
	ImageIcon image2 = new ImageIcon("MusicPlayer/graphics/Pop/Gotye.jpg");
	ImageIcon image3 = new ImageIcon("MusicPlayer/graphics/Pop/Late_night_talking.jpg");
	ImageIcon image4 = new ImageIcon("MusicPlayer/graphics/Pop/Chasing_cars.jpeg");
	
	public Pop() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.WHITE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//JButton
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
		
		btnLoop = new JButton(loopOff);
		btnLoop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnLoop_mouseClicked(e);
			}
		});
		btnLoop.setBounds(300, 380, 50, 50);
		btnLoop.setBackground(Color.WHITE);
		btnLoop.setBorderPainted(false);
		contentPanel.add(btnLoop);
		

		btnNightMode = new JButton(nightMode);
		btnNightMode.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnNightMode_mouseClicked(e);
			}
		});
		btnNightMode.setBounds(0, 0, 50, 50);
		btnNightMode.setBackground(Color.WHITE);
		btnNightMode.setBorderPainted(false);
		contentPanel.add(btnNightMode);
		
		//label
		lblTitle = new JLabel("");
		lblTitle.setBounds(50, 380, 300, 50);
		contentPanel.add(lblTitle);
		
		ImageIcon image = new ImageIcon();
		lblImage = new JLabel(image);
		lblImage.setBounds(50, 50, 300, 300);
		contentPanel.add(lblImage);
		
		
		//add files
		playlist.add(track1.getFileName());
		playlist.add(track2.getFileName());
		playlist.add(track3.getFileName());
		playlist.add(track4.getFileName());

		
		//add descriptions
		descriptionList.add(track1.getDescription());
		descriptionList.add(track2.getDescription());
		descriptionList.add(track3.getDescription());
		descriptionList.add(track4.getDescription());
		
		//add images
		imageList.add(image1);
		imageList.add(image2);
		imageList.add(image3);
		imageList.add(image4);


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
                    imageList.loop();
                    btnLoop.setIcon(loopOn);
            		isLooped = true;
            		System.out.println("looping");
            	} else {
            		playlist.unLoop();
            		descriptionList.unLoop();
            		imageList.unLoop();
            		btnLoop.setIcon(loopOff);
            		isLooped = false;
            		System.out.println("unlooped");
            	}

            }
        });
        loopThread.start();
	}

	protected void btnNightMode_mouseClicked(MouseEvent arg0) {
		
		if(!isNightMode) {
			btnPlay.setIcon(play_white);
			btnPlay.setBackground(Color.BLACK);
			btnResume.setIcon(resume_white);
			btnResume.setBackground(Color.BLACK);
			btnPause.setIcon(pause_white);
			btnPause.setBackground(Color.BLACK);
			btnPrevious.setIcon(previous_white);
			btnPrevious.setBackground(Color.BLACK);
			btnNext.setIcon(next_white);
			btnNext.setBackground(Color.BLACK);
			btnNightMode.setIcon(nightModeOff);
			btnLoop.setBackground(Color.BLACK);
			contentPanel.setBackground(Color.BLACK);
			lblTitle.setForeground(Color.WHITE);
			isNightMode = true;
		} else {
			btnPlay.setIcon(play);
			btnPlay.setBackground(Color.WHITE);
			btnResume.setIcon(resume);
			btnResume.setBackground(Color.WHITE);
			btnPause.setIcon(pause);
			btnPause.setBackground(Color.WHITE);
			btnPrevious.setIcon(previous);
			btnPrevious.setBackground(Color.WHITE);
			btnNext.setIcon(next);
			btnNext.setBackground(Color.WHITE);
			btnNightMode.setIcon(nightMode);
			btnLoop.setBackground(Color.WHITE);
			contentPanel.setBackground(Color.WHITE);
			lblTitle.setForeground(Color.BLACK);
			isNightMode = false;
		}
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
                    		fileName = (String) playlist.getHead();
                    		musicName = (String) descriptionList.getHead();
                    		lblImage.setIcon((Icon) imageList.getHead());
                        	lblTitle.setText(musicName);
                            currentClip = toWAV(fileName);
                            currentClip.start();
                    	} else {
                    		lblTitle.setText(musicName);
                    		currentClip = toWAV(fileName);
                            currentClip.start();
                    	}
                    	
                        while (currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                        	if(isPaused) {
                                if (currentClip != null && currentClip.isRunning()) {
                                    currentClip.stop();
                                }
                                clipPosition = currentClip.getMicrosecondPosition();
                        	}
                        	if (isResumed) {
                        		isPaused = false;
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
                                    lblTitle.setText(musicName);
                                    lblImage.setIcon((Icon) imageList.next());
                                    currentClip = toWAV(fileName);
                                    if(!isPaused) {
                                        currentClip.start();
                                    }
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
                                    lblTitle.setText(musicName);
                                    lblImage.setIcon((Icon) imageList.previous());
                                    currentClip = toWAV(fileName);
                                    if(!isPaused) {
                                        currentClip.start();
                                    }
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
                            lblImage.setIcon((Icon) imageList.next());
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