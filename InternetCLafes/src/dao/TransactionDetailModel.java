package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Vector;

import controller.PCBookController;
import database.Connect;
import model.PCBook;
import model.TransactionDetail;

public class TransactionDetailModel {
	// TransactionDetailModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile TransactionDetailModel instance = null;
	
	private TransactionDetailModel() {
		
	}
	
	public static TransactionDetailModel getInstance() {
		if(instance == null) {
			synchronized (TransactionDetailModel.class) {
				if(instance == null) {
					 instance = new TransactionDetailModel();
				}
			}
		}
		
		return instance;
	}
	
	// method untuk mendapatkan transaction detail dari transaction id 
	public Vector<TransactionDetail> getAllTransactionDetail(int transactionID) {
		Vector<TransactionDetail> transList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `transactiondetail` WHERE `TransactionID` = ?";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, transactionID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer TransactionDetailID = rs.getInt("TransactionDetailID");
				Integer TransactionID = rs.getInt("TransactionID");
				Integer PC_ID = rs.getInt("PC_ID");
				String CustomerName = rs.getString("CustomerName");
				LocalTime BookedTime = rs.getObject("BookedTime", LocalTime.class);

				transList.add(new TransactionDetail(TransactionID, PC_ID, TransactionDetailID, CustomerName, BookedTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transList;
	}

	// method untuk mendapatkan transaction detail dari customer id
	public Vector<TransactionDetail> getUserTransactionDetail(int userID) {
		Vector<TransactionDetail> transList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `transactiondetail` td JOIN `user` u ON td.`CustomerName` = u.`UserName` WHERE u.`UserID` = ?;";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer TransactionDetailID = rs.getInt("TransactionDetailID");
				Integer TransactionID = rs.getInt("TransactionID");
				Integer PC_ID = rs.getInt("PC_ID");
				String CustomerName = rs.getString("CustomerName");
				LocalTime BookedTime = rs.getObject("BookedTime", LocalTime.class);

				transList.add(new TransactionDetail(TransactionID, PC_ID, TransactionDetailID, CustomerName, BookedTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transList;
	}
	
	// method untuk menambahkan transaction detail
	public void addTransactionDetail(int TransactionID, List<PCBook> pcBookList) {
		Connect con = Connect.getInstance();
		UserModel um = UserModel.getInstance();
		
		for (PCBook pcBook : pcBookList) {
			String insertTransactionQuery = "INSERT INTO `transactiondetail`(`TransactionID`, `PC_ID`, `CustomerName`, BookedTime) VALUES (?, ?, ?, ?)";
			PreparedStatement insertPs = con.prepareStatement(insertTransactionQuery);

			try {
				String custName = um.getUserData(pcBook.getUserID()).getUserName();
				
				insertPs.setInt(1, TransactionID);
				insertPs.setInt(2, pcBook.getPC_ID());
				insertPs.setString(3, custName);
				LocalTime currentTime = LocalTime.now();
				Time sqlTime = Time.valueOf(currentTime);
				insertPs.setTime(4, sqlTime);
				insertPs.executeUpdate();
				
				PCBookController pcbControlller = PCBookController.getInstance();
				pcbControlller.deleteBookData(pcBook.getBook_ID());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}
