package com.gwpoc.fragment.iwdb;

import java.util.List;

import com.gwpoc.fragment.model.IwdbBaseResponse;
import com.gwpoc.fragment.model.Ordini;



public class OrdiniIwResponse  extends IwdbBaseResponse{

	private Ordini ordine;
	private List<Ordini> ordini;
	
	public Ordini getOrdine() {
		return ordine;
	}
	public void setOrdine(Ordini ordine) {
		this.ordine = ordine;
	}
	public List<Ordini> getOrdini() {
		return ordini;
	}
	public void setOrdini(List<Ordini> ordini) {
		this.ordini = ordini;
	}
}
