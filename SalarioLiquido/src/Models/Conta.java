package Models;

public class Conta {
	
    private double salarioBruto;
    private double valeRefeicao;
    private double valeTransporte;
    private double valeAlimentacao;
    private double planoSaudeDependente;
    private int numeroDependentes;

    //Construtor 
    public Conta(double salarioBruto, double valeRefeicao, double valeTransporte, double valeAlimentacao, double planoSaudeDependente, int numeroDependentes) {
        this.salarioBruto = salarioBruto;
        this.valeRefeicao = valeRefeicao;
        this.valeTransporte = valeTransporte;
        this.valeAlimentacao = valeAlimentacao;
        this.planoSaudeDependente = planoSaudeDependente;
        this.numeroDependentes = numeroDependentes;
    }

    //o calculo do INSS é o salário bruto multiplicado pela alíquota 
    public double calcularINSS() {
        double inss = 0.0;
        if (salarioBruto <= 1320.00) {
            inss = salarioBruto * 0.075;
        } else if (salarioBruto <= 2571.29) {
            inss = salarioBruto * 0.09;
        } else if (salarioBruto <= 3856.94) {
            inss = salarioBruto * 0.12;
        } else {
            inss = salarioBruto * 0.14;
        }
        return inss;
    }

    // o calculo é o salário bruto menos o desconto da contribuição previdenciária
    public double calcularIRRF(double salarioBase) {
        double irrf = 0.0;
        double baseCalculo = salarioBase - (189.59 * numeroDependentes);
        if (baseCalculo <= 2112.00) {
            irrf = 0.0;
        } else if (baseCalculo <= 2826.65) {
            irrf = baseCalculo * 0.075;
        } else if (baseCalculo <= 3751.05) {
            irrf = baseCalculo * 0.15;
        } else if (baseCalculo <= 4664.68) {
            irrf = baseCalculo * 0.225;
        } else {
            irrf = baseCalculo * 0.275;
        }
        return irrf;
    }

    //o calculo do salário líquido é o resultado do salário bruto após várias deduções e descontos
    public double calcularSalarioLiquido() {
        double inss = calcularINSS();
        double salarioBase = salarioBruto - inss;
        double irrf = calcularIRRF(salarioBase);
        double totalDescontos = valeRefeicao + valeTransporte + valeAlimentacao + (planoSaudeDependente * numeroDependentes);
        return salarioBruto - (inss + irrf + totalDescontos);
    }

    // Métodos Getters
    public double getSalarioBruto() { 
    	return salarioBruto; 
    }
    
    public double getValeRefeicao() { 
    	return valeRefeicao; 
    }
    
    public double getValeTransporte() { 
    	return valeTransporte; 
    }
    
    public double getValeAlimentacao() { 
    	return valeAlimentacao; 
    }
    
    public double getPlanoSaudeDependente() { 
    	return planoSaudeDependente; 
    }
    
    public int getNumeroDependentes() { 
    	return numeroDependentes; 
    }

    // Métodos Setters com validação
    public void setSalarioBruto(double salarioBruto) {
        if (salarioBruto < 0) {
            throw new IllegalArgumentException("Salário Bruto não pode ser negativo.");
        }
        if (salarioBruto > 999999) {
            throw new IllegalArgumentException("Salário Bruto não pode ter mais de 6 dígitos.");
        }
        this.salarioBruto = salarioBruto;
    }

    public void setValeRefeicao(double valeRefeicao) {
        if (valeRefeicao < 0) {
            throw new IllegalArgumentException("Vale Refeição não pode ser negativo.");
        }
        if (valeRefeicao > 9999) {
            throw new IllegalArgumentException("Vale Refeição não pode ter mais de 4 dígitos.");
        }
        this.valeRefeicao = valeRefeicao;
    }

    public void setValeTransporte(double valeTransporte) {
        if (valeTransporte < 0) {
            throw new IllegalArgumentException("Vale Transporte não pode ser negativo.");
        }
        if (valeTransporte > 9999) {
            throw new IllegalArgumentException("Vale Transporte não pode ter mais de 4 dígitos.");
        }
        this.valeTransporte = valeTransporte;
    }

    public void setValeAlimentacao(double valeAlimentacao) {
        if (valeAlimentacao < 0) {
            throw new IllegalArgumentException("Vale Alimentação não pode ser negativo.");
        }
        if (valeAlimentacao > 9999) {
            throw new IllegalArgumentException("Vale Alimentação não pode ter mais de 4 dígitos.");
        }
        this.valeAlimentacao = valeAlimentacao;
    }

    public void setPlanoSaudeDependente(double planoSaudeDependente) {
        if (planoSaudeDependente < 0) {
            throw new IllegalArgumentException("Plano de Saúde por Dependente não pode ser negativo.");
        }
        if (planoSaudeDependente > 9999) {
            throw new IllegalArgumentException("Plano de Saúde não pode ter mais de 4 dígitos.");
        }
        this.planoSaudeDependente = planoSaudeDependente;
    }

    public void setNumeroDependentes(int numeroDependentes) {
        if (numeroDependentes < 0) {
            throw new IllegalArgumentException("Número de Dependentes não pode ser negativo.");
        }
        if (numeroDependentes > 99) {
            throw new IllegalArgumentException("Número de Dependentes não pode ter mais de 2 dígitos.");
        }
        this.numeroDependentes = numeroDependentes;
    }
}
