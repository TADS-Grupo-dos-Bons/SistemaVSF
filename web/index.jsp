<%@page import="Classes.Combo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Estado"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body class="login">
	<div class="login-container">
		<h2>Internet Banking - VSF</h2>
		<br>
		<form action="ProcessaLogin" method="post">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i>CPF</i></span><input id="txtCpfLogin" name="cpf" class="form-control cpfmask" type="text"  placeholder="999.999.999-99">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i>Senha</i></span><input id="txtSenhaLogin" name="senha" class="form-control" type="password" name="senha" placeholder="Senha">
                        </div>
                    </div>
                    <button class="btn btn-block btn-large btn-primary login-submit" id="btnAcessar" >Acessar</button>
                    <br>
                    <a class='cadastro' onclick="mostramodal();">Cadastre-se</a>
                </form>
	</div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                    <div class="modal-content">
                            <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Cadastro de Usuario</h4>
                            </div>
                            <div class="modal-body">
                                    <form action="#cadastro......." method="post">
                                            <div class="form-group checkbox-conta">
                                                    
                                            </div>
                                            <div class="form-group">
                                                    <div class='row'>

                                                            <div class='col-md-4'>
                                                                    <u>Dados Pessoais</u><br><br>
                                                                    <label>* Nome:<br></label><input id="txtNome" class="required" type="text"><br><br>
                                                                    <label>* CPF:<br></label><input id="txtCpf" class="required cpfmask" type="text"><br><br>
                                                                    <u>Endereco</u><br><br>
                                                                    <label>* Logradouro:<br></label><input id="txtLogradouro" class="required" type="text"><br><br>
                                                                    <label>* Bairro:<br></label><input id="txtBairro" class="required" type="text"><br><br>
                                                                    <u>Contato</u><br><br>
                                                                    <label>* Telefone Fixo:<br></label><input id="txtTelFixo" class="telmask required" type="text"><br><br>
                                                                    <label>* Senha:<br></label><input id="txtSenha" class="required" type="password"><br><br>

                                                            </div>
                                                            <div class='col-md-4'>
                                                                    <br><br><label>Sobrenome:<br></label><input id="txtSobrenome" type="text"><br><br>
                                                                    <label>* Data de Nascimento:<br></label><input id="txtDtNascimento" class="dtmask required" type="text"><br><br><br><br>
                                                                    <label>* Numero:<br></label><input id="txtNumero" class="required" type="text"><br><br>
                                                                    <label>* Estado:<br></label>
                                                                    <select style="width: 145px;" class="required" id="ddlEstado">
                                                                        <option value=""></option>
                                                                        <% 
                                                                            List<Estado> lstestado = new ArrayList();
                                                                            lstestado = Combo.getEstado();
                                                                            for(Estado estado : lstestado){
                                                                                out.println("<option value=\"" + estado.getId() + "\">" + estado.getNome() + " </option>");
                                                                            }
                                                                        %>
                                                                    </select><br><br><br><br>
                                                                    <div style="margin-top: 5px;">
                                                                    <label>Telefone Celular:<br></label><input id="txtTelCelular" class="telmask" type="text"><br><br>
                                                                    <label>* Confirma Senha:<br></label><input id="txtConfirmarSenha" class="required" type="password"><br><br>
                                                                    </div>
                                                            </div>
                                                            <div class='col-md-4'>
                                                                    <br><br><label>* RG:<br></label><input id="txtRg" class="required" type="text"><br><br>
                                                                    <label>* Renda Mensal:<br></label><input id="txtRendaMensal" class="required" type="text" ><br><br><br><br>
                                                                    <label>Complemento:<br></label><input id="txtComplemento" type="text"><br><br>
                                                                    <label>* Cidade:<br></label>
                                                                    <select style="width: 145px;" id="ddlCidade" class="required">
                                                                        <option value="0"></option>
                                                                    </select><br><br><br><br>
                                                                    <div style="margin-top: 5px;">
                                                                    <label>E-mail:<br></label><input id="TxtEmail" type="text"><br><br>
                                                                    </div>

                                                            </div>
                                                    </div>
                                            </div>

                                    </form>

                            </div>
                            <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                    <button type="button" id="btnCadastrar" class="btn btn-primary">Cadastrar</button>
                            </div>
                    </div>
            </div>
        </div>
        <script type="text/javascript">
        $(document).ready(function() {
            carregaPagina();
        });
        
        function carregaPagina(){
             
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
            $(".dtmask").mask("99/99/9999");
            $(".telmask").mask("(99) 9999-9999");
            $(".cpfmask").mask("999.999.999-99");
            $("#txtRg").mask("99.999.999-9");
            
            $('#ddlEstado').off().on('change', function (){
                $.ajax({
                    type: "post",
                    url: "CarregaCidade", //this is my servlet
                    data: "idestado="+$('#ddlEstado').val(),
                    success: function(msg){
                        $('#ddlCidade').html(msg);
                    }
                });
            });
            
            $('#btnCadastrar').on('click', function (){
                if(($('.required').val() != '' && $('#ddlCidade').val() != '0')){
                    if(validarCPF($("#txtCpf").val())){
                        if($('#txtSenha').val() == $('#txtConfirmarSenha').val()){
                        $.ajax({
                          type: "post",
                          url: "ProcessaCadUsuario", //this is my servlet
                          data: "nome=" +$('#txtNome').val()+"&cpf="+$('#txtCpf').val()+"&logradouro="+$('#txtLogradouro').val()
                          +"&bairro="+$('#txtBairro').val()+"&telFixo="+$('#txtTelFixo').val()+"&senha="+$('#txtSenha').val()
                          +"&sobrenome="+$('#txtSobrenome').val()+"&dtNascimento="+$('#txtDtNascimento').val()+"&numero="+$('#txtNumero').val()
                          +"&estado="+$('#ddlEstado').val()+"&telCelular="+$('#txtTelCelular').val()+"&rg="+$('#txtRg').val()
                          +"&rendaMensal="+$('#txtRendaMensal').val()+"&complemento="+$('#txtComplemento').val()+"&cidade="+$('#ddlCidade').val()
                          +"&email="+$('#TxtEmail').val(),
                          success: function(msg){      
                                  alert('Cadastro realizado com sucesso.');
                          }
                        });
                        }else{
                            alert('As senhas são diferentes.');
                        }
                    }else{
                        alert('CPF inválido.')
                    }
                }else{
                    alert('Preencha os campos obrigatórios.');
                }
                
            });
        }
        
	function mostramodal(){
            $('#myModal').modal('show');
	}
        
        function validarCPF(cpf) {  
            cpf = cpf.replace(/[^\d]+/g,'');    
            if(cpf == '') return false; 
            // Elimina CPFs invalidos conhecidos    
            if (cpf.length != 11 || 
                cpf == "00000000000" || 
                cpf == "11111111111" || 
                cpf == "22222222222" || 
                cpf == "33333333333" || 
                cpf == "44444444444" || 
                cpf == "55555555555" || 
                cpf == "66666666666" || 
                cpf == "77777777777" || 
                cpf == "88888888888" || 
                cpf == "99999999999")
                    return false;       
            // Valida 1o digito 
            add = 0;    
            for (i=0; i < 9; i ++)       
                add += parseInt(cpf.charAt(i)) * (10 - i);  
                rev = 11 - (add % 11);  
                if (rev == 10 || rev == 11)     
                    rev = 0;    
                if (rev != parseInt(cpf.charAt(9)))     
                    return false;       
            // Valida 2o digito 
            add = 0;    
            for (i = 0; i < 10; i ++)        
                add += parseInt(cpf.charAt(i)) * (11 - i);  
            rev = 11 - (add % 11);  
            if (rev == 10 || rev == 11) 
                rev = 0;    
            if (rev != parseInt(cpf.charAt(10)))
                return false;       
            return true;   
        }
        </script>
</body>
</html>