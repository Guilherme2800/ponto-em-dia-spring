package br.com.pontoemdia.model.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.repository.PontoRepository;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		BuscarPontosUsuarioForm form = (BuscarPontosUsuarioForm) req.getSession().getAttribute("scopedTarget.buscarPontosUsuarioForm");

		String dataInicialString = form.getDataInicial();
		String dataFinalString = form.getDataFinal();

		if (dataInicialString == null || dataInicialString.equals("") || (dataFinalString == null || dataFinalString.equals(""))) {
			return "forward:historicoPontos.jsp";
		}

		Date dataInicial = null;
		Date dataFinal = null;
		try {
			dataInicial = sdf.parse(dataInicialString);
			dataFinal = sdf.parse(dataFinalString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Ponto> pontosUsuario = this.buscarHistoricoDoUsuario((Usuario) req.getSession().getAttribute("usuario"), dataInicial, dataFinal);
		req.setAttribute("listaPontos", pontosUsuario);
		req.setAttribute("dataInicio", sdf.format(dataInicial));
		req.setAttribute("dataFinal", sdf.format(dataFinal));
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
		return pontoRepository.findByUserIdAndStartDateAndEndDate(user.getId(), dataInicial, dataFinal);
	}


}
