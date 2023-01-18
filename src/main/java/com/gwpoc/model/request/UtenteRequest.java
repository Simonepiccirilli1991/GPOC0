package com.gwpoc.model.request;

import org.springframework.lang.NonNull;

public class UtenteRequest {

	private String nome;
	private String cognome;
	private String cellulare;
	private String cf;
	private String channel;
	private String bancaId;
	private String mail;
	private String bt;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBancaId() {
		return bancaId;
	}
	public void setBancaId(String bancaId) {
		this.bancaId = bancaId;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	
	
	
	
}
