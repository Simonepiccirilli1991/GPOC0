package com.gwpoc.fragment.model;

public class Account {

	private long id;
	private String codiceconto;
	private Double saldoattuale;
	private Double debito;
	private String tipoConto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodiceconto() {
		return codiceconto;
	}
	public void setCodiceconto(String codiceconto) {
		this.codiceconto = codiceconto;
	}
	public Double getSaldoattuale() {
		return saldoattuale;
	}
	public void setSaldoattuale(Double saldoattuale) {
		this.saldoattuale = saldoattuale;
	}
	public Double getDebito() {
		return debito;
	}
	public void setDebito(Double debito) {
		this.debito = debito;
	}
	public String getTipoConto() {
		return tipoConto;
	}
	public void setTipoConto(String tipoConto) {
		this.tipoConto = tipoConto;
	}
	
	
}
