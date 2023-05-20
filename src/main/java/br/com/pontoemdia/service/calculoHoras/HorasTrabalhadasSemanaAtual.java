package br.com.pontoemdia.service.calculoHoras;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.PontoService;


@Service
public class HorasTrabalhadasSemanaAtual extends CalcularHorasTrabalhadas {
	
	public HorasTrabalhadasSemanaAtual(PontoService pontoService) {
		super(pontoService);
	}

	@Override
	public String calcularHoras(HttpServletRequest req) {

		List<Ponto> pontosUsuario = pontoService.buscarPontoSemanaAtualDoUsuario((Usuario) req.getSession().getAttribute("usuario"));

		long totalMinutos = 0l;

		for (Ponto ponto : pontosUsuario) {
			pontoService.validarHorarios(ponto);
			totalMinutos += this.calcularMinutos(ponto);
		}

		return gerarHorasTrabalhadas(totalMinutos);
	}

	@Override
	public String intervalo() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date dataAtual = new Date();

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(dataAtual);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String inicioSemana = df.format(calendar.getTime());

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		String fimSemana = df.format(calendar.getTime());

		return inicioSemana + " - " + fimSemana;
	}

}
