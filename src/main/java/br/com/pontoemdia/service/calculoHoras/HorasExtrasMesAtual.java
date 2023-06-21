package br.com.pontoemdia.service.calculoHoras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Grupo;
import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.PontoService;
import br.com.pontoemdia.repository.UsuarioRepository;


@Service
public class HorasExtrasMesAtual extends CalcularHorasTrabalhadas {

	UsuarioRepository usuarioRepository;
	
	public HorasExtrasMesAtual(PontoService pontoService) {
		super(pontoService);
	}

	/**
	 * M�todo que calcula as horas extras trabalhadas no m�s atual
	 * do usu�rio autenticado no sistema
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public String calcularHoras(HttpServletRequest req) {
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
		
		List<Ponto> pontosUsuario = pontoService.buscarPontosMesAtualDoUsuario((usuario));

		return gerarHoras(pontosUsuario, usuario.getGrupo());

	}
	
	/**
	 * M�todo que calcula as horas extras trabalhadas no m�s atual
	 * Do usu�rio passado como par�metro
	 * 
	 * @param user
	 * @return
	 */
	public String calcularHoras(Usuario user) {
		List<Ponto> pontosUsuario = pontoService.buscarPontosMesAtualDoUsuario(user);

		return gerarHoras(pontosUsuario, user.getGrupo());

	}
	
	/**
	 * M�todo que calcula as horas extras trabalhadas no intervalo de tempo informado,
	 * do usu�rio passado como par�metro
	 * 
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @return
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

		return gerarHoras(pontosUsuario, user.getGrupo());
	}
	
	/**
	 * M�todo que realiza o processamento das horas trabalhadas dos pontos do usu�rio.
	 * 
	 * @param pontosUsuario
	 * @return
	 */
	private String gerarHoras(List<Ponto> pontosUsuario, Grupo grupo) {
		
		int qntMinutosNoDia = 480;
		if(grupo != null) {
			qntMinutosNoDia = grupo.getHorasDiariasTrabalhadas() * 60;
		}
		
		long totalMinutos = 0l;
		Integer totalHorasExtras = 0;

		for (Ponto ponto : pontosUsuario) {
			pontoService.validarHorarios(ponto);
			totalMinutos += calcularMinutos(ponto);
		}

		totalMinutos *= -1;
		
		for(int i = 0; i < pontosUsuario.size(); i++) {
			totalMinutos -= qntMinutosNoDia;
		}
		
		while(totalMinutos >= 60) {
			totalHorasExtras++;
			totalMinutos -= 60;
		}
		
		if(totalMinutos < 0) {
			totalMinutos = 0;
		}
		
		return totalHorasExtras.toString() + ":" + (totalMinutos == 0 ? "00" : totalMinutos) + "";
		
	}

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
