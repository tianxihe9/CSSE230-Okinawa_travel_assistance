import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener implements ActionListener {
	private SimulationPanel panel;
	private ChoiceFrame choiceFrameClass;

	public Listener(ChoiceFrame choiceFrame) {
		this.choiceFrameClass = choiceFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("City"))) {// CityButton
			this.panel.cityVisable();
		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Clear"))) {// clearButton
			this.choiceFrameClass.clearDataPanel();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Path"))) {// clearButton
			this.choiceFrameClass.setPathPanel();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("ListSuggestion"))) {// listSuggestionButton
			if (!this.choiceFrameClass.isDouble() || this.choiceFrameClass.pointList.size() == 0) {
				System.out.println("Error");
				return;
			}
			this.panel.printTheList(this.choiceFrameClass.getTheSuggList());
			this.choiceFrameClass.setResultPanel();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Suggest"))) {// suggestButton
			this.choiceFrameClass.clearDataPanel();
			this.choiceFrameClass.setSuggestPanel();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Quit"))) {// QuitButton
			this.choiceFrameClass.frame.dispose();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Category"))) {// CategoryButton
			this.panel.cateVisable();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("TryAgain"))) {// TryAgainButton
			this.choiceFrameClass.clearDataPanel();// clean data panel
			this.panel.back();// come back to select panel

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("ShowCity"))) {// ReturnToCityButton
			this.panel.backToCity();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("Calculate"))) {// FindPathButton

			this.panel.printThePath(this.choiceFrameClass.findThePath());
			this.choiceFrameClass.setResultPanel();

		} else if (e.getSource().equals(this.choiceFrameClass.butCointainer.get("ShowCategory"))) {// ReturnToCategoryButton
			this.panel.backToCategory();
		} else if (this.panel.getCityList()
				.contains(e.getActionCommand().substring(6, e.getActionCommand().length() - 6))) {// SelectCityButton
			// we need to restruct our buttonCommand since we need to remove
			// <html>.
			this.panel.showCityNCate(e.getActionCommand().substring(6, e.getActionCommand().length() - 6));
			this.panel.showSecondPanel();
			System.out.println(e.getActionCommand().substring(6, e.getActionCommand().length() - 6));
		} else if (this.panel.getCateList()
				.contains(e.getActionCommand().substring(6, e.getActionCommand().length() - 6))) {// SelectCategoryButton
			this.panel.showCityNCate(e.getActionCommand().substring(6, e.getActionCommand().length() - 6));
			this.panel.showSecondPanel();
			System.out.println(e.getActionCommand().substring(6, e.getActionCommand().length() - 6));
		} else {
			this.choiceFrameClass.addLocation(e.getActionCommand().substring(6, e.getActionCommand().length() - 6));
		}
	}

	public void addButton(String buttonName, JButton btn) {
		this.choiceFrameClass.butCointainer.put(buttonName, btn);

	}

	public void addResource(SimulationPanel simulationPanel) {
		this.panel = simulationPanel;

	}

}
