import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class selectPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public selectPanel() {
		this.setSize(1000, 580);
		setLayout(null);
		
		JLabel cityPic = new JLabel("");
		cityPic.setBounds(468, 13, 520, 554);
		add(cityPic);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(12, 13, 450, 554);
		add(buttonPanel);
		buttonPanel.setLayout(null);
		
		//make 2 back button
		JButton backToCity = new JButton("City");
		backToCity.setBounds(12, 463, 203, 75);
		buttonPanel.add(backToCity);
		backToCity.setOpaque(false);
		backToCity.setForeground(new Color(148, 0, 211));
		backToCity.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		backToCity.setFocusPainted(false);
		backToCity.setBorder(BorderFactory.createRaisedBevelBorder());
		backToCity.setBackground(new Color(250, 250, 210));
		
		JButton bakcToCategory = new JButton("Category");
		bakcToCategory.setBounds(227, 463, 203, 75);
		buttonPanel.add(bakcToCategory);
		bakcToCategory.setOpaque(false);
		bakcToCategory.setForeground(new Color(148, 0, 211));
		bakcToCategory.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		bakcToCategory.setFocusPainted(false);
		bakcToCategory.setBorder(BorderFactory.createRaisedBevelBorder());
		bakcToCategory.setBackground(new Color(250, 250, 210));
		//
		
		
		JButton button = new JButton("City");
		button.setOpaque(false);
		button.setForeground(new Color(199, 21, 133));
		button.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createRaisedBevelBorder());
		button.setBackground(new Color(250, 250, 210));
		button.setBounds(12, 13, 203, 75);
		buttonPanel.add(button);
		
		JButton button_1 = new JButton("City");
		button_1.setOpaque(false);
		button_1.setForeground(new Color(199, 21, 133));
		button_1.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_1.setFocusPainted(false);
		button_1.setBorder(BorderFactory.createRaisedBevelBorder());
		button_1.setBackground(new Color(250, 250, 210));
		button_1.setBounds(227, 13, 203, 75);
		buttonPanel.add(button_1);
		
		JButton button_2 = new JButton("City");
		button_2.setOpaque(false);
		button_2.setForeground(new Color(199, 21, 133));
		button_2.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_2.setFocusPainted(false);
		button_2.setBorder(BorderFactory.createRaisedBevelBorder());
		button_2.setBackground(new Color(250, 250, 210));
		button_2.setBounds(12, 101, 203, 75);
		buttonPanel.add(button_2);
		
		JButton button_3 = new JButton("City");
		button_3.setOpaque(false);
		button_3.setForeground(new Color(199, 21, 133));
		button_3.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_3.setFocusPainted(false);
		button_3.setBorder(BorderFactory.createRaisedBevelBorder());
		button_3.setBackground(new Color(250, 250, 210));
		button_3.setBounds(227, 101, 203, 75);
		buttonPanel.add(button_3);
		
		JButton button_4 = new JButton("City");
		button_4.setOpaque(false);
		button_4.setForeground(new Color(199, 21, 133));
		button_4.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_4.setFocusPainted(false);
		button_4.setBorder(BorderFactory.createRaisedBevelBorder());
		button_4.setBackground(new Color(250, 250, 210));
		button_4.setBounds(12, 189, 203, 75);
		buttonPanel.add(button_4);
		
		JButton button_5 = new JButton("City");
		button_5.setOpaque(false);
		button_5.setForeground(new Color(199, 21, 133));
		button_5.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_5.setFocusPainted(false);
		button_5.setBorder(BorderFactory.createRaisedBevelBorder());
		button_5.setBackground(new Color(250, 250, 210));
		button_5.setBounds(12, 277, 203, 75);
		buttonPanel.add(button_5);
		
		JButton button_6 = new JButton("City");
		button_6.setOpaque(false);
		button_6.setForeground(new Color(199, 21, 133));
		button_6.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_6.setFocusPainted(false);
		button_6.setBorder(BorderFactory.createRaisedBevelBorder());
		button_6.setBackground(new Color(250, 250, 210));
		button_6.setBounds(227, 189, 203, 75);
		buttonPanel.add(button_6);
		
		JButton button_7 = new JButton("City");
		button_7.setOpaque(false);
		button_7.setForeground(new Color(199, 21, 133));
		button_7.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_7.setFocusPainted(false);
		button_7.setBorder(BorderFactory.createRaisedBevelBorder());
		button_7.setBackground(new Color(250, 250, 210));
		button_7.setBounds(227, 277, 203, 75);
		buttonPanel.add(button_7);
		
		JButton button_8 = new JButton("City");
		button_8.setOpaque(false);
		button_8.setForeground(new Color(199, 21, 133));
		button_8.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_8.setFocusPainted(false);
		button_8.setBorder(BorderFactory.createRaisedBevelBorder());
		button_8.setBackground(new Color(250, 250, 210));
		button_8.setBounds(12, 365, 203, 75);
		buttonPanel.add(button_8);
		
		JButton button_9 = new JButton("City");
		button_9.setOpaque(false);
		button_9.setForeground(new Color(199, 21, 133));
		button_9.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		button_9.setFocusPainted(false);
		button_9.setBorder(BorderFactory.createRaisedBevelBorder());
		button_9.setBackground(new Color(250, 250, 210));
		button_9.setBounds(227, 365, 203, 75);
		buttonPanel.add(button_9);
		
	}
}
