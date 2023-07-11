package br.com.pontoemdia.model.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.repository.PontoRepository;
import br.com.pontoemdia.service.calculoHoras.HorasExtrasMesAtual;
import br.com.pontoemdia.service.calculoHoras.HorasTrabalhadasDiaAnterior;
import br.com.pontoemdia.service.calculoHoras.HorasTrabalhadasMesAtual;
import br.com.pontoemdia.service.calculoHoras.HorasTrabalhadasSemanaAtual;
import br.com.pontoemdia.web.form.BuscarPontosUsuarioForm;

/**
 * 
 * @author Guilherme2800
 *
 */
@Service
public class PontoService {

	private SimpleDateFormat formatacaoSemdia = new SimpleDateFormat("yyyy-MM");
	private SimpleDateFormat formatacaoSemHora = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat formatacaoComHora = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

	@Autowired
	private PontoRepository pontoRepository;
	
	public String registrarPonto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Usuario user = (Usuario) req.getSession().getAttribute("usuario");
		this.registrarPonto(user);
		return "redirect:entrada?acao=ExibirRegistrar";
	}
	
	public void registrarPonto(Usuario user) {
		Date date = new Date();
		this.inserirPonto(date, user);
	}
	
	public String buscarHistoricosUsuarioCorrente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SimpleDateFormat sdfDatabase = new SimpleDateFormat("yyyy-MM-dd");
		
		BuscarPontosUsuarioForm form = (BuscarPontosUsuarioForm) req.getSession().getAttribute("scopedTarget.buscarPontosUsuarioForm");

		String dataInicialString = form.getDataInicial();
		String dataFinalString = form.getDataFinal();

		if (dataInicialString == null || dataInicialString.equals("") || (dataFinalString == null || dataFinalString.equals(""))) {
			return "forward:historicoPontos.xhtml";
		}

		Date dataInicial = null;
		Date dataFinal = null;
		try {
			dataInicial = sdfDatabase.parse(dataInicialString);
			dataFinal = sdfDatabase.parse(dataFinalString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat sdfInterface = new SimpleDateFormat("dd/MM/YYYY");
		
		List<Ponto> pontosUsuario = this.buscarHistoricoDoUsuario((Usuario) req.getSession().getAttribute("usuario"), dataInicial, dataFinal);
		req.setAttribute("listaPontos", pontosUsuario);
		req.setAttribute("dataInicio", sdfInterface.format(dataInicial));
		req.setAttribute("dataFinal", sdfInterface.format(dataFinal));
		return "forward:historicoPontos.xhtml";
	}

	private void inserirPonto(Date dataHoraPonto, Usuario user) {

		String coluna = "";

		Ponto pontoCorrente = pontoRepository.findByUserIdAndDate(user.getId(), formatacaoSemHora.format(new Date()));
		
		if (pontoCorrente == null || pontoCorrente.getDataEntrada() == null) {
			coluna = "data_hora_entrada";
		} else if (pontoCorrente.getDataAlmoco() == null) {
			coluna = "data_hora_almoco";
		} else if (pontoCorrente.getDataVoltaAlmoco() == null) {
			coluna = "data_hora_volta_almoco";
		} else if (pontoCorrente.getDataSaida() == null) {
			coluna = "data_hora_saida";
		}
		
		if (coluna != null) {

			switch (coluna) {

			case "data_hora_entrada":
				Ponto ponto = new Ponto();
				ponto.setData(new Date());
				ponto.setDataEntrada(dataHoraPonto);
				ponto.setUser_id(user.getId());
				pontoRepository.save(ponto);
				break;
			case "data_hora_almoco":
				pontoCorrente.setDataAlmoco(dataHoraPonto);
				pontoRepository.save(pontoCorrente);
				break;
			case "data_hora_volta_almoco":
				pontoCorrente.setDataVoltaAlmoco(dataHoraPonto);
				pontoRepository.save(pontoCorrente);
				break;
			case "data_hora_saida":
				pontoCorrente.setDataSaida(dataHoraPonto);
				pontoRepository.save(pontoCorrente);
				break;
			default:
				break;
			}

		}

	}

	public Ponto validarHorarios(Ponto ponto) {

		if (ponto.getDataAlmoco() == null) {
			ponto.setDataAlmoco(ponto.getDataEntrada());
		}

		if (ponto.getDataVoltaAlmoco() == null) {
			ponto.setDataVoltaAlmoco(ponto.getDataAlmoco());
		}

		if (ponto.getDataSaida() == null) {
			ponto.setDataSaida(ponto.getDataVoltaAlmoco());
		}

		return ponto;

	}

	public List<Ponto> buscarHistoricoDoUsuario(Usuario user, Date dataInicial, Date dataFinal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
		String startDate = sdf.format(dataInicial);
		String endDate = sdf.format(dataFinal);
		
		return pontoRepository.findByUserIdAndStartDateAndEndDate(user.getId(), startDate, endDate);
	}

	public List<Ponto> buscarPontoSemanaAtualDoUsuario(Usuario user) {

		Date dataAtual = new Date();

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(dataAtual);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date startWeek = calendar.getTime();

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		Date endWeek = calendar.getTime();

		return this.buscarHistoricoDoUsuario(user, startWeek, endWeek);
	}
	
	public List<Ponto> buscarPontosMesAtualDoUsuario(Usuario user) {

		LocalDateTime month = LocalDateTime.now();
		
		Date startMonth = Date.from(month.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant());
		
		Date endMonth = Date.from(month.withDayOfMonth(YearMonth.of(month.getYear(), month.getMonth()).lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant());

		return this.buscarHistoricoDoUsuario(user, startMonth, endMonth);
	}
	
	public Ponto buscarPontoDiaAnteriorDoUsuario(Usuario user) {

		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, -1);

		return pontoRepository.buscarPontoDiaAnteriorDoUsuario(user.getId(), formatacaoSemHora.format(data.getTime()));

	}
	
	public String buildDashbordUserCurrent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HorasTrabalhadasDiaAnterior horasTrabalhadasDiaAnterior = new HorasTrabalhadasDiaAnterior(this);
		
		req.setAttribute("horasExtrasMes", new HorasExtrasMesAtual(this).calcularHoras(req));
		req.setAttribute("horasTrabalhadasMes", new HorasTrabalhadasMesAtual(this).calcularHoras(req));
		req.setAttribute("intervaloMes", new HorasTrabalhadasMesAtual(this).intervalo());
		
		req.setAttribute("horasTrabalhadasSemana", new HorasTrabalhadasSemanaAtual(this).calcularHoras(req));
		req.setAttribute("intervaloSemana", new HorasTrabalhadasSemanaAtual(this).intervalo());
		
		req.setAttribute("horasTrabDiaAnterior", horasTrabalhadasDiaAnterior.calcularHoras(req));
		req.setAttribute("intervaloDia", horasTrabalhadasDiaAnterior.intervalo());
		
		req.setAttribute("diasDeAtraso", this.buscarDiasDeAtrasoUsuario(req));
		
		return "forward:dashbord.xhtml";
	}
	
	public List<String> buscarDiasDeAtrasoUsuario(HttpServletRequest req){
		Usuario user = (Usuario) req.getSession().getAttribute("usuario");
		List<Ponto> lista_pontos = this.buscarPontosMesAtualDoUsuario(user);
		ArrayList<String> lista_pontos_atraso = new ArrayList<>();
		
		if(lista_pontos.size() > 0) {
			SimpleDateFormat formatacaoComHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			double hora_entrada_grupo;
			if(user.getGrupo() == null) {
				hora_entrada_grupo = 8;
			} else {
				hora_entrada_grupo = user.getGrupo().getHorarioEntrada();
			}
			for(Ponto ponto : lista_pontos) {
				double hora_entrada_ponto = ponto.getDataEntrada().getHours();
				double minuto_entrada_ponto = ponto.getDataEntrada().getMinutes() / 100;
				
				if((hora_entrada_ponto + minuto_entrada_ponto) > hora_entrada_grupo)
				{
					lista_pontos_atraso.add(formatacaoComHora.format(ponto.getDataEntrada()));
				}
				
			}
		}
		return lista_pontos_atraso;	
	}

}
