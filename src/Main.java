import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main implements ActionListener {
	
	private JPanel mainPnl;
	private JPanel addSessionPnl;
	private JPanel viewStatsPnl;
	private JPanel viewSessionsPnl;
	
	private List<String> exercises;
	
	
	public Main() {
		JFrame frame = new JFrame();
		JButton addSessionBtn = new JButton("Add Session");
		JButton viewStatsBtn = new JButton("View Stats");
		JButton viewSessionsBtn = new JButton("View Sessions");
		
		addSessionBtn.addActionListener(this);
		
		//Creating main menu panel
		mainPnl = new JPanel();
		mainPnl.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		mainPnl.setLayout(new GridLayout(0,1));
		mainPnl.add(addSessionBtn);
		mainPnl.add(viewStatsBtn);
		mainPnl.add(viewSessionsBtn);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mainPnl, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	public static void main(String[] arg) {
		new Main();
	}
	
	public void createAddSessionPnl() {
		JLabel durationLbl = new JLabel("Duration:");
		JTextField durationTxt = new JTextField();
		
		JLabel typeLbl = new JLabel("Type of climbing: ");
		JCheckBox sportBox = new JCheckBox("Sport");
		JCheckBox boulderingBox = new JCheckBox("Bouldering");
		JCheckBox tradBox = new JCheckBox("Trad");
		
		JLabel inOutLbl = new JLabel("Where:");
		ButtonGroup inOutGroup = new ButtonGroup();
		inOutGroup.add(new JRadioButton("Indoors"));
		inOutGroup.add(new JRadioButton("Outdoors"));
		
		JLabel exercisesLbl = new JLabel("Exercises:");	
		List<JCheckBox> exerciseBox = new LinkedList<JCheckBox>();
		for (String ex : exercises) {
			exerciseBox.add(new JCheckBox(ex));
		}
		
		JLabel newExerciseLbl = new JLabel("Add new exercise...");
		JTextField newExerciseTxt = new JTextField();
		JButton newExerciseBtn = new JButton("Add");
				
				
		JLabel maxGradeLbl = new JLabel("Max Grade this session: ");
		JTextField maxGradeTxt = new JTextField();
		
		JLabel commentLbl = new JLabel("Comment:");
		JTextArea commentTxt = new JTextArea();
		
		
		addSessionPnl = new JPanel();
		addSessionPnl.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		addSessionPnl.setLayout(new GridLayout(0,1));
		//addSessionPnl.add()
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0);
		
	}
}