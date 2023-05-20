
function editar(e) {

	var linha = $(e).closest("tr");
	var user_id = linha.find("td:eq(0)").text().trim();
	var data = linha.find("td:eq(1)").text().trim();
	var entrada = linha.find("td:eq(2)").text().trim();
	var almoco = linha.find("td:eq(3)").text().trim();
	var voltaAlmoco = linha.find("td:eq(4)").text().trim();
	var saida = linha.find("td:eq(5)").text().trim();

	$("#user_id_update").val(user_id);
	$("#user_id_remove").val(user_id);
	$("#data_update").val(data);
	$("#data_remove").val(data);
	$("#horaEntrada").val(entrada);
	$("#horaAlmoco").val(almoco);
	$("#horaVoltaAlmoco").val(voltaAlmoco);
	$("#horaSaida").val(saida);

}

$(document).ready(function() {
	$('#tabelaHistorico').DataTable(
		{
			"language": {
			"lengthMenu": "Registros por página:  _MENU_",
			"zeroRecords": "Nada encontrado - Utilize o formulário acima",
			"info": "Mostrando página _PAGE_ de _PAGES_",
			"infoEmpty": "Nenhum registro disponível",
			"infoFiltered": "(filtrado de _MAX_ registros no total)",
			"search": "Pesquisar",
			"paginate": {
				"next": "Próximo",
				"previous": "Anterior",
				"first": "Primeiro",
				"last": "Último"
			},
		}
		});
});