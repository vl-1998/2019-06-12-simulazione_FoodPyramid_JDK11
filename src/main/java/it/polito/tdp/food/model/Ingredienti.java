package it.polito.tdp.food.model;

public class Ingredienti implements Comparable<Ingredienti> {
	private Integer id;
	private String ingredient_name;
	private Double calorie;
	private Integer n_cibi;
	
	/**
	 * @param id
	 * @param ingredient_name
	 * @param calorie
	 * @param n_cibi
	 */
	public Ingredienti(Integer id, String ingredient_name, Double calorie, Integer n_cibi) {
		super();
		this.id = id;
		this.ingredient_name = ingredient_name;
		this.calorie = calorie;
		this.n_cibi = n_cibi;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredienti other = (Ingredienti) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getIngredient_name() {
		return ingredient_name;
	}


	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
	}


	public Double getCalorie() {
		return calorie;
	}


	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}


	public Integer getN_cibi() {
		return n_cibi;
	}


	public void setN_cibi(Integer n_cibi) {
		this.n_cibi = n_cibi;
	}


	@Override
	public String toString() {
		return "Nome: " + ingredient_name + ", calorie=" + calorie + ", n.cibi=" + n_cibi;
	}
	@Override
	public int compareTo(Ingredienti o) {
		// TODO Auto-generated method stub
		return -this.getCalorie().compareTo(o.getCalorie());
	}
	
	
}




























