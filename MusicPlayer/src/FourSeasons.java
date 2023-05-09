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
    private boolean previousCalled = false;
    private boolean nextCalled = false;
    private long clipPosition = 0;
    private String fileName = "";
    private String musicName = "";
    Thread playThread;
    
	//files
	MusicTrack Spring1 = new MusicTrack("music/FourSeasons/Spring_1st_movement.wav", "Spring-1st Movement");
	MusicTrack Spring2 = new MusicTrack("music/FourSeasons/Spring_2nd_movement.wav", "Spring-2nd Movement");
	MusicTrack Spring3 = new MusicTrack("music/FourSeasons/Spring_3rd_movement.wav", "Spring-3rd Movement");
	MusicTrack Summer1 = new MusicTrack("music/FourSeasons/Summer_1st_movement.wav", "Summer-1st Movement");
	MusicTrack Summer2 = new MusicTrack("music/FourSeasons/Summer_2nd_movement.wav", "Summer-2nd Movement");
	MusicTrack Summer3 = new MusicTrack("music/FourSeasons/Summer_3rd_movement.wav", "Summer-3rd Movement");
	MusicTrack Autumn1 = new MusicTrack("music/FourSeasons/Autumn_1st_movement.wav", "Autumn-1st Movement");
	MusicTrack Autumn2 = new MusicTrack("music/FourSeasons/Autumn_2nd_movement.wav", "Autumn-2nd Movement");
	MusicTrack Autumn3 = new MusicTrack("music/FourSeasons/Autumn_3rd_movement.wav", "Autumn-3rd Movement");
	MusicTrack Winter1 = new MusicTrack("music/FourSeasons/Winter_1st_movement.wav", "Winter-1st Movement");
	MusicTrack Winter2 = new MusicTrack("music/FourSeasons/Winter_2nd_movement.wav", "Winter-2nd Movement");
	MusicTrack Winter3 = new MusicTrack("music/FourSeasons/Winter_3rd_movement.wav", "Winter-3rd Movement");

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
		ImageIcon spring = new ImageIcon("graphics/Spring.png");
		ImageIcon summer = new ImageIcon("graphics/Summer.png");
		ImageIcon autumn = new ImageIcon("graphics/Autumn.png");
		ImageIcon winter = new ImageIcon("graphics/Winter.png");
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
