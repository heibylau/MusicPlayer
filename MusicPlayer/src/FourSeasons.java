import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FourSeasons extends JDialog {
	private final JPanel contentPanel = new JPanel();
	
	public FourSeasons() {
//		addComponentListener(new ComponentAdapter() {
//			public void componentShown(ComponentEvent arg0) {
//				componentShown(arg0);
//			}
//		});
		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
	}
}