package som;


public class Latice {
	
	/* Essa classe tem como objetivo implementar as funcionalidades do latice. Ou seja
	 * para o cojunto de neoronios com indices, indicar as posicoes e rela�oes entre eles no espa�o
	 * matricial.
	 * 
	 * Nesta implementacao de SOM os Neuronios ser�o armazenados em um vetor, a classe responsavel por
	 * entender os neuronois no espoa�o matricial � esta classe "latice".
	 * com ela sera possivel perguntar as posic�es matriciais de um neuronio a partir de seu indice no
	 * vetor de neuronios, tambem sera possivel calcular as relacoes entre os neuronios.
	 * 
	 * Nota-se que dependendo das dimensoes do latice o neoronio de indice i pode ou n�o se relacionar
	 * com um neoronio de indice j
	 * 
	 * */
	
	public int colunas; public int linhas;// dimen��es a x b do latice
	
	public Latice(int b, int a){
		
		//construtor determina as dimen��es do latice
		
		this.colunas=a;
		this.linhas=b;
	}
	
	public int getIndice(int x,int y){
		//retorna o indice de um neoronio da cordenada (x,y) do espa�o matricial.
		//dado um neoronio no espa�o matricial retorna o seu indice no vetor de neoronios.
		
		if(y>=linhas || x>=colunas ){
			// se a cordenada pedida est� fora do latice retorna um indice invalifo
			return -1;
		}
		
		return(x+(y*colunas));
	}
	
	public int getColuna(int indice){
		// dado um indice do neuronio(no vetor de neuronios) 
		//retorna a coluna onde ele se encontra no espa�o matricial
		return(int)((indice)%colunas);
	}
	
	public int getLinha(int indice){
		// dado um indice do neuronio(no vetor de neuroios) 
		//retorna a linha onde ele se encontra no espa�o matricial
		return(int)((indice)/colunas);
	}
	
	
	int raioMatricial(int i, int j){
		/* dado dois neuronios de indices i e j retorna o raio que um esta do outro
		 esta fun��o ser� importante para calcular a influencia entre os neoronios considerando
		 a fun��o de vizinhan�a
		*/
		
		int xi=getColuna(i); //pega a posi��o x matricial do neoronio de indie i
		int yi=getLinha(i);	//pega a posi��o y matricial do neoronio de indie i
		
		int xj=getColuna(j);	//pega a posi��o x matricial do neoronio de indie j
		int yj=getLinha(j);		//pega a posi��o y matricial do neoronio de indie j
		
		return Math.abs(xi-xj)+Math.abs(yi-yj);
		
		/*o calculo acima considera o raio do ponto de vista da topologia retangular-diamante
		 * */
		
	}
	
	double[] getVizinhancaPonderada(int i,int raio,Vizinhanca vizinhanca){
		
		/*esta fun��o devolve a pondera��o de cada neuronio em rela��o com neoronio de indice "i"
		 *dependendo do raio na vizinhan�a.
		 *Por Exemplo, se tratando de fun��o BUBBLE os neoronios que estiverem
		 *mais longe de "i" que o "raio determinado" receber�o pondera��o 0 o que anulara a movimenta��o
		 *no metodo mudar pesos; 
		*/
		double ponderacoes[]= new double[linhas*colunas];// o numero de pondera��es � igual ao numero de neuronios
		
		if(vizinhanca==Vizinhanca.BUBBLE){
			/*se a vizinha�a � do tipo buble at� neoronios com at� a distancia r receber�o 1 s demais 0*/
			// passaremos por todos os neoronios "j" medindo a distancias deles com "i".
			
			for(int j=0; j<linhas*colunas;j++){//para cada um dos indeces de neuronios
				int raioMedido=raioMatricial(i, j);
				if(raioMedido<=raio){
					ponderacoes[j]=1;
				}
			}
		}
		if(vizinhanca==Vizinhanca.GAUSS){
			
			/*na fun��o de vizinhan�a gaussiana o neoronio de indice "i" influencia todos os neoronios
			 * com alguma intensidade definida pelo fun��o gaussiana que tamb�m tem uma raio de "atua��o pratica"
			 * */
			
			for(int j=0; j<linhas*colunas;j++){// para cada um ds indices de neuronios
				int raioMedido=raioMatricial(i, j);
					ponderacoes[j]=Math.exp(-((raioMedido*raioMedido)/(2*raio*raio)));//fun��o gaussiana
				
			}
		}
			
		return ponderacoes;
		
	}
	

}
