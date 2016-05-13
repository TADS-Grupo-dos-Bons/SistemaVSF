<%-- 
    Document   : conta
    Created on : 10/04/2016, 21:21:35
    Author     : Junior
--%>

<%@page import="Classes.Format"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.ContaDAO"%>
<%@page import="Classes.Conta"%>
<%@page import="Classes.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Internet Banking - VSF</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/tudo.css">
	<link type="text/css" rel="stylesheet" href="css/bootstrap-switch.css">
	<script src="js/jquery-2.2.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/bootstrap-switch.js"></script>
        <link rel="stylesheet" href="js/jquery-ui-1.11.4.custom/jquery-ui.min.css">
        <script src="js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
        <script src="js/jquery.mask.js"></script>
    </head>
    <% 
        HttpSession sessionCli = request.getSession();
        Cliente cliente = (Cliente) sessionCli.getAttribute("Cliente");
        
        Format frm = new Format();
        
        if(cliente != null ){
    %>
    <body>
        <nav class="navbar navbar-default navbar-principal navbar-fixed-top">
	<div class="container-fluid">
	
		<div class="navbar-header logo">
			<div >

				<h2 class="nome_logo">Internet Banking - VSF</h2>

			</div>

		</div>

		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<div class="nav navbar-nav navbar-right" style="margin-right: 20px; margin-top: 25px;">
                            <span><% out.print(cliente.getNome()); %>, </span><a href="ProcessaLogout">Sair</a>
			</div>
		</div>

  </div>
</nav>

	<div class="container" >
	<br>
		<div class="panel panel-default conta">
			<div class="panel-heading ">
				<h4 class="panel-title">
					<a class="accordion-toggle " data-toggle="collapse" data-parent="#accordion">
						Conta
					</a>
					<button type="button" onclick="mostramodal()" class="btn btn-primary btn-novo">Novo</button>
				</h4>
			</div>
			<div class="panel-body">
				<table id="lvListaConta" class="table table-bordered table-striped" aria-describedby="dataTable1_info">
					<thead>
						<tr>                      
							<th>
								Agencia  
							</th>
							<th>
								Conta
							</th>
                                                        <th>
								CPF/CNPJ
							</th>
							<th>
								Acoes
							</th>
						</tr>                 
					</thead>
							
					<tbody class="lvContaTbody" role="alert" aria-live="polite" aria-relevant="all" id="tbConteudoLvConta">
						
					</tbody>
				</table>
							
				  
			</div>  
		</div>  

	</div>
        <div class="modal fade" id="modal-novaconta" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Nova Conta</h4>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group" style="padding-left: 25%;">
                                                <label>Tipo:</label><input style="margin-left:10px;" type="radio" name="rblTipo" value="1" checked>Física
                                                                        <input style="margin-left:10px;" type="radio" name="rblTipo" value="2">Jurídica<br><br>
						<label>Agência:</label><input id="txtNovaAgencia"  class="agenciamask" type="text"><br><br>
						<label>Conta:</label><input id="txtNovaConta" style="margin-left:14px;" class="sobrenome" type="text" name="sobrenome"><br><br>
                                                <label id="lblCpfCnpj">CPF:</label><input disabled style="margin-left:27px;" id="txtCpfCnpjNovaConta" value="<% out.println(cliente.getCpf()); %>" class="cpfmask" type="text"><br><br>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				<button type="button" onclick='cadastrarConta();' class="btn btn-primary">Criar</button>
			</div>
		</div>
	</div>
</div>


<!-- Modal OPERACOES -->

<div class="modal fade" id="modal-operacao" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Operacoes</h4>
			</div>
			<div class="modal-body">
				<div>
                                    <div style="display:none;">
                                        <input class="" id="hdnRespostaToken" type="text" name="data" disabled/>
                                        <input class="" id="hdnConta" type="text" name="data" disabled/>
                                    </div>
				  <!-- Nav tabs -->
				  <ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active abaclick"><a href="#saldo" aria-controls="saldo" role="tab" data-toggle="tab">Saldo</a></li>
				    <li role="presentation" class="abaclick"><a href="#extrato" aria-controls="extrato" role="tab" data-toggle="tab">Extrato</a></li>
				    <li role="presentation" class="abaclick"><a href="#transferencia" aria-controls="transferencia" role="tab" data-toggle="tab">Transferencia</a></li>
				    <li role="presentation" class="abaclick"><a href="#saque" aria-controls="saque" role="tab" data-toggle="tab">Saque</a></li>
				    <li role="presentation"><a href="#deposito" aria-controls="deposito" role="tab" data-toggle="tab">Deposito</a></li>
				  </ul>

				  <!-- Tab panes -->
				  <div class="tab-content">

				  	<!-- SALDO -->
				  	<div role="tabpanel" class="tab-pane active" id="saldo">
				  		<div class='row'>
					  		<div class='col-md-8 col-md-offset-3'>
								<br>
								<div class="cel1">
									<label for="cod_analise">Saldo</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="" id="txtTabsaldoSaldo" type="text" name="data" disabled/>
								</div><br>
								<div class="cel1">
									<label for="cod_analise">Limite</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="" id="txtTabsaldoLimite" type="text" name="data" disabled/>
								</div><br>
								<div class="cel1">
									<label for="cod_analise">Data</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="dtmask" id="txtTabsaldodata" type="text" name="data" disabled/>
								</div><br>
							</div>
						</div>
                                            
				  	</div>

				  	<!-- EXTRATO -->
				    <div role="tabpanel" class="tab-pane" id="extrato">
				    	<br>

						<div class='col-md-4 '>
							<label><input type="radio" name="optradio">Completo</label>
						</div>

						<div class='col-md-4'>
							<label><input type="radio" name="optradio">Ultimos 15 dias</label>
						</div>

						<div class='col-md-4'>
							<label><input type="radio" name="optradio">Ultimos 30 dias</label>
						</div>

						<div class='row'>
							<br><br><br>
							<div class='col-md-12 '>
								<table class="table table-bordered table-striped" aria-describedby="dataTable1_info">
									<thead>
										<tr>
											<th>
												Minha Conta
											</th>                        
											<th>
												Conta Destino  
											</th>
                                                                                        <th>
												Valor  
											</th>
											<th>
												Data  
											</th>
											<th>
												Tipo
											</th>
											
										</tr>                 
									</thead>
											
									<tbody id="tbdTransacao" role="alert" aria-live="polite" aria-relevant="all" id="">
									</tbody>
								</table>
							</div>
						</div>
				    </div>

				    <div role="tabpanel" class="tab-pane" id="transferencia">
				    	<form>
							<br>
							<div class='row'>
								<div class='col-md-8 col-md-offset-3'>
									<div class="cel1">
										<label for="cod_analise">Agencia</label>
										<input id="txtTransferenciaAgencia" class="nome" type="text"/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Conta</label>&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtTransferenciaConta" class="nome" type="text" name="data"/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Data</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtTransferenciaData" class="nome" type="text" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Valor</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtTransferenciaValor" class="nome" type="text" name="data"/>
									</div><br>
								</div>	
							</div>							

						</form>
                                            <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                                    <button type="button" onclick='getToken(1);' class="btn btn-primary">Transferir</button>
                                            </div>
				    </div>

				    <div role="tabpanel" class="tab-pane" id="saque">
                                            <form>
							<br>
							<div class='row'>
								<div class='col-md-8 col-md-offset-3'>
									<br>
									<div class="cel1">
										<label for="cod_analise">Agencia</label>
										<input id="txtSaqueAgencia" class="nome" type="text" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Conta</label>&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtSaqueConta" class="nome" type="text" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Data</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtSaqueData" class="nome" type="text" name="data" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Valor</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input id="txtSaqueValor" class="nome" type="text" name="data"/>
									</div><br>
								</div>								
							</div>
						</form>
                                            <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                                    <button type="button" onclick='getToken(2);' class="btn btn-primary">Sacar</button>
                                            </div>
				    </div>

				    <div role="tabpanel" class="tab-pane" id="deposito">
                                                <form>
							<br>
							<div class='row'>
								<div class='col-md-8 col-md-offset-3'>
									<br>
									<div class="cel1">
										<label for="cod_analise">Agencia</label>
										<input class="agenciamask" id="txtDepositoAgencia" type="text" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Conta</label>&nbsp;&nbsp;&nbsp;&nbsp;
										<input class="contamask" id="txtDepositoConta" type="text" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Data</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input class="datamask" id="txtDepositoData" type="text" name="data" disabled/>
									</div><br>
									<div class="cel1">
										<label for="cod_analise">Valor</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input class="nome" id="txtDepositoValor" type="text" name="data"/>
									</div><br>
								</div>								
							</div>
						</form>
                                            <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                                    <button type="button" onclick='getToken(3);' class="btn btn-primary">Depositar</button>
                                            </div>
				    </div>

				  </div>

				</div>

			</div>
		</div>
	</div>
</div>
        <script type="text/javascript">
		$( document ).ready(function() {
                        carregaPagina();
			

		});
                
                function carregaPagina(){
                   $('#myTabs a').click(function (e) {
			  e.preventDefault()
			  $(this).tab('show')
                    });
                    $(".cpfmask").mask("999.999.999-99");
                    $(".cnpjmask").mask("99.999.999/9999-99");
                    $(".agenciamask").mask("9999-9");
                    $(".contamask").mask("99999");
                    
                    getLista();
                    
                    $("input[type='radio'][name='rblTipo']").on('change', function() {
                        
			if($(this).val() == 1){
                            var cpf ="<%= cliente.getCpf()%>";
                            $('#txtCpfCnpjNovaConta').val(cpf);
                            $("#txtCpfCnpjNovaConta").prop('disabled', true);
                            $('#txtCpfCnpjNovaConta').removeClass('cnpjmask');
                            $('#txtCpfCnpjNovaConta').addClass('cpfmask');
                            $(".cpfmask").mask("999.999.999-99");
                            $("#lblCpfCnpj").html('CPF:');
                        }else{
                            $('#txtCpfCnpjNovaConta').val('');
                            $("#txtCpfCnpjNovaConta").prop('disabled', false);
                            $('#txtCpfCnpjNovaConta').addClass('cnpjmask');
                            $('#txtCpfCnpjNovaConta').removeClass('cpfmask');
                            $(".cnpjmask").mask("99.999.999/9999-99");
                            $("#lblCpfCnpj").html('CNPJ:');
                        }
                     });
                     
                    $( ".dtmask" ).datepicker({
                        dateFormat: 'dd/mm/yy',
                        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
                        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
                        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
                        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
                        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
                        nextText: 'Próximo',
                        prevText: 'Anterior'
                    });
                 }
                 
                function getFormOperacao(idConta){
                    $("#hdnConta").val(idConta);
                    getSaldo(idConta);
                    getTransacao();
                }
                 
                function getSaldo(idConta){
                    $.ajax({
                        type: "post",
                        url: "CarregaSaldo", //this is my servlet
                        data: "idConta="+idConta,
                        success: function(msg){
                          var vet = msg.split(",");
                          $('#txtTabsaldoSaldo').val(vet[0]);
                          $('#txtTabsaldoLimite').val(vet[1]);
                          $('#txtTabsaldodata').val(vet[2]);
                          $('#txtDepositoData').val(vet[2]);
                          $('#txtDepositoAgencia').val(vet[3]);
                          $('#txtDepositoConta').val(vet[4]);
                          
                          $('#txtSaqueAgencia').val(vet[3]);
                          $('#txtSaqueConta').val(vet[4]);
                          $('#txtSaqueData').val(vet[2]);
                          getTransacao();
                        }
                    });
                }
                
                function getToken(tipo){
                    $.ajax({
                        type: "post",
                        url: "ProcessaToken", //this is my servlet
                        data: "",
                        success: function(msg){
                            console.log(msg);
                            var vet = msg.split("|");
                            var respostausuer = prompt(vet[0]);
                            var respostaajax = vet[1];
                            $('#hdnRespostaToken').val(respostaajax);
                            if($('#hdnRespostaToken').val() == respostausuer){
                                if(tipo == 2){
                                    sacar();
                                }else if(tipo == 3){
                                    depositar();
                                }else if(tipo == 1){
                                    checarConta();
                                }
                            }else{
                                alert('Resposta incorreta.');
                            }
                            
                        }
                    });
                }
                
                function checarConta(){
                    $.ajax({
                        type: "post",
                        url: "ProcessaDeposito", //this is my servlet
                        data: "idConta="+$('#hdnConta').val()+"&numero="+$('#txtTransferenciaConta').val()+"&valor="+$('#txtTransferenciaValor').val()+
                                "&agencia="+$('#txtTransferenciaAgencia').val()+"&tipo=checarConta",
                        success: function(msg){
                            var vet = msg.split(",");
                            if(vet[0] == 0){
                                alert(vet[1]);
                            }else{
                                if(confirm(vet[1])){
                                    transferir();
                                }
                            }
                        }
                    });
                }
                
                function transferir(){
                    $.ajax({
                        type: "post",
                        url: "ProcessaDeposito", //this is my servlet
                        data: "idConta="+$('#hdnConta').val()+"&numero="+$('#txtTransferenciaConta').val()+"&valor="+$('#txtTransferenciaValor').val()+
                                "&agencia="+$('#txtTransferenciaAgencia').val()+"&tipo=transferencia",
                        success: function(msg){
                            alert(msg);
                            getSaldo($('#hdnConta').val());
                            getTransacao();
                        }
                    });
                }
                
                function sacar(){
                    $.ajax({
                            type: "post",
                            url: "ProcessaDeposito", //this is my servlet
                            data: "idConta="+$('#hdnConta').val()+"&valor="+$('#txtSaqueValor').val()+"&tipo=saque",
                            success: function(msg){
                                alert(msg);
                                getSaldo($('#hdnConta').val());
                                getTransacao();
                            }
                    });
                }
                
                function depositar(){
                    $.ajax({
                        type: "post",
                        url: "ProcessaDeposito", //this is my servlet
                        data: "idConta="+$('#hdnConta').val()+"&valor="+$('#txtDepositoValor').val()+"&tipo=deposito",
                        success: function(msg){
                            alert(msg);
                            getSaldo($('#hdnConta').val());
                            getTransacao();
                        }
                    });
                }
                 
                function cadastrarConta(){
                    $.ajax({
                        type: "post",
                        url: "ProcessaCadConta", //this is my servlet
                        data: "tipo="+$("input[type='radio'][name='rblTipo']").val()+"&agencia="+$('#txtNovaAgencia').val()+
                              "&conta="+$('#txtNovaConta').val()+"&cpfCnpj="+$('#txtCpfCnpjNovaConta').val(),
                        success: function(msg){
                            alert('Conta cadastrada com sucesso.');
                            location.reload(true);
                        }
                    });
                 }
                
                function getLista(){
                    $.ajax({
                        type: "post",
                        url: "ListaConta", //this is my servlet
                        data: "",
                        success: function(msg){
                            $('#tbConteudoLvConta').html(msg);
                        }
                    });
                }
                
                function getTransacao(){
                    $.ajax({
                        type: "post",
                        url: "ListaTransacao", //this is my servlet
                        data: "idConta="+$('#hdnConta').val(),
                        success: function(msg){
                            $('#tbdTransacao').html(msg);
                        }
                    });
                }
                
                function mostramodal(){
                    var nconta ="<%= frm.getNumeroNovaConta(cliente.getId()) %>";
                    $('#txtNovaAgencia').val('0123-4');
                    $('#txtNovaConta').val(nconta);
                    $("#txtNovaConta").prop('disabled', true);
                    $("#txtNovaAgencia").prop('disabled', true);
                    $('#modal-novaconta').modal('show');
		}
                
		function mostramodal1(idConta){
                    getFormOperacao(idConta);
                    $('#modal-operacao').modal('show');
		}

	</script>
    </body>
    <% 
        }else{
            request.setAttribute("ERRO", "ERRO: VOCE PRECISA ESTAR LOGADO PARA ACESSAR ESSA PAGINA.");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProcessaErro");
            rd.forward(request, response);
        }
    %>
</html>
