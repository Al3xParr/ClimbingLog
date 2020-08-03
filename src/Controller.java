import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Controller{
	
	private View view;
	private Model model;
	
	//Constructor
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	//Returns linked list of exercises from the db
	public static LinkedList<String> getExercises(){
		return Model.getExercises();
	}
	
	//Adds the listeners for the buttons of the window
	public void addListeners() {
		view.getAddExBtn().addActionListener(e -> addExBtnPressed());
		view.getSubmitBtn().addActionListener(e -> submitBtnPressed());
	}
	
	//Add a new exercise to the db
	public void addExBtnPressed() {
		String newEx = view.getNewEx();
		String qString = "Are you sure you want to add " + newEx + " as a new exercise?";
		boolean success = false;
		
		//Asks user if they are sure they want to add the exercise
		if (view.questionMsg(qString)) { 
			success = Model.addExercise(newEx);
			if (success) {
				view.userMsg("Successfully added new exercise");
				view.newExerciseAdded(newEx);}
			else { view.errorMsg("Something went wrong! Check the exercise isn't already an option!");}
		}	
	}
	
	//Processess user input before adding new record to db
	public void submitBtnPressed() {
		String date;
		int dur = 0;
		String max;
		int indoor;
		String type;
		LinkedList<String> ex;
		
		//Validation for user inputs
		date = view.getDate();
		if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
			view.errorMsg("Please put the date in the format required!");
			return;
		}
		
		try {dur = Integer.parseInt(view.getDuration());}
		catch (NumberFormatException e) {view.errorMsg("Please enter a valid integer for duration"); return;}
		
		type = view.getType();
		
		if (view.getInOut().equals("Indoor")) { indoor = 1;}
		else { indoor = 0;}
		
		ex = view.getExercises();
		
		//Regex formats of different climbing grade systems for different disciplines
		String[] sprtGrdFormat = new String[3];
		sprtGrdFormat[0] = "\\d[abc][+]{0,1}";
		sprtGrdFormat[1] = "5.\\d";
		sprtGrdFormat[2] = "5.\\d{2}[abcd]";
		
		String[] blderGrdFormat = new String[2];
		blderGrdFormat[0] = "[vV]\\d{1,2}";
		blderGrdFormat[1] = "\\d[abc][+]{0,1}";
		
		String[] trdGrdFormat = new String[5];
		trdGrdFormat[0] = "Mod";
		trdGrdFormat[1] = "Diff";
		trdGrdFormat[2] = "VDiff";
		trdGrdFormat[3] = "HVD";
		trdGrdFormat[4] = "Sev";
		trdGrdFormat[5] = "HS";
		trdGrdFormat[6] = "VS_\\d[abc]";
		trdGrdFormat[7] = "HVS_\\d[abc]";
		trdGrdFormat[8] = "E\\d{1,2}_\\d[abc]";
		
		max = view.getMaxGrade();
		boolean valid = false;
		String[] formatReq;
		
		if (type.equals("Sport")) { formatReq = sprtGrdFormat;}
		else if (type.equals("Trad")) { formatReq = trdGrdFormat;}
		else { formatReq = blderGrdFormat;}
		
		for (String reg : formatReq) {
			if (max.matches(reg)) { valid = true;}
		}
		if (!valid) {view.errorMsg("Please enter a valid grade"); return; }
		
				
		Model.addSession(date, dur, max, indoor, type, ex);
		
		//Creates a StringBuffer to save all the information to a txt diary
		StringBuffer sessionString = new StringBuffer();
		sessionString.append(date);
		sessionString.append("\nDuration: " + dur);
		if (indoor == 1) { sessionString.append("\nIndoors");}
		else { sessionString.append("\nOutdoors");}
		sessionString.append("\nMax grade: " + max);
		
		sessionString.append("\nExercises:\n");
		String comma = "";
		for (String e : ex) {
			sessionString.append(comma + e);
			comma = ", ";
		}
		sessionString.append("Comment:\n" + view.getComment());
		
		try {
			FileWriter txtFile = new FileWriter("sessionsDiary.txt");
			txtFile.write(sessionString.toString());
			txtFile.close();
		} catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
			
		view.userMsg("Your climbing session has been logged!");
	}
}