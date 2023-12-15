package controller;

import java.util.Vector;

import dao.TransactionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import view.ViewAllTransaction.ViewAllTransactionVar;

public class TransactionController {
	// Transaction Controller menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile TransactionController instance = null;
	
	private TransactionController() {
		
	}
	
	public static TransactionController getInstance() {
		if(instance == null) {
			synchronized (TransactionController.class) {
				if(instance == null) {
					 instance = new TransactionController();
				}
			}
		}
		
		return instance;
	}
	
	// Akses model untuk mengakses data transaction header dan detail dari database
	TransactionModel transactionModel = TransactionModel.getInstance();
	
	// Method untuk mendapatkan list isinya semua data transaction header
	public Vector<TransactionHeader> getAllTransactionHeaderData(){
		return transactionModel.getAllTransactionHeaderData();
	}
	
	// Method untuk menambahkan record transaction header dan detail ke database melalui model
	public void addTransaction(int TransactionID, Vector<PCBook> ListPcBook, int StaffID) {
		transactionModel.addTransaction(TransactionID, ListPcBook, StaffID);
	}
	
	// Method untuk mendapatkan semua transaction detail dari transaction id yang bersangkutan
	public Vector<TransactionDetail> getAllTransactionDetail(int TransactionID) {
		return transactionModel.getAllTransactionDetail(TransactionID);
	}
	
	// Method untuk mendapatkan semua transaction detail dari user id yang bersangkutan
	public Vector<TransactionDetail> getUserTransactionDetail(int UserID) {
		return transactionModel.getUserTransactionDetail(UserID);
	}
	
	public void addViewTransactionDetailHandler(ViewAllTransactionVar components) {
		// mengatur logic kalau ada record di table transaction header yang di select
		components.transactionTable.setOnMouseClicked(e -> {
			TableSelectionModel<TransactionHeader> tableSelectionModel = components.transactionTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			// menampung selected item dari table
			TransactionHeader th = tableSelectionModel.getSelectedItem();
			
			// kalau transaction header ada yang di-select
			if(th != null) {
				AdminController.getInstance().createTransactionDetailWindow(th.getTransactionID());
			}
		});
	}
}