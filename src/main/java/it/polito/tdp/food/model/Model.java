package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	
	private List<String> migliore;
	private int pesoCammino;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public String creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		this.vertici = this.dao.getVertici(calorie);
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		//archi
		List<Adiacenza> archi = this.dao.getAdiacenze(vertici, calorie);
		for(Adiacenza a : archi)
			Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
		
		return String.format("Grafo creato con %d vertici e %d archi\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<String> getVertici() {
		return vertici;
	}

	public List<Adiacenza> getCorrelati(String porzione) {
		List<Adiacenza> correlati = new ArrayList<Adiacenza>();
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione);
		
		for(String vicino : vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(porzione, vicino);
			Adiacenza a = new Adiacenza(porzione, vicino, this.grafo.getEdgeWeight(e));
			correlati.add(a);
		}
		return correlati;
	}

	public List<String> getCammino(String porzione, int passi) {
		this.migliore = new ArrayList<String>();
		this.pesoCammino = 0;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(porzione);
		
		this.cerca(parziale, passi, 1);
		
		return migliore;
	}

	private void cerca(List<String> parziale, int passi, int L) {
		//casi terminali
		if(L == passi+1) {
			int pesoParziale = this.peso(parziale);
			
			if(pesoParziale > this.pesoCammino) {
				this.migliore = new ArrayList<String>(parziale);
				this.pesoCammino = pesoParziale;
			}
			return;
		}
		
		
		String ultimo = parziale.get(L-1);
		List<String> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for(String vicino : vicini) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				
				cerca(parziale, passi, L+1);
				
				//backtracking
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	private int peso(List<String> parziale) {
		int p = 0;
		for(int i=0; i<parziale.size(); i++) {
			p += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return p;
	}
	
	public int getPesoCammino() {
		return pesoCammino;
	}

}
