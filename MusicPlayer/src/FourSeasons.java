import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FourSeasons extends JFrame{

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
	MusicTrack Spring1 = new MusicTrack("MusicPlayer/music/FourSeasons/Spring_1st_movement.wav", "Spring-1st Movement - Antonio Vivaldi");
	MusicTrack Spring2 = new MusicTrack("MusicPlayer/music/FourSeasons/Spring_2nd_movement.wav", "Spring-2nd Movement - Antonio Vivaldi");
	MusicTrack Spring3 = new MusicTrack("MusicPlayer/music/FourSeasons/Spring_3rd_movement.wav", "Spring-3rd Movement - Antonio Vivaldi");
	MusicTrack Summer1 = new MusicTrack("MusicPlayer/music/FourSeasons/Summer_1st_movement.wav", "Summer-1st Movement - Antonio Vivaldi");
	MusicTrack Summer2 = new MusicTrack("MusicPlayer/music/FourSeasons/Summer_2nd_movement.wav", "Summer-2nd Movement - Antonio Vivaldi");
	MusicTrack Summer3 = new MusicTrack("MusicPlayer/music/FourSeasons/Summer_3rd_movement.wav", "Summer-3rd Movement - Antonio Vivaldi");
	MusicTrack Autumn1 = new MusicTrack("MusicPlayer/music/FourSeasons/Autumn_1st_movement.wav", "Autumn-1st Movement - Antonio Vivaldi");
	MusicTrack Autumn2 = new MusicTrack("MusicPlayer/music/FourSeasons/Autumn_2nd_movement.wav", "Autumn-2nd Movement - Antonio Vivaldi");
	MusicTrack Autumn3 = new MusicTrack("MusicPlayer/music/FourSeasons/Autumn_3rd_movement.wav", "Autumn-3rd Movement - Antonio Vivaldi");
	MusicTrack Winter1 = new MusicTrack("MusicPlayer/music/FourSeasons/Winter_1st_movement.wav", "Winter-1st Movement - Antonio Vivaldi");
	MusicTrack Winter2 = new MusicTrack("MusicPlayer/music/FourSeasons/Winter_2nd_movement.wav", "Winter-2nd Movement - Antonio Vivaldi");
	MusicTrack Winter3 = new MusicTrack("MusicPlayer/music/FourSeasons/Winter_3rd_movement.wav", "Winter-3rd Movement - Antonio Vivaldi");

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
	ImageIcon spring = new ImageIcon("MusicPlayer/graphics/FourSeasons/Spring.png");
	ImageIcon summer = new ImageIcon("MusicPlayer/graphics/FourSeasons/Summer.png");
	ImageIcon autumn = new ImageIcon("MusicPlayer/graphics/FourSeasons/Autumn.png");
	ImageIcon winter = new ImageIcon("MusicPlayer/graphics/FourSeasons/Winter.png");

	
	public FourSeasons() {
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
		playlist.add(Spring1.getFileName());
		playlist.add(Spring2.getFileName());
		playlist.add(Spring3.getFileName());
		playlist.add(Summer1.getFileName());
		playlist.add(Summer2.getFileName());
		playlist.add(Summer3.getFileName());
		playlist.add(Autumn1.getFileName());
		playlist.add(Autumn2.getFileName());
		playlist.add(Autumn3.getFileName());
		playlist.add(Winter1.getFileName());
		playlist.add(Winter2.getFileName());
		playlist.add(Winter3.getFileName());
		
		//add descriptions
		descriptionList.add(Spring1.getDescription());
		descriptionList.add(Spring2.getDescription());
		descriptionList.add(Spring3.getDescription());
		descriptionList.add(Summer1.getDescription());
		descriptionList.add(Summer2.getDescription());
		descriptionList.add(Summer3.getDescription());
		descriptionList.add(Autumn1.getDescription());
		descriptionList.add(Autumn2.getDescription());
		descriptionList.add(Autumn3.getDescription());
		descriptionList.add(Winter1.getDescription());
		descriptionList.add(Winter2.getDescription());
		descriptionList.add(Winter3.getDescription());
		
		//add images
		imageList.add(spring);
		imageList.add(spring);
		imageList.add(spring);
		imageList.add(summer);
		imageList.add(summer);
		imageList.add(summer);
		imageList.add(autumn);
		imageList.add(autumn);
		imageList.add(autumn);
		imageList.add(winter);
		imageList.add(winter);
		imageList.add(winter);

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
                            getImage();
                    	} else {
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
	
	public void getImage() {
		if (musicName == "Spring-1st Movement" || musicName == "Spring-2nd Movement" || musicName == "Spring-3rd Movement") {
			lblImage.setIcon(spring);
		}
		if (musicName == "Summer-1st Movement" || musicName == "Summer-2nd Movement" || musicName == "Summer-3rd Movement") {
			lblImage.setIcon(summer);
		}
		if (musicName == "Autumn-1st Movement" || musicName == "Autumn-2nd Movement" || musicName == "Autumn-3rd Movement") {
			lblImage.setIcon(autumn);
		}
		if (musicName == "Winter-1st Movement" || musicName == "Winter-2nd Movement" || musicName == "Winter-3rd Movement") {
			lblImage.setIcon(winter);
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
