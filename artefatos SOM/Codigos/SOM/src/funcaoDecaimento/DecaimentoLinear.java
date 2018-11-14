package funcaoDecaimento;

public class DecaimentoLinear implements Decaimento {

	/*este tipo de decaimento da taxa de apredizagem se baseia em uma funcao linear
	 * decrescente começando de com o valor 1 para o iterações 0 e terminando em 0 na iteração final
	 * com comportamento de reta, ou seja ritmo constante de decaimento 
	 * */
	int maxEpocas;// maximo de epocas que se mandou iterar o SOM
	
	public DecaimentoLinear(int maxEpocas){
		this.maxEpocas=maxEpocas;
	}
	
	@Override
	public double getfatorDecaimento(int epocas) {
		double ang= (-1.0/maxEpocas);// coeficiente angular dessa reta
		double lin= 1;// coeficiente linear dessa reta
		
		return (epocas*ang)+lin;// aplicando o numero da epoca atual na funcao
	}
	
	

}
