import java.util.LinkedList;

public class Controller{
	
	private View view;
	private Model model;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	public static LinkedList<String> getExercises(){
		return Model.getExercises();
	}
	
	public void addListeners() {
		view.getAddExBtn().addActionListener(e -> addExBtnPressed());
		view.getSubmitBtn().addActionListener(e -> submitBtnPressed());
	}
	
	public void addExBtnPressed() {
		;
	}
	
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
			date = "";
		}
		
		try {dur = Integer.parseInt(view.getDuration());}
		catch (NumberFormatException e) {view.errorMsg("Please enter a valid integer for duration");}
		
		type = view.getType();
		
		if (view.getInOut().equals("Indoor")) { indoor = 1;}
		else { indoor = 0;}
		
		ex = view.getExercises();
		
		max = view.getMaxGrade();
		if (type.equals("sport")) {
			if (date.matches(regex))
		}
		
		
		
		model.addSession(date, dur, max, indoor, type, ex);
	}
}