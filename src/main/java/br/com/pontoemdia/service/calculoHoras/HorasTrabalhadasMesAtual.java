package br.com.pontoemdia.service.calculoHoras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.PontoService;

@Service
public class HorasTrabalhadasMesAtual extends CalcularHorasTrabalhadas {

	public HorasTrabalhadasMesAtual(PontoService pontoService) {
		super(pontoService);
	}


	/**
	 * M�todo que calcula as horas trabalhadas no m�s atual, do usu�rio autenticado no sistema
	 * 
	 * @param req
	 * @return Retorna a quantidade de horas trabalhadas do usu�rio
	 */
	@Override
	public String calcularHoras(HttpServletRequest req) {

		List<Ponto> pontosUsuario = pontoService.buscarPontosMesAtualDoUsuario((Usuario) req.getSession().getAttribute("usuario"));

		long totalMinutos = 0l;

		for (Ponto ponto : pontosUsuario) {
			pontoService.validarHorarios(ponto);
			totalMinutos += calcularMinutos(ponto);
		}

		return gerarHorasTrabalhadas(totalMinutos);
	}
	

	/**
	 * M�todo que calcula as horas trabalhadas no m�s atual, do usu�rio informado no parametro
	 * 
	 * @param user
	 * @return Retorna a quantidade de horas trabalhadas do usu�rio
	 */
	public String calcularHoras(Usuario user) {

		List<Ponto> pontosUsuario = pontoService
				.buscarPontosMesAtualDoUsuario(user);

		long totalMinutos = 0l;

		for (Ponto ponto : pontosUsuario) {
			pontoService.validarHorarios(ponto);
			totalMinutos += calcularMinutos(ponto);
		}

		return gerarHorasTrabalhadas(totalMinutos);
	}
	
	/**
	 * M�todo que calcula as horas trabalhadas no intervalo de tempo informado, do usu�rio passado
	 * como parametro
	 * 
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @return Retorna a quantidade de horas trabalhadas do usu�rio
	 */
	public String calcularHoras(Usuario user, String startDate, String endDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date start = null;
		Date end = null;
		try {
			end = sdf.parse(endDate);
			start = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Ponto> pontosUsuario = pontoService.buscarHistoricoDoUsuario(user, start, end);

		long totalMinutos = 0l;

		for (Ponto ponto : pontosUsuario) {
			pontoService.validarHorarios(ponto);
			totalMinutos += calcularMinutos(ponto);
		}

		return gerarHorasTrabalhadas(totalMinutos);
	}

	/**
	 * @return intervalo de tempo do m�s atual
	 */
	@Override
	public String intervalo() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String inicioMes = df.format(cal.getTime());

		cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String fimMes = df.format(cal.getTime());

		return inicioMes + " - " + fimMes;
	}

}
