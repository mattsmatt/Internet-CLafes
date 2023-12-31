package controller;

import main.Main;
import view.MakePCBook;
import view.MakeReport;
import view.MenuCustomer;
import view.ViewAllPC;
import view.ViewCustomerTransaction;

public class CustomerController {
	// Class ini dianggap sebagai Customer Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Customer
	
	public MenuCustomer menuCustomer;
	private final String role = "Customer";
	
	// CustomerController menggunakan Singleton pattern supaya cuma satu instance yang dibuat dalam app
	public static volatile CustomerController instance = null;
	
	public static CustomerController getInstance() {
		if(instance == null) {
			synchronized (CustomerController.class) {
				if(instance == null) {
					instance = new CustomerController();
				}
			}
		}
		
		return instance;
	}
	
	private CustomerController() {
		menuCustomer = new MenuCustomer();
		menuCustomer.initialize();
		addHandlers(menuCustomer);
	}
	
	// Menambahkan event handler onclick untuk tiap menu item
	private void addHandlers(MenuCustomer mc) {
		// Book PC
		mc.menuItemBookPC.setOnAction(e->{
			Main.changeScene(new MakePCBook().initPage(Main.user));
		});
		// Make Report
		mc.menuItemMakeReport.setOnAction(e->{
			Main.changeScene(new MakeReport().initPage(role));
		});
		// View All PC
		mc.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
		// View Customer Transaction History
		// menambahkan param user untuk mendapatkan user ID
		mc.menuItemViewCustomerTransaction.setOnAction(e->{
			Main.changeScene(new ViewCustomerTransaction().initPage(role, Main.user));
		});
	}
}
