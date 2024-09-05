package Sistema;

import javax.swing.*;

import Database.DatabaseManager;
import Models.Conta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Sistema {

	public static void main(String[] args) {

		//Cria uma aplicação gráfica
		JFrame frame = new JFrame("Calculadora de Salário Líquido");
		//Define a largura e altura da janela 
		frame.setSize(500, 400);
		//Quando o usuário clicar no X, a janela fecha e sai da aplicação 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Define o Layout da aplicação , utilizando GridBagLayout
		frame.setLayout(new GridBagLayout());
		//Responsável pelas restrições do Layout 
		GridBagConstraints gbc = new GridBagConstraints();
		//Define o espaço entre os compononentes
		//Insets = inserções 
		//Inserções = adição de algo 
		gbc.insets = new Insets(5, 5, 5, 5);

		//Adiciona as labels para os campos de entrada
		//Labels = rótulos 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		frame.add(new JLabel("Salário Bruto (R$):"), gbc);

		gbc.gridy = 1;
		frame.add(new JLabel("Vale Refeição (R$):"), gbc);

		gbc.gridy = 2;
		frame.add(new JLabel("Vale Transporte (R$):"), gbc);

		gbc.gridy = 3;
		frame.add(new JLabel("Vale Alimentação (R$):"), gbc);

		gbc.gridy = 4;
		frame.add(new JLabel("Plano de Saúde por Dependente (R$):"), gbc);

		gbc.gridy = 5;
		frame.add(new JLabel("Número de Dependentes:"), gbc);

		//Adiciona campos de texto para entrada de dados 
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField tfSalarioBruto = new JTextField();
		frame.add(tfSalarioBruto, gbc);

		gbc.gridy = 1;
		JTextField tfValeRefeicao = new JTextField();
		frame.add(tfValeRefeicao, gbc);

		gbc.gridy = 2;
		JTextField tfValeTransporte = new JTextField();
		frame.add(tfValeTransporte, gbc);

		gbc.gridy = 3;
		JTextField tfValeAlimentacao = new JTextField();
		frame.add(tfValeAlimentacao, gbc);

		gbc.gridy = 4;
		JTextField tfPlanoSaude = new JTextField();
		frame.add(tfPlanoSaude, gbc);

		gbc.gridy = 5;
		JTextField tfDependentes = new JTextField();
		frame.add(tfDependentes, gbc);

		// Adiciona o botão de cálculo
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		JButton btnCalcular = new JButton("Calcular");
		frame.add(btnCalcular, gbc);

		// Adiciona a área de texto para exibir resultados
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		JTextArea taResultado = new JTextArea(10, 30);
		taResultado.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(taResultado);
		frame.add(scrollPane, gbc);

        // Adiciona um listener para o botão de cálculo
		// Listener é um objeto que implementa uma interface e que é responsável por tratar eventos
		btnCalcular.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            double salarioBruto = Double.parseDouble(tfSalarioBruto.getText());
		            double valeRefeicao = Double.parseDouble(tfValeRefeicao.getText());
		            double valeTransporte = Double.parseDouble(tfValeTransporte.getText());
		            double valeAlimentacao = Double.parseDouble(tfValeAlimentacao.getText());
		            double planoSaude = Double.parseDouble(tfPlanoSaude.getText());
		            int dependentes = Integer.parseInt(tfDependentes.getText());

		            // Valida os valores de entrada
		            if (salarioBruto < 0 || valeRefeicao < 0 || valeTransporte < 0 || valeAlimentacao < 0 || planoSaude < 0 || dependentes < 0) {
		                throw new NumberFormatException();
		            }

		            Conta conta = new Conta(salarioBruto, valeRefeicao, valeTransporte, valeAlimentacao, planoSaude, dependentes);
		            double salarioLiquido = conta.calcularSalarioLiquido();
		            double inss = conta.calcularINSS();
		            double irrf = conta.calcularIRRF(salarioBruto - inss);
		            double totalDescontos = valeRefeicao + valeTransporte + valeAlimentacao + (planoSaude * dependentes);

		            taResultado.setText(String.format(
		                "Salário Bruto: R$ %.2f\n" +
		                "Desconto INSS: R$ %.2f\n" +
		                "Desconto IRRF: R$ %.2f\n" +
		                "Desconto VR: R$ %.2f\n" +
		                "Desconto VT: R$ %.2f\n" +
		                "Desconto VA: R$ %.2f\n" +
		                "Desconto Plano de Saúde: R$ %.2f\n" +
		                "Salário Líquido: R$ %.2f",
		                salarioBruto, inss, irrf, valeRefeicao, valeTransporte, valeAlimentacao,
		                planoSaude * dependentes, salarioLiquido));

		            // Insere dados no banco de dados
		            try (DatabaseManager dbManager = new DatabaseManager()) {
		                dbManager.inserirConta(salarioBruto, valeRefeicao, valeTransporte, valeAlimentacao, planoSaude, dependentes);
		            }

		        } catch (NumberFormatException ex) {
		            taResultado.setText("Erro: Verifique os valores inseridos.");
		        } catch (SQLException ex) {
		            taResultado.setText("Erro ao acessar o banco de dados.");
		        }
		    }
		});
		frame.setVisible(true);
	}
}
