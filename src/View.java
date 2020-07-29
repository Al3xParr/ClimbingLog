import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class View{
	
	private JPanel panel;
	private JFrame frame;
	private JLabel dateLbl;
	private JTextField dateTxt;
	private JLabel durationLbl;
	private JTextField durationTxt;
	private JLabel typeLbl;
	private ButtonGroup typeGroup;
	private JLabel inOutLbl;
	private ButtonGroup inOutGroup;
	private JLabel exercisesLbl;
	private LinkedList<JCheckBox> exerciseBox;
	private JLabel newExerciseLbl;
	private JTextField newExerciseTxt;
	private JButton newExerciseBtn;
	private JLabel maxGradeLbl;
	private JTextField maxGradeTxt;
	private JLabel commentLbl;
	private JTextArea commentTxt;
	private JButton submitBtn;	
	
	public View() {
		
		
		dateLbl = new JLabel("Date: ");
		dateTxt = new JTextField();
		
		durationLbl = new JLabel("Duration:");
		durationTxt = new JTextField();
			
		
		typeLbl = new JLabel("Type of climbing: ");
		typeGroup = new ButtonGroup();
		
		JRadioButton sportRadio = new JRadioButton("Sport")
		sportRadio.setActionCommand("Sport");
		JRadioButton tradRadio = new JRadioButton("Trad")
		tradRadio.setActionCommand("Trad");
		JRadioButton boulderRadio = new JRadioButton("Bouldering")
	    boulderRadio.setActionCommand("Bouldering");
		
		typeGroup.add(sportRadio);
		typeGroup.add(tradRadio);
		typeGroup.add(boulderRadio);
		
		
		inOutLbl = new JLabel("Where:");
		inOutGroup = new ButtonGroup();
		JRadioButton inRadio = new JRadioButton("Indoors");
		inRadio.setActionCommand("Indoors");
		JRadioButton outRadio = new JRadioButton("Outdoors");
		outRadio.setActionCommand("Outdoors");
				
		inOutGroup.add(inRadio);
		inOutGroup.add(outRadio);
		
		exercisesLbl = new JLabel("Exercises:");	
		exerciseBox = new LinkedList<JCheckBox>();
		LinkedList<String> exercises =  Controller.getExercises();
		
		for (String ex : exercises) {
			exerciseBox.add(new JCheckBox(ex));
		}
		
		newExerciseLbl = new JLabel("Add new exercise...");
		newExerciseTxt = new JTextField();
		newExerciseBtn = new JButton("Add");
				
				
		maxGradeLbl = new JLabel("Max Grade this session: ");
		maxGradeTxt = new JTextField();
		
		commentLbl = new JLabel("Comment:");
		commentTxt = new JTextArea();
		
		submitBtn = new JButton("Submit");
		
		
		frame = new JFrame("Climbing Log");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		
		panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addComponent(durationLbl)
					.addComponent(durationTxt));
		
		
		
	}
	
	public JButton getAddExBtn() {
		return this.newExerciseBtn;
	}
	
	public JButton getSubmitBtn() {
		return this.submitBtn;
	}
	
	public String getDate() {
		return dateTxt.getText();
	}
	
	public String getDuration() {
		return durationTxt.getText();
	}
	
	public String getMaxGrade() {
		return maxGradeTxt.getText();
	}
	
	public String getType() {
		return typeGroup.getSelection().getActionCommand();
	}
	
	public String getInOut() {
		return inOutGroup.getSelection().getActionCommand();
	}
	
	public LinkedList<String> getExercises(){
		LinkedList<String> ex = new LinkedList<String>();
		for (JCheckBox e : exerciseBox) {
			if (e.isSelected()) { ex.add(e.getText());}
		}
		return ex;
	}
	
	public String getComment() {
		return commentTxt.getText();
	}
	
	public void errorMsg(String error) {
		JOptionPane.showMessageDialog(frame, error,
	               "Something went wrong!", JOptionPane.ERROR_MESSAGE);
	}
	
	
}

	
	