package br.com.pontoemdia.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * 
 * @author Guilherme2800
 *
 */
@Entity
@Table(name = "ponto")
public class Ponto {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	private Date dataEntrada;
	private Date dataAlmoco;
	private Date dataVoltaAlmoco;
	private Date dataSaida;
	private Long user_id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataAlmoco() {
		return dataAlmoco;
	}

	public void setDataAlmoco(Date dataAlmoco) {
		this.dataAlmoco = dataAlmoco;
	}

	public Date getDataVoltaAlmoco() {
		return dataVoltaAlmoco;
	}

	public void setDataVoltaAlmoco(Date dataVoltaAlmoco) {
		this.dataVoltaAlmoco = dataVoltaAlmoco;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}	
	
	
}
