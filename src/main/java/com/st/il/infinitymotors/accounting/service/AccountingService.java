package com.st.il.infinitymotors.accounting.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
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
	
	private List<Order> ordersInCurrentMonthAndYear = new ArrayList<>();
	private int totalRevenue = 0;
	private float taxAmount = 0;
	private float netIncome = 0;
	
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
		
		int monthInt = now.getMonthValue();
		
		//populate the private List<Order>. works like an init
		setOrdersInCurrentMonthAndYear(monthInt, year);
		
		//calculate results
		String numCarsSold = getNumCarsSold();
		String numOrders = getNumOrders();
		String numCustomers = getNumCustomers();
		String totalRevenue = getTotalRevenue();
		
		
		//add results as a row to csv file
		String[] data = {numCarsSold, numOrders, numCustomers, totalRevenue};
		writer.writeNext(data);
			
		writer.close();
	}
	
	
	public void setOrdersInCurrentMonthAndYear(int month, int year) {
		List<Order> orders = orderDao.findAll();
		
		for(Order o : orders) {
			//add orders that are within current month and year
			if((month == o.getPurchaseDate().getMonthValue()) && (year == o.getPurchaseDate().getYear())) {
				ordersInCurrentMonthAndYear.add(o);
			}
		}
	}
	
	
	public String getNumCarsSold() {	
		List<Integer> orderIds = new ArrayList<>();
		
		for(Order o : ordersInCurrentMonthAndYear) {
			orderIds.add(o.getOrderId());  //add orderIds to an arraylist
		}

		int totalNumberCars = 0;
		for(int i=0; i<orderIds.size(); i++) {
			//using custom-made jpa method
			List<OrderItem> matchingItems = orderItemDao.findAllByOrderId(orderIds.get(i));

			totalNumberCars = totalNumberCars + matchingItems.size();
		}
		
		//return totalNumberCars as a string
		return Integer.toString(totalNumberCars);
	}
	
	
	public String getNumOrders() {
		return Integer.toString(ordersInCurrentMonthAndYear.size());
	}
	
	
	public String getNumCustomers() {	
		HashSet<Integer> customers = new HashSet<>();
		
		for(int i=0; i<ordersInCurrentMonthAndYear.size(); i++) {
			if(!customers.contains(ordersInCurrentMonthAndYear.get(i).getUserId())) {
				customers.add(ordersInCurrentMonthAndYear.get(i).getUserId());
			}
		}
		
		return Integer.toString(customers.size()); 
	}
	
	
	public String getTotalRevenue() {
		for(int i=0; i<ordersInCurrentMonthAndYear.size(); i++) {
			totalRevenue = totalRevenue + ordersInCurrentMonthAndYear.get(i).getTotalPrice();
		}
		
		return Integer.toString(totalRevenue);
	}
	

}