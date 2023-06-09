import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PlayerFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnFourSeasons;
	private JButton btnPop;
	private JButton btnNCS;
	private JButton btnTango;
	private JLabel defaultBackground;
	private JLabel lblFourSeasons;
	private JLabel lblPop;
	private JLabel lblNCS;
	private JLabel lblTango;

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

		
		//Four Seasons playlist button
		ImageIcon fs = new ImageIcon("graphics/FourSeasons/Four_seasons_playlist_cover.png");
		btnFourSeasons = new JButton(fs);
		btnFourSeasons.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				btnFourSeasons_mouseClicked(arg0);
			}
		});
		btnFourSeasons.setBounds(75, 150, 100, 100);
		contentPane.add(btnFourSeasons);
		
		//Four Seasons playlist description
		lblFourSeasons = new JLabel("Four Seasons");
		lblFourSeasons.setBounds(82, 255, 200, 20);
		contentPane.add(lblFourSeasons);
		
		//Pop playlist button
		ImageIcon pop = new ImageIcon("graphics/Pop/Pop_playlist_cover.jpeg");
		btnPop = new JButton(pop);
		btnPop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnPop_mouseClicked(e);
			}
		});
		btnPop.setBounds(225, 150, 100, 100);
		btnPop.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(btnPop);
		
		//Pop playlist description
		lblPop = new JLabel("Pop");
		lblPop.setBounds(265, 255, 100, 20);
		contentPane.add(lblPop);

		//NCS playlist button
		ImageIcon ncs = new ImageIcon("graphics/NCS/NCS_playlist_cover.png");
		btnNCS = new JButton(ncs);
		btnNCS.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnNCS_mouseClicked(e);
			}
		});
		btnNCS.setBounds(225, 325, 100, 100);
		contentPane.add(btnNCS);
		
		//NCS playlist description
		lblNCS = new JLabel("NCS");
		lblNCS.setBounds(265, 430, 100, 20);
		contentPane.add(lblNCS);
		
		//Tango button
		ImageIcon tango = new ImageIcon("graphics/Tango/Tango_playlist_cover.jpg");
		btnTango = new JButton(tango);
		btnTango.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btnTango_mouseClicked(e);
			}
		});
		btnTango.setBounds(75, 325, 100, 100);
		btnTango.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPane.add(btnTango);
		
		//Tango playlist description
		lblTango = new JLabel("Tango");
		lblTango.setBounds(105, 430, 100, 20);
		contentPane.add(lblTango);

		//Background
		ImageIcon def = new ImageIcon("background/Default_background.png");
		defaultBackground = new JLabel(def);
		defaultBackground.setBounds(0, 0, 400, 600);
		contentPane.add(defaultBackground);
		
	}
	
	protected void btnFourSeasons_mouseClicked(MouseEvent arg0) {
		FourSeasons fourSeasons = new FourSeasons();
		fourSeasons.setLocationRelativeTo(this);
		fourSeasons.setVisible(true);
		this.repaint();
	}
	
	protected void btnPop_mouseClicked(MouseEvent arg0) {
		Pop pop = new Pop();
		pop.setLocationRelativeTo(this);
		pop.setVisible(true);
		this.repaint();
	}
	
	protected void btnNCS_mouseClicked(MouseEvent arg0) {
		NCS ncs = new NCS();
		ncs.setLocationRelativeTo(this);
		ncs.setVisible(true);
		this.repaint();
	}
	
	protected void btnTango_mouseClicked(MouseEvent arg0) {
		Tango tango = new Tango();
		tango.setLocationRelativeTo(this);
		tango.setVisible(true);
		this.repaint();
	}

}
