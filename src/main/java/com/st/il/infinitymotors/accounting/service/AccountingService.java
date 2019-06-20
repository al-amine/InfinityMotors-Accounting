package com.st.il.infinitymotors.accounting.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.st.il.infinitymotors.accounting.dao.OrderDao;
import com.st.il.infinitymotors.accounting.dao.OrderItemDao;
import com.st.il.infinitymotors.accounting.models.Order;
import com.st.il.infinitymotors.accounting.models.OrderItem;

@Service
public class AccountingService {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	
	
	public void generateReport() throws IOException{
		
		//create filename based on current month and year
		LocalDateTime now = LocalDateTime.now();
		Month monthName = now.getMonth();
		int year = now.getYear();
		String filename = monthName + "-" + year + "report.csv";
		
		//initialize a csvwriter
		CSVWriter writer = new CSVWriter(new FileWriter(filename));
		
		//add header row
		String[] header = {
			"NumCarsSold", "NumOrders", "NumCustomers", "TotalRevenue", "Taxes(6%)", "NetIncome"
		};
		writer.writeNext(header);
		
		//calculate results
		int monthInt = now.getMonthValue();
		String numCarsSold = getNumCarsSold(monthInt, year);
		String numOrders = getNumOrders(monthInt, year);
		
		
		//add results as a row to csv file
		String[] data = {numCarsSold, numOrders};
		writer.writeNext(data);
			
		writer.close();
	}
	
	
	public String getNumCarsSold(int month, int year) {
		
		List<Order> orders = orderDao.findAll();
		List<Integer> orderIds = new ArrayList<>();
		
		for(Order o : orders) {
			//add orderIds that are within current month and year to arraylist
			if((month == o.getPurchaseDate().getMonthValue()) && (year == o.getPurchaseDate().getYear())) {
				orderIds.add(o.getOrderId());
			}
		}
		
		int totalNumberCars = 0;
		for(int i=0; i<orderIds.size(); i++) {
			//using custom-made jpa method
			List<OrderItem> matchingItems = orderItemDao.findAllByOrderId(orderIds.get(i));

			totalNumberCars = totalNumberCars + matchingItems.size();
		}
		
		//return string version of totalNumberCars
		return Integer.toString(totalNumberCars);
	}
	
	
	public String getNumOrders(int month, int year) {
		
		List<Order> orders = orderDao.findAll();
		List<Integer> orderIds = new ArrayList<>();
		
		for(Order o : orders) {
			if((month == o.getPurchaseDate().getMonthValue()) && (year == o.getPurchaseDate().getYear())) {
				orderIds.add(o.getOrderId());
			}
		}
		
		return Integer.toString(orderIds.size());
	}
	
	
	
}