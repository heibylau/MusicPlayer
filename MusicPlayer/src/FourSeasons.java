import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FourSeasons extends JDialog {

	private final JPanel contentPanel = new JPanel();
	//files
	private static final String SPRING1 = "music/Spring_1st_movement.mp3";
	private static final String SPRING2 = "music/Spring_2nd_movement.mp3";
	private static final String SPRING3 = "music/Spring_3rd_movement.mp3";
	private static final String SUMMER1 = "music/Summer_1st_movement.mp3";
	private static final String SUMMER2 = "music/Summer_2nd_movement.mp3";
	private static final String SUMMER3 = "music/Summer_3rd_movement.mp3";
	private static final String AUTUMN1 = "music/Autumn_1st_movement.mp3";
	private static final String AUTUMN2 = "music/Autumn_2nd_movement.mp3";
	private static final String AUTUMN3 = "music/Autumn_3rd_movement.mp3";
	private static final String WINTER1 = "music/Winter_1st_movement.mp3";
	private static final String WINTER2 = "music/Winter_2nd_movement.mp3";
	private static final String WINTER3 = "music/Winter_3rd_movement.mp3";
	
	static ArrayList<String> fileName = new ArrayList<> ();
	static LinkedList<AudioInputStream> playlist = new LinkedList();

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
	}
	
	public static void addFileName() {
		fileName.add(SPRING1);
		fileName.add(SPRING2);
		fileName.add(SPRING3);
		fileName.add(SUMMER1);
		fileName.add(SUMMER2);
		fileName.add(SUMMER3);
		fileName.add(AUTUMN1);
		fileName.add(AUTUMN2);
		fileName.add(AUTUMN3);
		fileName.add(WINTER1);
		fileName.add(WINTER2);
		fileName.add(WINTER3);
	}
	
	public static void addMusic() {
//		InputStream music;
		for (String name: fileName) {
			try {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(name));
				playlist.add(audio);
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public static void playMusic() {
		
	}

}
