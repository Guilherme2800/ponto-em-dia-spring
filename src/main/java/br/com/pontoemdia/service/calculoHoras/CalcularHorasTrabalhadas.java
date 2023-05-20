package br.com.pontoemdia.service.calculoHoras;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.service.PontoService;

/**
 * 
 * @author Guilherme2800
 *
 */
public abstract class CalcularHorasTrabalhadas {

	protected PontoService pontoService;
	
	public CalcularHorasTrabalhadas(PontoService pontoService) {
		this.pontoService = pontoService;
	}
	
	public abstract String calcularHoras(HttpServletRequest req);
	
	public abstract String intervalo();
	
	public Long calcularMinutos(Ponto ponto) {
		
		Long totalMilesegundos = ponto.getDataEntrada().getTime() - ponto.getDataAlmoco().getTime()
				+ ponto.getDataVoltaAlmoco().getTime() - ponto.getDataSaida().getTime();
		
		TimeUnit time = TimeUnit.MINUTES;
		return time.convert(totalMilesegundos, TimeUnit.MILLISECONDS);
		
	}
	
	public String gerarHorasTrabalhadas(Long minutosTrabalhados) {
		
		Long totalHoras = 0l;
		
		if(minutosTrabalhados < 0) {
			minutosTrabalhados *= -1;
		}
		
		while (minutosTrabalhados >= 60) {
			minutosTrabalhados -= 60;
			totalHoras++;
		}
		
		if(minutosTrabalhados < 10) {
			return totalHoras.toString() + ":" + (minutosTrabalhados == 0 ? "00" : "0"+minutosTrabalhados) + "";
		}

		return totalHoras.toString() + ":" + (minutosTrabalhados == 0 ? "00" : minutosTrabalhados) + "";
	}
	
}
