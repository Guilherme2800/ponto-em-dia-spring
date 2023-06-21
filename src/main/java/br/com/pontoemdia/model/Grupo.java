package br.com.pontoemdia.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "grupo")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@ManyToOne
	private Usuario admin;

	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Usuario> usuarios;

	private double horarioEntrada;

	private double horariosaida;

	private int horasDiariasTrabalhadas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public double getHorarioEntrada() {
		return horarioEntrada;
	}

	public void setHorarioEntrada(double horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

	public double getHorariosaida() {
		return horariosaida;
	}

	public void setHorariosaida(double horariosaida) {
		this.horariosaida = horariosaida;
	}

	public int getHorasDiariasTrabalhadas() {
		return horasDiariasTrabalhadas;
	}

	public void setHorasDiariasTrabalhadas(int horasDiariasTrabalhadas) {
		this.horasDiariasTrabalhadas = horasDiariasTrabalhadas;
	}

}
