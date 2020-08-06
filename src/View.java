import java.util.LinkedList;

import javax.swing.*;

public class View{
	
	//Declaring all the components for the UI
	private JPanel panel;
	private JFrame frame;
	private JLabel dateLbl;
	private JTextField dateTxt;
	private JLabel durationLbl;
	private JTextField durationTxt;
	private JLabel typeLbl;
	private ButtonGroup typeGroup;
	private JRadioButton sportRadio;
	private JRadioButton tradRadio;
	private JRadioButton boulderRadio;
	private JLabel inOutLbl;
	private ButtonGroup inOutGroup;
	private JRadioButton inRadio;
	private JRadioButton outRadio;
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
	
	private GroupLayout layout;
	
	public View() {
		
		//Filling all the UI components
		dateLbl = new JLabel("Date: ");
		dateTxt = new JTextField();
		
		durationLbl = new JLabel("Duration:");
		durationTxt = new JTextField();
			
		
		typeLbl = new JLabel("Type of climbing: ");
		typeGroup = new ButtonGroup();
		
		sportRadio = new JRadioButton("Sport");
		sportRadio.setActionCommand("Sport");
		tradRadio = new JRadioButton("Trad");
		tradRadio.setActionCommand("Trad");
		boulderRadio = new JRadioButton("Bouldering");
	    boulderRadio.setActionCommand("Bouldering");
		
		typeGroup.add(sportRadio);
		typeGroup.add(tradRadio);
		typeGroup.add(boulderRadio);
		
		inOutLbl = new JLabel("Where:");
		inOutGroup = new ButtonGroup();
		inRadio = new JRadioButton("Indoors");
		inRadio.setActionCommand("Indoors");
		outRadio = new JRadioButton("Outdoors");
		outRadio.setActionCommand("Outdoors");
				
		inOutGroup.add(inRadio);
		inOutGroup.add(outRadio);
		
		//Getting the current list of exercises for the db to display them
		exercisesLbl = new JLabel("Exercises:");	
		exerciseBox = new LinkedList<JCheckBox>();
		LinkedList<String> exercises =  Controller.getExercises();
		
		for (String ex : exercises) {
			exerciseBox.add(new JCheckBox(ex.replace("_", " ")));
		}
		
		newExerciseLbl = new JLabel("Add new exercise...");
		newExerciseTxt = new JTextField();
		newExerciseBtn = new JButton("Add");
				
				
		maxGradeLbl = new JLabel("Max Grade this session: ");
		maxGradeTxt = new JTextField();
		
		commentLbl = new JLabel("Comment:");
		commentTxt = new JTextArea();
		
		submitBtn = new JButton("Submit");
		
		//Creating the structure of the window
		frame = new JFrame("Climbing Log");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		panel = new JPanel();
		layout = new GroupLayout(panel);
		panel.setLayout(layout);	
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		setLayout();

					
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	//Setters and getters for components and values
	public JButton getAddExBtn() {
		return this.newExerciseBtn;
	}
	
	public JButton getSubmitBtn() {
		return this.submitBtn;
	}
	
	public JTextField getNewExTxt() {
		return this.newExerciseTxt;
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
	
	public String getNewEx() {
		return newExerciseTxt.getText();
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
	
	//Methods to display different messages to the user
	public void errorMsg(String error) {
		JOptionPane.showMessageDialog(frame, error, "Something went wrong!", JOptionPane.ERROR_MESSAGE);
	}
	
	public void userMsg(String msg) {
		JOptionPane.showMessageDialog(frame, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean questionMsg(String qu) {
		if (JOptionPane.showConfirmDialog(null, qu, "Question",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			return true;
		} else { return false; }
	}
	
	//Add new exercise into the window
	public void newExerciseAdded(String newEx) {
		exerciseBox.add(new JCheckBox(newEx));
		//Recreates the window with the new exercise and displays it
		setLayout();
		frame.pack();
		panel.revalidate();
		panel.repaint();
	}
	
	private void setLayout() {
		
		GroupLayout.ParallelGroup mainHorizGrp = layout.createParallelGroup();
		mainHorizGrp.addGroup(layout.createSequentialGroup()
							.addComponent(dateLbl)
							.addComponent(dateTxt))
					.addGroup(layout.createSequentialGroup()
							.addComponent(durationLbl)
							.addComponent(durationTxt))
					.addGroup(layout.createSequentialGroup()
							.addComponent(maxGradeLbl)
							.addComponent(maxGradeTxt))
					.addGroup(layout.createSequentialGroup()
							.addComponent(inOutLbl)
							.addComponent(outRadio)
							.addComponent(inRadio))
					.addGroup(layout.createSequentialGroup()
							.addComponent(typeLbl)
							.addComponent(tradRadio)
							.addComponent(boulderRadio)
							.addComponent(sportRadio));
		
		for (int i = 0; i < exerciseBox.size(); i += 4) {
			GroupLayout.SequentialGroup seqGrp = layout.createSequentialGroup();
			if (i < exerciseBox.size()) {seqGrp.addComponent(exerciseBox.get(i));}
			if (i+1 < exerciseBox.size()) {seqGrp.addComponent(exerciseBox.get(i+1));}
			if (i+2 < exerciseBox.size()) {seqGrp.addComponent(exerciseBox.get(i+2));}
			if (i+3 < exerciseBox.size()) {seqGrp.addComponent(exerciseBox.get(i+3));}
			mainHorizGrp.addGroup(seqGrp);
		}
		
		mainHorizGrp.addGroup(layout.createSequentialGroup()
							.addComponent(newExerciseLbl)
							.addComponent(newExerciseTxt)
							.addComponent(newExerciseBtn))
					.addGroup(layout.createSequentialGroup()
							.addComponent(commentLbl)
							.addComponent(commentTxt))
					.addGroup(layout.createSequentialGroup()
							.addComponent(submitBtn));
		
		layout.setHorizontalGroup(mainHorizGrp);
		
		GroupLayout.SequentialGroup mainVertGrp = layout.createSequentialGroup();
		mainVertGrp.addGroup(layout.createParallelGroup()
							.addComponent(dateLbl)
							.addComponent(dateTxt))
					.addGroup(layout.createParallelGroup()
							.addComponent(durationLbl)
							.addComponent(durationTxt))
					.addGroup(layout.createParallelGroup()
							.addComponent(maxGradeLbl)
							.addComponent(maxGradeTxt))
					.addGroup(layout.createParallelGroup()
							.addComponent(inOutLbl)
							.addComponent(outRadio)
							.addComponent(inRadio))
					.addGroup(layout.createParallelGroup()
							.addComponent(typeLbl)
							.addComponent(tradRadio)
							.addComponent(boulderRadio)
							.addComponent(sportRadio));
		
		for (int i = 0; i < exerciseBox.size(); i += 4) {
			GroupLayout.ParallelGroup parraGrp = layout.createParallelGroup();
			if (i < exerciseBox.size()) {parraGrp.addComponent(exerciseBox.get(i));}
			if (i+1 < exerciseBox.size()) {parraGrp.addComponent(exerciseBox.get(i+1));}
			if (i+2 < exerciseBox.size()) {parraGrp.addComponent(exerciseBox.get(i+2));}
			if (i+3 < exerciseBox.size()) {parraGrp.addComponent(exerciseBox.get(i+3));}
			mainVertGrp.addGroup(parraGrp);
		}
		
		mainVertGrp.addGroup(layout.createParallelGroup()
							.addComponent(newExerciseLbl)
							.addComponent(newExerciseTxt)
							.addComponent(newExerciseBtn))
					.addGroup(layout.createParallelGroup()
							.addComponent(commentLbl)
							.addComponent(commentTxt))
					.addGroup(layout.createParallelGroup()
							.addComponent(submitBtn))
					;
		
		layout.setVerticalGroup(mainVertGrp);
		
	}
	
	
}

	
	