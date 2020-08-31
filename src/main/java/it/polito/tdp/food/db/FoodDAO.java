package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Condiment;

public class FoodDAO {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public void getVertex (Integer calorie, Map<Integer, Condiment> idMap){
		String sql = "select distinct condiment_code, display_name, condiment_calories\n" + 
				"from condiment\n" + 
				"where condiment_calories<?";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Condiment c = new Condiment (res.getInt("condiment_code"), res.getString("display_name"), res.getDouble("condiment_calories"));
				if(!idMap.containsKey(res.getInt("condiment_code"))) {
					idMap.put(c.getCondiment_id(), c);
				}
			}
			conn.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Adiacenza> getEdges (Map <Integer, Condiment> idMap){
		String sql = "select c1.condiment_code, c2.condiment_code, count(distinct c1.food_code) as peso\n" + 
				"from food_condiment c1, food_condiment c2\n" + 
				"where c1.condiment_code <> c2.condiment_code\n" + 
				"and c1.condiment_code > c2.condiment_code\n" + 
				"and c1.food_code = c2.food_code\n" + 
				"group by c1.condiment_code, c2.condiment_code";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(idMap.containsKey(res.getInt("c1.condiment_code")) && idMap.containsKey(res.getInt("c2.condiment_code"))){
					Adiacenza a = new Adiacenza(idMap.get(res.getInt("c1.condiment_code")), idMap.get(res.getInt("c2.condiment_code")), res.getInt("peso"));
					result.add(a);
				}
				
			}
			conn.close();
			return result;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

