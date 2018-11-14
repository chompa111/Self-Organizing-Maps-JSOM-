package som;


public class Latice {
	
	/* Essa classe tem como objetivo implementar as funcionalidades do latice. Ou seja
	 * para o cojunto de neoronios com indices, indicar as posicoes e relaçoes entre eles no espaço
	 * matricial.
	 * 
	 * Nesta implementacao de SOM os Neuronios serão armazenados em um vetor, a classe responsavel por
	 * entender os neuronois no espoaço matricial é esta classe "latice".
	 * com ela sera possivel perguntar as posicões matriciais de um neuronio a partir de seu indice no
	 * vetor de neuronios, tambem sera possivel calcular as relacoes entre os neuronios.
	 * 
	 * Nota-se que dependendo das dimensoes do latice o neoronio de indice i pode ou não se relacionar
	 * com um neoronio de indice j
	 * 
	 * */
	
	public int colunas; public int linhas;// dimenções a x b do latice
	
	public Latice(int b, int a){
		
		//construtor determina as dimenções do latice
		
		this.colunas=a;
		this.linhas=b;
	}
	
	public int getIndice(int x,int y){
		//retorna o indice de um neoronio da cordenada (x,y) do espaço matricial.
		//dado um neoronio no espaço matricial retorna o seu indice no vetor de neoronios.
		
		if(y>=linhas || x>=colunas ){
			// se a cordenada pedida está fora do latice retorna um indice invalifo
			return -1;
		}
		
		return(x+(y*colunas));
	}
	
	public int getColuna(int indice){
		// dado um indice do neuronio(no vetor de neuronios) 
		//retorna a coluna onde ele se encontra no espaço matricial
		return(int)((indice)%colunas);
	}
	
	public int getLinha(int indice){
		// dado um indice do neuronio(no vetor de neuroios) 
		//retorna a linha onde ele se encontra no espaço matricial
		return(int)((indice)/colunas);
	}
	
	
	int raioMatricial(int i, int j){
		/* dado dois neuronios de indices i e j retorna o raio que um esta do outro
		 esta função será importante para calcular a influencia entre os neoronios considerando
		 a função de vizinhança
		*/
		
		int xi=getColuna(i); //pega a posição x matricial do neoronio de indie i
		int yi=getLinha(i);	//pega a posição y matricial do neoronio de indie i
		
		int xj=getColuna(j);	//pega a posição x matricial do neoronio de indie j
		int yj=getLinha(j);		//pega a posição y matricial do neoronio de indie j
		
		return Math.abs(xi-xj)+Math.abs(yi-yj);
		
		/*o calculo acima considera o raio do ponto de vista da topologia retangular-diamante
		 * */
		
	}
	
	double[] getVizinhancaPonderada(int i,int raio,Vizinhanca vizinhanca){
		
		/*esta função devolve a ponderação de cada neuronio em relação com neoronio de indice "i"
		 *dependendo do raio na vizinhança.
		 *Por Exemplo, se tratando de função BUBBLE os neoronios que estiverem
		 *mais longe de "i" que o "raio determinado" receberão ponderação 0 o que anulara a movimentação
		 *no metodo mudar pesos; 
		*/
		double ponderacoes[]= new double[linhas*colunas];// o numero de ponderações é igual ao numero de neuronios
		
		if(vizinhanca==Vizinhanca.BUBBLE){
			/*se a vizinhaça é do tipo buble até neoronios com até a distancia r receberão 1 s demais 0*/
			// passaremos por todos os neoronios "j" medindo a distancias deles com "i".
			
			for(int j=0; j<linhas*colunas;j++){//para cada um dos indeces de neuronios
				int raioMedido=raioMatricial(i, j);
				if(raioMedido<=raio){
					ponderacoes[j]=1;
				}
			}
		}
		if(vizinhanca==Vizinhanca.GAUSS){
			
			/*na função de vizinhança gaussiana o neoronio de indice "i" influencia todos os neoronios
			 * com alguma intensidade definida pelo função gaussiana que também tem uma raio de "atuação pratica"
			 * */
			
			for(int j=0; j<linhas*colunas;j++){// para cada um ds indices de neuronios
				int raioMedido=raioMatricial(i, j);
					ponderacoes[j]=Math.exp(-((raioMedido*raioMedido)/(2*raio*raio)));//função gaussiana
				
			}
		}
			
		return ponderacoes;
		
	}
	

}
