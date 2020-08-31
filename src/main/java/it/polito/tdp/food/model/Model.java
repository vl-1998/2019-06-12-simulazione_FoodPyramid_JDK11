package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Adiacenza;
import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private FoodDAO dao;
	private Graph<Condiment,DefaultWeightedEdge> grafo;
	private Map<Integer, Condiment> idMap;
	private List<Condiment> best;
	private Double caMax;
	
	public Model () {
		this.dao = new FoodDAO();
	}
	
	public void creaGrafo(Integer calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<>();
		
		this.dao.getVertex(calorie, idMap);
		
		for(Condiment c: this.idMap.values()) {
			this.grafo.addVertex(c);
		}
		
		for(Adiacenza a : this.dao.getEdges(idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getPeso());
		}
	}
	
	public List<Condiment> getVertex(){
		List<Condiment> vertici = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
		
	public List<DefaultWeightedEdge> getEdge(){
		List<DefaultWeightedEdge> archi = new ArrayList<>(this.grafo.edgeSet());
		return archi;
	}
	
	public List<Ingredienti> getIngredienti (){
		List<Ingredienti> result = new ArrayList<>();
		for(Condiment c: this.getVertex()) {
			Ingredienti i = new Ingredienti (c.getCondiment_id(), c.getDisplay_name(), c.getCondiment_calories(), this.getPeso(c));
			result.add(i);
		}
		
		Collections.sort(result);
		return result;
	}
	
	private Integer getPeso(Condiment c) {
		List<Condiment> successori = Graphs.successorListOf(this.grafo, c);
		Integer peso=0;
		
		for(Condiment o: successori) {
			peso = (int) (peso + this.grafo.getEdgeWeight(this.grafo.getEdge(c, o)));
		}
		
		return peso;
	}
	
	
	public List<String> ricorsiva (Condiment partenza){
		this.best = new ArrayList<>();
		List<String> result = new ArrayList<>();
		this.caMax = 0.0;
		
		List<Condiment> parziale = new ArrayList<>();
		Double pTemp = 0.0;
		parziale.add(partenza);
		
		cerca(parziale, pTemp,partenza);
		
		for(Condiment c: this.best) {
			result.add(c.getDisplay_name());
		}
		
		
		return result;
	}

	private void cerca(List<Condiment> parziale, Double pTemp, Condiment partenza) {
		List<Condiment> successori = new ArrayList<>();
		for(Condiment c: Graphs.neighborListOf(this.grafo, partenza)) {
			successori.add(c);
		}
		
		
		if(parziale.size()>best.size() && pTemp>caMax) {
			this.best = new ArrayList<>(parziale);
			caMax = pTemp;
		}
		
		
		for(Condiment s : successori) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				pTemp = pTemp + this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, s));
				cerca(parziale, pTemp, s);
				parziale.remove(s);
				pTemp = pTemp - this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, s));
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

}
