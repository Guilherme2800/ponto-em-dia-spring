package br.com.pontoemdia.service.calculoHoras;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.PontoService;

@Service
public class HorasTrabalhadasDiaAnterior extends CalcularHorasTrabalhadas {

	public HorasTrabalhadasDiaAnterior(PontoService pontoService) {
		super(pontoService);
	}
	
	String horas;

	@Override
	public String calcularHoras(HttpServletRequest req) {

		long totalMinutos = 0l;

		Ponto ponto = pontoService.buscarPontoDiaAnteriorDoUsuario((Usuario) req.getSession().getAttribute("usuario"));

		if(ponto != null) {
			pontoService.validarHorarios(ponto);
		}

		horas = gerarHorasTrabalhadas(totalMinutos);
		
		return horas;
	}

	@Override
	public String intervalo() {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, -1);

		return new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());
	}
	
	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	@Override
	public String toString() {
		return "teste" + this.horas;
	}
	
	

}
