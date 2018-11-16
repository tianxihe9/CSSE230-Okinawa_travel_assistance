import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChoiceFrame {

	protected JFrame frame;
	protected SimulationPanel PS;
	protected ArrayList<String> pointList;
	protected JPanel dataPanel;
	protected MainMapCalculator map;
	protected HashMap<String, JButton> butCointainer = new HashMap();
	protected HashMap<String, JComponent> jCompCointainer = new HashMap();
	protected Listener listener = new Listener(this);
	private Boolean isSuggestMode = false;
	private JTextField textField;

	public ChoiceFrame() throws IOException {
		this.map = new MainMapCalculator();
		this.pointList = new ArrayList<>();
		this.initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void rePrint() {
		this.frame.repaint();
	}

	private void initialize() {
		this.frame = new JFrame();
		this.PS = new SimulationPanel(this.frame, this.map, this, this.listener);
		this.frame.setTitle("Start Your Trip...");
		this.frame.getContentPane().setBackground(Color.WHITE);
		this.frame.setBounds(100, 100, 1300, 625); // BG1 = 1000x580
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);
		this.frame.setLocationRelativeTo(null);

		ImageIcon BG1 = new ImageIcon("image/beach.jpg");
		BG1.setImage(BG1.getImage().getScaledInstance(1000, 580, Image.SCALE_DEFAULT));
		JLabel BGL = new JLabel("");
		BGL.setIcon(BG1);
		BGL.setBounds(0, 0, 1000, 580);
		this.frame.getContentPane().add(BGL);
		setDataPanel();
	}

	public void setDataPanel() {

		this.dataPanel = new JPanel();
		this.dataPanel.setBackground(new Color(255, 255, 255));
		this.dataPanel.setBounds(1000, 13, 270, 540);
		this.frame.getContentPane().add(this.dataPanel);
		this.dataPanel.setLayout(null);

		JLabel lblFirstLocation = new JLabel("From Location: ");
		this.jCompCointainer.put("From", lblFirstLocation);
		lblFirstLocation.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblFirstLocation.setBounds(12, 13, 263, 46);
		this.dataPanel.add(lblFirstLocation);

		JLabel lblSecondLocation = new JLabel("To Location: ");
		this.jCompCointainer.put("To", lblSecondLocation);
		lblSecondLocation.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblSecondLocation.setBounds(12, 224, 263, 46);
		this.dataPanel.add(lblSecondLocation);

		JLabel lblDisTravel = new JLabel("Distance (Km): ");
		this.jCompCointainer.put("Dis", lblDisTravel);
		lblDisTravel.setVisible(false);
		lblDisTravel.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblDisTravel.setBounds(12, 224, 263, 46);
		this.dataPanel.add(lblDisTravel);

		JButton clearBut = new JButton("Clear");
		this.butSetUp(clearBut);
		clearBut.setBounds(12, 470, 200, 70);
		this.listener.addButton("Clear", clearBut);
		clearBut.addActionListener(this.listener);
		this.dataPanel.add(clearBut);

		JButton quitBut = new JButton("Quit");
		this.butSetUp(quitBut);
		quitBut.setVisible(false);
		quitBut.setBounds(12, 470, 200, 70);
		this.listener.addButton("Quit", quitBut);
		quitBut.addActionListener(this.listener);
		this.dataPanel.add(quitBut);

		JButton againButton = new JButton("Try Again");
		this.butSetUp(againButton);
		againButton.setBounds(12, 365, 246, 73);
		this.dataPanel.add(againButton);
		this.listener.addButton("TryAgain", againButton);
		againButton.setVisible(false);
		againButton.addActionListener(this.listener);

		JButton calButton = new JButton("Calculate");
		this.butSetUp(calButton);
		calButton.setVisible(false);
		calButton.setBounds(12, 365, 246, 73);
		this.dataPanel.add(calButton);
		calButton.addActionListener(this.listener);
		this.listener.addButton("Calculate", calButton);

		JButton suggestListButton = new JButton("List Suggestion");
		this.butSetUp(suggestListButton);
		suggestListButton.setVisible(false);
		suggestListButton.setBounds(12, 365, 246, 73);
		this.dataPanel.add(suggestListButton);
		this.listener.addButton("ListSuggestion", suggestListButton);

		JTextField textField = new JTextField("number only");
		this.jCompCointainer.put("inputBar", textField);
		textField.setFont(new Font("Dialog", Font.PLAIN, 20));
		textField.setBounds(22, 283, 163, 40);
		textField.getText();
		textField.setVisible(false);
		dataPanel.add(textField);
		textField.setColumns(10);
		suggestListButton.addActionListener(this.listener);
	}

	public void addLocation(String point) {
		// Adding user selection into our datapanel and repaint it
		if (this.isSuggestMode && this.pointList.size() == 1) {
			return;
		}
		if (this.pointList.size() == 2) {
			return;
		}
		this.pointList.add(point);
		System.out.println(this.pointList);
		System.out.println(this.pointList.size());
		if (this.pointList.size() == 1) {
			JLabel variable1 = new JLabel("<html>" + this.pointList.get(0) + "<html>");
			this.jCompCointainer.put(this.pointList.get(0), variable1);
			variable1.setFont(new Font("Dialog", Font.PLAIN, 30));
			variable1.setForeground(Color.RED);
			variable1.setBounds(12, 72, 246, 73);
			this.dataPanel.add(variable1);
		}

		if (this.pointList.size() == 2) {
			JLabel variable2 = new JLabel("<html>" + this.pointList.get(1) + "<html>");
			this.jCompCointainer.put(this.pointList.get(1), variable2);
			variable2.setForeground(Color.RED);
			variable2.setFont(new Font("Dialog", Font.PLAIN, 30));
			variable2.setBounds(12, 283, 246, 73);
			this.dataPanel.add(variable2);
		}

		if (this.pointList.size() == 2) {
			this.butCointainer.get("Calculate").setVisible(true);

		}
		this.dataPanel.repaint();
	}

	private void butSetUp(JButton JB) {
		JB.setForeground(new Color(199, 21, 133));
		JB.setFont(new Font("Dialog", Font.PLAIN, 30));
		JB.setFocusPainted(false);
		JB.setBorder(BorderFactory.createRaisedBevelBorder());
		JB.setBackground(new Color(250, 250, 210));
		JB.setVisible(true);
	}

	public ArrayList<Object> findThePath() {
		System.out.println(this.pointList);
		System.out.println(this.map.findShortestPath(this.pointList.get(0), this.pointList.get(1)));
		return this.map.findShortestPath(this.pointList.get(0), this.pointList.get(1));
	}

	public void clearDataPanel() {
		for (int i = 0; i < this.pointList.size(); i++) {
			this.dataPanel.remove(this.jCompCointainer.get(this.pointList.get(i)));
		}
		this.butCointainer.get("Calculate").setVisible(false);
		this.butCointainer.get("Quit").setVisible(false);
		this.butCointainer.get("TryAgain").setVisible(false);
		this.butCointainer.get("Clear").setVisible(true);
		this.pointList.clear();
		this.dataPanel.repaint();
		// frame.remove(this.dataPanel);
		// setDataPanel();
		// frame.add(this.dataPanel);
		// this.frame.repaint();
	}

	public void setResultPanel() {

		this.butCointainer.get("Calculate").setVisible(false);
		this.butCointainer.get("Clear").setVisible(false);
		this.butCointainer.get("TryAgain").setVisible(true);
		this.butCointainer.get("Quit").setVisible(true);
	}

	public void setSuggestPanel() {
		this.isSuggestMode = true;
		this.butCointainer.get("ListSuggestion").setVisible(true);
		this.jCompCointainer.get("inputBar").setVisible(true);
		this.jCompCointainer.get("To").setVisible(false);
		this.jCompCointainer.get("Dis").setVisible(true);
		this.butCointainer.get("Suggest").setVisible(false);
		this.butCointainer.get("Path").setVisible(true);
	}

	public void setPathPanel() {
		this.isSuggestMode = false;
		this.butCointainer.get("ListSuggestion").setVisible(false);
		this.jCompCointainer.get("inputBar").setVisible(false);
		this.jCompCointainer.get("To").setVisible(true);
		this.jCompCointainer.get("Dis").setVisible(false);
		this.butCointainer.get("Suggest").setVisible(true);
		this.butCointainer.get("Path").setVisible(false);

	}

	public ArrayList<Object> getTheSuggList() {
		double num = Double.parseDouble(((JTextField) this.jCompCointainer.get("inputBar")).getText());
		return this.map.suggestionDistance(this.pointList.get(0), num);
	}

	public boolean isDouble() {
		String str = ((JTextField) this.jCompCointainer.get("inputBar")).getText();
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
