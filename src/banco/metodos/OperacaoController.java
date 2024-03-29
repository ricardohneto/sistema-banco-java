package banco.metodos;

import banco.app.App;
import banco.classes.Conta;
import banco.classes.ContaCorrente;
import banco.classes.ContaPoupanca;
import banco.classes.Saque;
import banco.classes.Tarifacao;
import banco.classes.Deposito;
import banco.classes.Operacao;
import banco.classes.Rendimento;
import banco.classes.Transferencia;
import banco.classes.Util;

public class OperacaoController {

	public static void menu() {
		int op = 0;
		boolean sair = false;

		do {

			System.out.print("\n\nMENU DE OPERACOES BANCARIAS\n" + "(1) - VER SALDO\n" + "(2) - DEPOSITAR\n"
					+ "(3) - SACAR\n" + "(4) - TRANSFERIR\n" + "(5) - VER EXTRATO\n" + "(6) - VIRA MES\n(0) - VOLTAR\n=>");
			op = Util.input.nextInt();
			switch (op) {
			case 0:
				sair = true;
				break;
			case 1:
				formVerSaldo();
				break;
			case 2:
				formDepositar();
				break;
			case 3:
				formSacar();
				break;
			case 4:
				formTransferir();
				break;
			case 5:
				formVerExtrato();
				break;
			case 6:
				formViraMes();
				break;
			default:
				System.out.println("[+] opcao invalida!!!");
				break;
			}

		} while (!sair);
	}

	public static void formVerSaldo() {
		int codConta = 0;

		System.out.println("[?] informe o CODIGO da sua CONTA");
		codConta = Util.input.nextInt();

		if (ContaController.existe(codConta)) {
			double saldo = ContaController.getConta(codConta).getSaldo();
			System.out.println(saldo);
		} else {
			System.out.println("[+] conta nao existe!!!");
		}
	}

	public static void formDepositar() {

		System.out.println("[?] informe o CODIGO da CONTA:");
		int codConta = Util.input.nextInt();
		System.out.println("[?] informe o VALOR para DEPOSITAR:");
		double valor = Util.input.nextDouble();

		if (ContaController.existe(codConta)) {
			Conta contaAux = ContaController.getConta(codConta);
			Operacao deposito = new Deposito(contaAux, valor);

			if (deposito.efetuar()) {
				System.out.println("[+] DEPOSITO realizado com SUCESSO!");
			} else {
				System.out.println("[+] falha ao DEPOSITAR!!!");
			}
		} else {
			System.out.println("[+] conta nao existe!!!");
		}
	}

	public static void formSacar() {
		double valor = 0;
		int codConta = 0;
		Conta contaAux = null;

		System.out.println("[?] informe o CODIGO da CONTA:");
		codConta = Util.input.nextInt();
		System.out.println("[?] informe o VALOR para SACAR:");
		valor = Util.input.nextDouble();

		if (ContaController.existe(codConta)) {
			contaAux = ContaController.getConta(codConta);
			Operacao saque = new Saque(contaAux, valor);

			if (saque.efetuar()) {
				System.out.println("[+] SAQUE realizado com SUCESSO!");
			} else {
				System.out.println("[+] falha ao SACAR!!!");
			}
		} else {
			System.out.println("[+] conta n�o existe!!!");
		}
	}

	public static void formTransferir() {
		double valor = 0;
		int codConta = 0;
		Conta contaOrigem = null;
		int codConta2 = 0;
		Conta contaDestino = null;

		System.out.println("[?] informe o CODIGO de SUA CONTA:");
		codConta = Util.input.nextInt();
		System.out.println("[?] informe o CODIGO da CONTA para TRANSFERIR:");
		codConta2 = Util.input.nextInt();
		System.out.println("[?] informe o VALOR para TRANSFERIR:");
		valor = Util.input.nextDouble();

		if (ContaController.existe(codConta)) {
			if (ContaController.existe(codConta2)) {
				contaOrigem = ContaController.getConta(codConta);
				contaDestino = ContaController.getConta(codConta2);
				Operacao transferencia = new Transferencia(contaOrigem, contaDestino, valor);

				if (transferencia.efetuar()) {
					System.out.println("[+] TRANSFERENCIA realizada com SUCESSO!");
				} else {
					System.out.println("[+] falha ao SACAR!!!");
				}
			}
		} else {
			System.out.println("[+] conta n�o existe!!!");
		}
	}

	// apresentar [ daqui p/ baixo ]

	public static void formVerExtrato() {
		int codConta = 0;

		System.out.println("[?] informe o CODIGO da sua CONTA");
		codConta = Util.input.nextInt();

		if (ContaController.existe(codConta)) {
			String extrato = ContaController.getConta(codConta).extrato();
			System.out.println(extrato);
			System.out.println("########################################\n");
		} else {
			System.out.println("[+] conta n�o existe!!!");
		}
	}

	public static void formViraMes(){
		Operacao viraMes = null;

		Conta conta = null;

		App.itContas = App.contas.iterator();

		while(App.itContas.hasNext()){
			conta = (Conta) App.itContas.next();
			if(conta instanceof ContaCorrente){
				viraMes = new Tarifacao(conta);
				viraMes.efetuar();
			}else{
				if(conta instanceof ContaPoupanca){
					viraMes = new Rendimento(conta);
					viraMes.efetuar();
				}
			} 	
		}
		System.out.println("[!] operação de vira mes realizada com sucesso!!!");

	}
}


