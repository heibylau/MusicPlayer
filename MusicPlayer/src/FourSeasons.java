import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FourSeasons extends JDialog {

	private final JPanel contentPanel = new JPanel();
	//files
	private static final String SPRING1 = "music/Spring_1st_movement.wav";
	private static final String SPRING2 = "music/Spring_2nd_movement.wav";
	private static final String SPRING3 = "music/Spring_3rd_movement.wav";
	private static final String SUMMER1 = "music/Summer_1st_movement.wav";
	private static final String SUMMER2 = "music/Summer_2nd_movement.wav";
	private static final String SUMMER3 = "music/Summer_3rd_movement.wav";
	private static final String AUTUMN1 = "music/Autumn_1st_movement.wav";
	private static final String AUTUMN2 = "music/Autumn_2nd_movement.wav";
	private static final String AUTUMN3 = "music/Autumn_3rd_movement.wav";
	private static final String WINTER1 = "music/Winter_1st_movement.wav";
	private static final String WINTER2 = "music/Winter_2nd_movement.wav";
	private static final String WINTER3 = "music/Winter_3rd_movement.wav";

	//button
	private JButton btnPlay;
	
//	static ArrayList<String> playlist = new ArrayList<> ();
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
	}
	
	public static void addFile() {
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
	
//	public static void addMusic() {
//		for (String name: fileName) {
//			try {
//				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(name));
//				playlist.add(audio);
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		}
//		
//	}
	
	protected void btnPlay_mouseClicked(MouseEvent arg0) {
		playMusic();
	}
	
	public static Clip toMP3(String location) {
		try {
			File music = new File(location);
			if (music.exists()) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(music);
				Clip musicClip = AudioSystem.getClip();
				musicClip.open(audio);
				musicClip.start();
				return musicClip;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void playMusic () {
		try {
			for (String fileName : playlist) {
				System.out.println(fileName);
				Clip currentClip = toMP3(fileName);
				while(currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
					
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
