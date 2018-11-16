import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Credit extends JFrame {

	private JPanel contentPane;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Credit frame = new Credit();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
	public Credit() {

		setBounds(100, 100, 730, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Design by:");
		label.setForeground(new Color(255, 0, 0));
		label.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 45));
		label.setBounds(28, 22, 257, 86);
		contentPane.add(label);
		
		JLabel lblJengrungTu = new JLabel("Jeng-Rung Tu");
		lblJengrungTu.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
		lblJengrungTu.setBounds(28, 100, 320, 112);
		contentPane.add(lblJengrungTu);
		
		JLabel lblTianxiHe = new JLabel("Tianxi He");
		lblTianxiHe.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
		lblTianxiHe.setBounds(380, 290, 281, 112);
		contentPane.add(lblTianxiHe);
		
		
		ImageIcon cat = new ImageIcon("image/cat.jpg");
		cat.setImage(cat.getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT));
		JLabel pic1 = new JLabel("");
		pic1.setIcon(cat);
		pic1.setBounds(389, 42, 220, 220);
		contentPane.add(pic1);
		

		JLabel pic2 = new JLabel("pic2");
		pic2.setBounds(65, 220, 220, 220);
		ImageIcon dog = new ImageIcon("image/dog.jpg");
		dog.setImage(dog.getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT));
		pic2.setIcon(dog);
		contentPane.add(pic2);
		
		
	}
}
