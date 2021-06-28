package it.polito.tdp.food.model;

import java.util.List;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public List<String> getPortionName() {
		return this.dao.getPortionName();
	}
	
}
