import java.awt.EventQueue;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PlayerFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnFourSeasons;
	private JLabel defaultBackground;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerFrame frame = new PlayerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlayerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		{
		//Four Seasons playlist button
		ImageIcon fs = new ImageIcon("graphics/Four_seasons_playlist_cover.png");
		btnFourSeasons = new JButton(fs);
		btnFourSeasons.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				btnFourSeasons_mouseClicked(arg0);
			}
		});
		btnFourSeasons.setBounds(75, 200, 100, 100);
		contentPane.add(btnFourSeasons);

		//Background button
		}
		
		//Background
		{
		ImageIcon def = new ImageIcon("background/Default_background.png");
		defaultBackground = new JLabel(def);
		defaultBackground.setBounds(0, 0, 400, 600);
		contentPane.add(defaultBackground);
		
		}
		
	}
	
	protected void btnFourSeasons_mouseClicked(MouseEvent arg0) {
		FourSeasons fourSeasons = new FourSeasons();
		fourSeasons.setLocationRelativeTo(this);
		fourSeasons.setModalityType(ModalityType.APPLICATION_MODAL);
		fourSeasons.setVisible(true);
		this.repaint();
	}

}
