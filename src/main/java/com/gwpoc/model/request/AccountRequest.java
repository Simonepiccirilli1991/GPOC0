package com.gwpoc.model.request;

import org.springframework.lang.NonNull;


public class AccountRequest {
	
	@NonNull
	private String bt;
	private Double importo; // usato anche in fase di creazione per saldo
	
	// per creazione acc
	private String tipoAccount;
	private Double debito;

	private String codiceConto;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getTipoAccount() {
		return tipoAccount;
	}
	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}
	public Double getDebito() {
		return debito;
	}
	public void setDebito(Double debito) {
		this.debito = debito;
	}

	public String getCodiceConto() {
		return codiceConto;
	}

	public void setCodiceConto(String codiceConto) {
		this.codiceConto = codiceConto;
	}

	@Override
	public String toString() {
		return "AccountRequest [bt=" + bt + ", importo=" + importo + ", tipoAccount=" + tipoAccount + ", debito="
				+ debito + "]";
	}
	
	
	
	
}
