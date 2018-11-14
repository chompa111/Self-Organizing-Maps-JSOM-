package funcaoDecaimento;

public class DecaimentoExponencial implements Decaimento {
	
	/*este decaimento de taxa de aprendizagem funciona com base numa funcao 
	 * exponencial com expoente negativo, isso siginifica que o valor decresce
	 * mas decresce mais rapidamento no começo e no final tem comportamento assintotico
	 * mas precisamos definir a velocidade de decrescimento  
	 * */
	
	
	Double ritmo;//velocidade de decrescimento 
	//quanto maior o ritmo mais vagarozamente a funcao decresce 
	
	public DecaimentoExponencial(Double velocidade) {
		this.ritmo=velocidade;
		
	}
	
	
	@Override
	public double getfatorDecaimento(int epocas) {
		
		return Math.exp(-(epocas/ritmo));
		//exponensial aplicada a epoca corrente no algoritmo
		 
	}

	
}
