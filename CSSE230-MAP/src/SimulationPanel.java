import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimulationPanel {// Only taking Care of the Displacing Panel not
								// the clear part

	private JFrame frame;
	private JPanel cityPanel;
	private JPanel catePanel;
	private MainMapCalculator data;
	private ArrayList<String> cityList = new ArrayList<>();
	private ArrayList<String> cateList = new ArrayList<>();
	private Listener l;
	private JPanel firstPanel;
	private JPanel secondPanel;
	private JPanel shortPathResultPanel;
	private JPanel suggestPanel;
	private ChoiceFrame CF;

	public SimulationPanel(JFrame f, MainMapCalculator m, ChoiceFrame chooseFrame, Listener listener) {
		this.frame = f;
		this.data = m;
		this.cityList = m.getCityList();
		this.cateList = m.getCategoryList();
		this.CF = chooseFrame;
		this.l = listener;
		this.l.addResource(this);
		setFirstPanel();
		setSecondPanel();
		setShortestResultPanel();
		setSuggestResultPanel();
		makeCityPanelNControlButton();
		makeCategoryPanelNControlButton();
		makeTitle();
		makeControlButton();

	}

	private void setFirstPanel() {
		this.firstPanel = new JPanel();
		this.firstPanel.setVisible(true);
		this.firstPanel.setSize(1000, 580);
		this.firstPanel.setLayout(null);
		this.firstPanel.setOpaque(false);
		this.frame.getContentPane().add(this.firstPanel);
	}

	private void setSecondPanel() {
		this.secondPanel = new JPanel();
		this.secondPanel.setVisible(false);
		this.secondPanel.setSize(1000, 580);
		this.secondPanel.setLayout(null);
		this.secondPanel.setOpaque(false);
		this.frame.getContentPane().add(this.secondPanel);
	}

	private void setShortestResultPanel() {
		this.shortPathResultPanel = new JPanel();
		this.shortPathResultPanel.setVisible(false);
		this.shortPathResultPanel.setSize(1000, 580);
		this.shortPathResultPanel.setLayout(null);
		this.shortPathResultPanel.setOpaque(false);
		this.frame.getContentPane().add(this.shortPathResultPanel);
	}

	private void setSuggestResultPanel() {
		this.suggestPanel = new JPanel();
		this.suggestPanel.setVisible(false);
		this.suggestPanel.setSize(1000, 580);
		this.suggestPanel.setLayout(null);
		this.suggestPanel.setOpaque(false);
		this.frame.getContentPane().add(this.suggestPanel);
	}

	public void makeCityPanelNControlButton() {
		// city panel
		this.cityPanel = new JPanel();
		this.cityPanel.setBounds(279, 98, 650, 400);
		this.firstPanel.add(this.cityPanel);
		this.cityPanel.setLayout(null);
		this.cityPanel.setVisible(false);
		this.cityPanel.setOpaque(false);
		ArrayList<JButton> k = new ArrayList<>();
		for (int i = 0; i < this.cityList.size(); i++) {
			k.add(new JButton("<html>" + this.cityList.get(i) + "<html>"));
			this.buttonSetup(k.get(i), i);
			this.cityPanel.add(k.get(i));
			k.get(i).addActionListener(this.l);
			this.l.addButton(this.cityList.get(i), k.get(i));
		}

	}

	private void makeCategoryPanelNControlButton() {
		// category panel
		this.catePanel = new JPanel();
		this.catePanel.setBounds(279, 98, 650, 400);
		this.firstPanel.add(this.catePanel);
		this.catePanel.setLayout(null);
		this.catePanel.setVisible(false);
		this.catePanel.setOpaque(false);
		ArrayList<JButton> j = new ArrayList<>();
		for (int i = 0; i < this.cateList.size(); i++) {
			j.add(new JButton("<html>" + this.cateList.get(i) + "<html>"));
			this.buttonSetup(j.get(i), i);
			this.catePanel.add(j.get(i));
			j.get(i).addActionListener(this.l);
			this.l.addButton(this.cateList.get(i), j.get(i));
		}
	}

	private void makeTitle() {
		JLabel lblNewLabel = new JLabel("Choose the location by...");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 45));
		lblNewLabel.setBounds(24, 13, 509, 86);
		this.firstPanel.add(lblNewLabel);
	}

	private void makeControlButton() {

		JPanel optionPanel = new JPanel();
		optionPanel.setBounds(12, 78, 270, 420);
		this.firstPanel.add(optionPanel);
		optionPanel.setLayout(null);
		optionPanel.setOpaque(false);

		// button setup
		final JButton cityBut = new JButton("City");
		cityBut.setBounds(12, 33, 243, 72);
		optionPanel.add(cityBut);
		cityBut.addActionListener(this.l);
		this.l.addButton("City", cityBut);
		cityBut.setOpaque(false);
		cityBut.setForeground(new Color(250, 128, 114));
		cityBut.setFont(new Font("Cooper Black", Font.PLAIN, 45));
		cityBut.setFocusPainted(false);
		cityBut.setBorder(BorderFactory.createRaisedBevelBorder());
		cityBut.setBackground(new Color(250, 250, 210));

		JButton catBut = new JButton("Category");
		catBut.setBounds(12, 134, 243, 72);
		optionPanel.add(catBut);
		catBut.addActionListener(this.l);
		this.l.addButton("Category", catBut);
		catBut.setOpaque(false);
		catBut.setForeground(new Color(250, 128, 114));
		catBut.setFont(new Font("Cooper Black", Font.PLAIN, 45));
		catBut.setFocusPainted(false);
		catBut.setBorder(BorderFactory.createRaisedBevelBorder());
		catBut.setBackground(new Color(250, 250, 210));

		JButton sugBut = new JButton("Suggest");
		sugBut.setOpaque(false);
		sugBut.setForeground(Color.MAGENTA);
		sugBut.setFont(new Font("Cooper Black", Font.PLAIN, 45));
		sugBut.setFocusPainted(false);
		sugBut.setBorder(BorderFactory.createRaisedBevelBorder());
		sugBut.setBackground(new Color(250, 250, 210));
		sugBut.setBounds(12, 296, 243, 72);
		sugBut.addActionListener(this.l);
		this.l.addButton("Suggest", sugBut);
		optionPanel.add(sugBut);

		JButton pathBut = new JButton("<html>Find The Path<htmp>");
		pathBut.setVisible(false);
		pathBut.setOpaque(false);
		pathBut.setForeground(Color.MAGENTA);
		pathBut.setFont(new Font("Cooper Black", Font.PLAIN, 45));
		pathBut.setFocusPainted(false);
		pathBut.setBorder(BorderFactory.createRaisedBevelBorder());
		pathBut.setBackground(new Color(250, 250, 210));
		pathBut.setBounds(12, 296, 243, 120);
		pathBut.addActionListener(this.l);
		this.l.addButton("Path", pathBut);
		optionPanel.add(pathBut);

	}

	// create the city and category select panel
	public void makeCityNCatePanel(String name) {

		ImageIcon BG1 = new ImageIcon();

		if (this.cityList.contains(name)) {
			String city = name + ".png";
			BG1 = new ImageIcon("City/" + city);
		}

		if (this.cateList.contains(name)) {
			String category = name + ".png";
			BG1 = new ImageIcon("Category/" + category);
		}

		BG1.setImage(BG1.getImage().getScaledInstance(520, 554, Image.SCALE_DEFAULT));
		// change icon size
		JLabel BG = new JLabel("BG");
		BG.setIcon(BG1);
		BG.setBounds(468, 13, 520, 554);
		BG.setOpaque(false);
		this.secondPanel.add(BG);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(12, 13, 450, 554);
		this.secondPanel.add(buttonPanel);
		buttonPanel.setLayout(null);
		buttonPanel.setVisible(true);
		buttonPanel.setOpaque(false);

		// make 2 back button
		JButton backToCity = new JButton("City");
		backToCity.setBounds(12, 463, 203, 75);
		buttonPanel.add(backToCity);
		backToCity.setOpaque(false);
		backToCity.setForeground(new Color(250, 128, 114));
		backToCity.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		backToCity.setFocusPainted(false);
		backToCity.setBorder(BorderFactory.createRaisedBevelBorder());
		backToCity.setBackground(new Color(250, 250, 210));
		backToCity.addActionListener(this.l);
		this.l.addButton("ShowCity", backToCity);

		JButton bakcToCategory = new JButton("Category");
		bakcToCategory.setBounds(227, 463, 203, 75);
		buttonPanel.add(bakcToCategory);
		bakcToCategory.setOpaque(false);
		bakcToCategory.setForeground(new Color(250, 128, 114));
		bakcToCategory.setFont(new Font("Cooper Black", Font.PLAIN, 40));
		bakcToCategory.setFocusPainted(false);
		bakcToCategory.setBorder(BorderFactory.createRaisedBevelBorder());
		bakcToCategory.setBackground(new Color(250, 250, 210));
		bakcToCategory.addActionListener(this.l);
		this.l.addButton("ShowCategory", bakcToCategory);

		ArrayList<String> pointList = new ArrayList<String>();
		pointList = this.data.getCategoryNCity(name);
		System.out.println(pointList);

		ArrayList<JButton> k = new ArrayList<>();
		for (int i = 0; i < pointList.size(); i++) {
			k.add(new JButton("<html>" + pointList.get(i) + "<html>"));
			this.pointBottonSetup(k.get(i), i);
			buttonPanel.add(k.get(i));
			k.get(i).addActionListener(this.l);
			this.l.addButton(pointList.get(i), k.get(i));
		}

	}

	public ArrayList<String> getCityList() {
		return this.cityList;
	}

	public ArrayList<String> getCateList() {
		return this.cateList;
	}

	/*
	 * The following method is used for recreate the frame based on what button
	 * has been pressed.
	 */
	public void cityVisable() {
		this.cityPanel.setVisible(true);
		this.catePanel.setVisible(false);
	}

	public void cateVisable() {
		this.cityPanel.setVisible(false);
		this.catePanel.setVisible(true);
	}

	public void closeFirstPage() {
		this.firstPanel.setVisible(false);
	}

	public void showCityNCate(String name) {
		this.closeFirstPage();
		this.makeCityNCatePanel(name);
	}

	public void showSecondPanel() {
		this.secondPanel.setVisible(true);
	}

	public void backToCity() {
		this.secondPanel.setVisible(false);
		this.firstPanel.setVisible(true);
		this.cityVisable();
		this.panel2RemoveAll();
	}

	public void backToCategory() {
		this.secondPanel.setVisible(false);
		this.firstPanel.setVisible(true);
		this.cateVisable();
		this.panel2RemoveAll();
	}

	public void panel2RemoveAll() {
		this.secondPanel.removeAll();
	}
	/*
	 * Stop Here
	 */

	public void buttonSetup(JButton JB, int i) {
		JB.setOpaque(false);
		JB.setForeground(new Color(220, 20, 60));
		JB.setFont(new Font("Cooper Black", Font.PLAIN, 25));
		JB.setFocusPainted(false);
		JB.setBorder(BorderFactory.createRaisedBevelBorder());
		JB.setBackground(new Color(250, 250, 210));
		if (i <= 3) {
			JB.setBounds(12, 13 + i * 85, 183, 72);
		} else if (i <= 7) {
			JB.setBounds(226, 13 + (i - 4) * 85, 183, 72);
		} else if (i <= 11) {
			JB.setBounds(440, 13 + (i - 8) * 85, 183, 72);
		}
	}

	public void pointBottonSetup(JButton JB, int i) {
		JB.setOpaque(false);
		JB.setForeground(new Color(199, 21, 133));
		JB.setFont(new Font("Cooper Black", Font.PLAIN, 30));
		JB.setFocusPainted(false);
		JB.setBorder(BorderFactory.createRaisedBevelBorder());
		JB.setBackground(new Color(250, 250, 210));

		if (i <= 4) {
			JB.setBounds(12, 13 + 88 * i, 203, 75);
		} else {
			JB.setBounds(227, 13 + 88 * (i - 5), 203, 75);
		}
	}

	public void printThePath(ArrayList<Object> result) {

		//
		this.firstPanel.setVisible(false);
		this.secondPanel.setVisible(false);
		System.out.println("CORRECT");
		JLabel resultLabel = new JLabel("<HTML>We Find The Shortest Path For You!!<br>Have A Safe Trip ^_^<HTML>");
		resultLabel.setForeground(new Color(255, 153, 0));
		resultLabel.setFont(new Font("UD Digi Kyokasho NK-B", Font.BOLD, 38));
		resultLabel.setBounds(130, 5, 750, 160);
		this.shortPathResultPanel.add(resultLabel);

		System.out.println(result);

		JLabel lblPath = new JLabel("<html>Shortest Path: <html>");
		lblPath.setForeground(new Color(255, 215, 0));
		lblPath.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		lblPath.setBounds(12, 190, 215, 75);
		this.shortPathResultPanel.add(lblPath);

		JLabel lblShortestPath = new JLabel("");
		String message = "<html>";
		for (int i = 0; i < result.size() - 3; i++) {
			message = message + result.get(i) + " ==> ";
		}
		message += result.get(result.size() - 3);
		message += "<html>";
		lblShortestPath.setText(message);
		lblShortestPath.setForeground(new Color(255, 0, 0));
		lblShortestPath.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		lblShortestPath.setBounds(239, 128, 731, 199);
		this.shortPathResultPanel.add(lblShortestPath);

		JLabel lblDistance = new JLabel("Distance:");
		lblDistance.setForeground(new Color(255, 215, 0));
		lblDistance.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		lblDistance.setBounds(12, 339, 215, 75);
		this.shortPathResultPanel.add(lblDistance);

		DecimalFormat numFormat = new DecimalFormat("#.00");
		JLabel disData = new JLabel(numFormat.format(result.get(result.size() - 2)) + " km");
		disData.setForeground(Color.RED);
		disData.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		disData.setBounds(239, 340, 425, 75);
		this.shortPathResultPanel.add(disData);

		JLabel lblTime = new JLabel("Time: ");
		lblTime.setForeground(new Color(255, 215, 0));
		lblTime.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		lblTime.setBounds(12, 427, 215, 75);
		this.shortPathResultPanel.add(lblTime);
		this.shortPathResultPanel.setVisible(true);
		this.frame.repaint();

		String timeNum = "";
		timeNum += result.get(result.size() - 1);
		JLabel timeData = new JLabel(timeNum + " min");
		timeData.setForeground(Color.RED);
		timeData.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 34));
		timeData.setBounds(239, 428, 425, 75);
		this.shortPathResultPanel.add(timeData);

	}

	public void back() {
		this.shortPathResultPanel.setVisible(false);
		this.suggestPanel.setVisible(false);
		this.suggestPanel.removeAll();
		this.shortPathResultPanel.removeAll();
		backToCity();
		this.frame.repaint();

	}

	public void printTheList(ArrayList<Object> arrayList) {
		this.suggestPanel.setVisible(true);
		this.firstPanel.setVisible(false);
		this.secondPanel.setVisible(false);
		System.out.println(arrayList);
		
		int size = arrayList.size();
		int j=0;
		if(size==0){
			JLabel resultLabel = new JLabel("<HTML>There are no places around you in the distance, please try again!<HTML>");
			resultLabel.setForeground(new Color(255, 153, 0));
			resultLabel.setFont(new Font("UD Digi Kyokasho NK-B", Font.BOLD, 45));
			resultLabel.setBounds(130, 100, 750, 250);
			this.suggestPanel.add(resultLabel);
			return;
		}

		JLabel resultLabel = new JLabel("<HTML>These are the places you can go, have a safe trip^_^<HTML>");
		resultLabel.setForeground(new Color(255, 153, 0));
		resultLabel.setFont(new Font("UD Digi Kyokasho NK-B", Font.BOLD, 38));
		resultLabel.setBounds(130, 5, 750, 160);
		this.suggestPanel.add(resultLabel);

		JLabel lblDistance = new JLabel("Distance:");
		lblDistance.setForeground(new Color(255, 51, 51));
		lblDistance.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 25));
		lblDistance.setBounds(12, 279, 153, 101);
		this.suggestPanel.add(lblDistance);

		JLabel lblLocation = new JLabel("Location: ");
		lblLocation.setForeground(new Color(255, 51, 51));
		lblLocation.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 25));
		lblLocation.setBounds(12, 165, 153, 101);
		this.suggestPanel.add(lblLocation);

		JLabel label_2 = new JLabel("Time: ");
		label_2.setForeground(new Color(255, 51, 51));
		label_2.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 30));
		label_2.setBounds(12, 393, 153, 101);
		this.suggestPanel.add(label_2);

		for (int i = 0; i < size; i = i + 3) {
			if (i > 14) {
				return;
			}
			
			JLabel location = new JLabel("<html>" + arrayList.get(i) +"<html>");
			location.setForeground(Color.MAGENTA);
			location.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 23));
			location.setBounds(177 + 157*j, 148, 145, 136);
			this.suggestPanel.add(location);

			DecimalFormat numFormat = new DecimalFormat("#.00");
			JLabel distance = new JLabel(numFormat.format(arrayList.get(i+1)) + "km");
			distance.setForeground(Color.MAGENTA);
			distance.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 23));
			distance.setBounds(177 + 157*j , 279, 145, 101);
			this.suggestPanel.add(distance);
			
			JLabel time = new JLabel(arrayList.get(i + 2)+"min");
			time.setForeground(Color.MAGENTA);
			time.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 23));
			time.setBounds(177 + 157*j, 393, 145, 101);
			this.suggestPanel.add(time);
			String message = "<html>";
			
			j++;
		}
	}

}
