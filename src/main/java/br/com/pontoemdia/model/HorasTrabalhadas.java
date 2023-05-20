package br.com.pontoemdia.model;

/**
 * 
 * @author Guilherme2800
 *
 */
public class HorasTrabalhadas {

	private Long id_user;
	private String nome_user;
	private String cargo_user;
	private String totalHorasMesAtual;
	private String totalHorasExtrasMesAtual;
	private String intervalo;
	
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	public String getNome_user() {
		return nome_user;
	}
	public void setNome_user(String nome_user) {
		this.nome_user = nome_user;
	}
	public String getCargo_user() {
		return cargo_user;
	}
	public void setCargo_user(String cargo_user) {
		this.cargo_user = cargo_user;
	}
	public String getIntervalo() {
		return intervalo;
	}
	public void setIntervalo(String intervalo) {
		this.intervalo = intervalo;
	}
	public String getTotalHorasMesAtual() {
		return totalHorasMesAtual;
	}
	public void setTotalHorasMesAtual(String totalHorasMesAtual) {
		this.totalHorasMesAtual = totalHorasMesAtual;
	}
	public String getTotalHorasExtrasMesAtual() {
		return totalHorasExtrasMesAtual;
	}
	public void setTotalHorasExtrasMesAtual(String totalHorasExtrasMesAtual) {
		this.totalHorasExtrasMesAtual = totalHorasExtrasMesAtual;
	}
	
	
	
}
