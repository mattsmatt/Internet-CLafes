package controller;

import main.Main;
import view.MenuTechnician;
import view.ViewAllPC;

public class ComputerTechnicianController {
	// Class ini dianggap sebagai Computer Technician Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Computer Technician
	
	public MenuTechnician menuTechnician;
	private final String role = "Computer Technician";
	
	// ComputerTechnicianController menggunakan Singleton pattern supaya instance hanya satu di seluruh aplikasi
	public static volatile ComputerTechnicianController instance = null;
	
	public static ComputerTechnicianController getInstance() {
		if(instance == null) {
			synchronized (ComputerTechnicianController.class) {
				if(instance == null) {
					instance = new ComputerTechnicianController();
				}
			}
		}
		
		return instance;
	}
	
	private ComputerTechnicianController() {
		menuTechnician = new MenuTechnician();
		menuTechnician.initialize();
		addHandlers(menuTechnician);
	}
	
	// Menambahkan event handler onclick untuk tiap menu item
	private void addHandlers(MenuTechnician mt) {
		// Complete Job
		mt.menuItemCompleteJob.setOnAction(e->{
			Main.changeScene(null);
		});
		// View All PC
		mt.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
		// View All Job
		mt.menuItemViewAllJob.setOnAction(e->{
			Main.changeScene(null);
		});
	}
}
