package som;


import Input.Inputdados;
import distancia.Medir;
import funcaoDecaimento.Decaimento;
import funcaoDecaimento.DecaimentoLinear;


import posProcess.HitMap;

import posProcess.MatrizU;
import posProcess.Palavrachave;
import posProcess.associacaoDados;


public class Aprendizado {
	

	public static void iniciarNeuronios(Neuronio[] neuronios , Dado dados[]){
		/*inicialização baseada em aleatoriedade dentro do espaço dos dados
		 * */
		
		
		/*para iniciar corretamente os neuronios buscaremos pelos dados
		 * os maximos e minimos de cada coordenada para iniciar os neuronios dentro 
		 * do espaço correto, existe um construtor em neoronios que recebe max e min para fazer isso
		 *  //veja a classe Neuronio
		 * */
		
		int ncoord=dados[0].coordenadas.length;// dimnsão que reside o dado
		double min[]=new double[ncoord];
		double max[]=new double[ncoord];
		
		for(int i=0; i<ncoord;i++){
			min[i]= Double.MAX_VALUE;//setando valores para começar a busca
			max[i]= Double.MIN_VALUE;//setando valores para começar a busca
		}
		
		/*então para cada coordenada i dos dados 
		 * testaremos se ela é menor e maior que a registrada nos vetores max e min
		 * */
		for(Dado d: dados){
			for(int i=0;i<ncoord;i++){
				if(d.coordenadas[i]>max[i])max[i]=d.coordenadas[i];
				if(d.coordenadas[i]<min[i])min[i]=d.coordenadas[i];
			}
		}
		// ao final do laço acima os vetores max e min gurdam os maximos e minimos de cada
		// coordenada, Então Basta Utilizar o Contrutor;
		
		
		for(int i=0;i<neuronios.length;i++){
			 neuronios[i]=new Neuronio(i,min,max);
		}
		
	}
	
	static void mudarPesos(Neuronio n,Dado d,double ataxa,int epoca,Decaimento decaimentoAprendizagem,double 
			fatorVizinhanca){
		/*Essa função tem como objetivo aproximar o neoronio n do dado d dependendo da taxa
		 * de aprendizagem tambem como da epoca que o algorimo esta(decaimento de apredizagem)
		 * por fim levando em consideração se o neoronio é o BMU ou devemos considerar um fator
		 * de vizinhança na movimentação 
		 * */
		
		int ncord=d.coordenadas.length; //numero de caracteristicas dos dados
		
		for(int i=0;i<ncord;i++){
			// para cada um dos pesos i
			
			double mudanca= d.coordenadas[i]-n.pesos[i];// valor maximo a ser alterado no peso i
			
			n.pesos[i]=n.pesos[i]+mudanca*ataxa*decaimentoAprendizagem.getfatorDecaimento(epoca)*fatorVizinhanca;
		}
		
		/*observação se o neuronio movimentado é o bmu o fator de vizinhança aplicado será 1
		 * */
	}
	
	
	static void treinar(Dado[]dados,Neuronio[] neuronios,int raio,int epocas,double ataxa,Decaimento decaimento,Latice latice,Vizinhanca vizinhanca){
		
		/*este metodo tem como função executar o processo de treinamento do SOM
		 * ou seja para cada epoca percorrer os dados buscando o BMU e mudar os pessos do BMU,
		 *  e de sua vizinhança considerando a topologia e as funções de vizinhanca e taxa de aprendizagem
		 * O tipo de decaimento e o tipo de vizinhanca entrarao na mudanca de pessos 
		 * */
		
		
		for(int epoca=0; epoca<epocas;epoca++){
			// o processo sera repetido um numero de epocas
			
			for(Dado d: dados){
			// para cada dado d, precisamos encontrar o BMU deste dado;
				
				Neuronio bmu=null;// candidato a BMU
				double distanciamin=Double.MAX_VALUE;//para armazenar a minima distancia
				
				for(Neuronio n:neuronios){
					// para cada um dos neoronios devemos medir a distancia dele 
					// para o dado d, o neoronio mais proximo será o BMU
					double distancia= Medir.distanciaEuclid(n.pesos,d.coordenadas);
					
					if(distancia<distanciamin){
						// se a distancia medida é menor que a distancia minima o neoronio n é candidato a BMU
						bmu=n;
						distanciamin=distancia;
					}
					
				}
				
				/*ao final do laço acima foram percorridos todos os neuronios
				 * por isso apartir desse ponto a variavel bmu guarda de fato o neoronio
				 *  mais proximo do dado d.
				 */
				
				// tendo o bmu agora temos que move-lo em direçao ao dado d juntamente 
				// com os vizinhos matriciais
				
				bmu.nhits++;// numero de hits incrementado(para pos processamento)
				
				mudarPesos(bmu,d,ataxa,epoca,decaimento,1);// muda os pesos do BMU(veja o metodo mudar pesos)
				
				/*
				 * agora temos que mudar os pesos da vizinhanca, por isso precisamos de um vetor
				 * com todas as ponderacoes de vizinhanca em relacao ao bmu (mais explicacoes
				 * va na classe latice)*/
				
				double ponderacoes[]=latice.getVizinhancaPonderada(bmu.id,raio,vizinhanca);
				
				/*para todos os outro neuronios aplicaremos a mudanca de pesos, caso algum neuronio
				nao seja influenciado pelo bmu recebera ponderacao 0 o que anulara a mundanca de pesos
				*/
				
				for(int aux=0;aux<neuronios.length;aux++){//para cada indice de neuronio indice aux
					
					if(aux==bmu.id)continue;// pula o bmu que já mudou seus pesos
					
					mudarPesos(neuronios[aux], d, ataxa,epoca,decaimento ,ponderacoes[aux]);
					/*muda se o peso desse neuronio dependendo da ponderação deste com o bmu*/
				}
				
				
				
			}//for dos dados
			
		}//for das epocas
		
	}
	
	
	public static void associardadosneuronios(Dado[] dados, Neuronio[] neoronios){
		/*para produzir um ps processamento mais rico é interessante para cada 
		 * neoronio ter uma lista de dados para os este neoronio é o BMU
		 * */
		for(Dado d: dados){
			double mindist=Double.MAX_VALUE;
			Neuronio bmu=null;// candidato a bmu
			for(Neuronio n: neoronios){
				double distancia = Medir.distanciaEuclid(d.coordenadas,n.pesos);
				
				if(distancia<mindist){
					mindist=distancia;
					bmu=n;
				}
			}
			// ao final de percorrer todos os neoronios a variave bmu
			//guarda de fato o bmu
			bmu.dadosProximos.add(d);// o dado d é adicionado a lusta de dados proximos do bmu dele
		}
		
	}
	
	

	public static void main(String[] args) {
		
		// exemplos de parametrizacao e de execucao a seguir
		
		// 1 iris
		
		Dado[] dados= Inputdados.csvLeitorIris("C:\\Users\\gusta\\Desktop\\iris.csv");// imporrtar dados
		Neuronio neuronios[]= new Neuronio[64];
		iniciarNeuronios(neuronios, dados);// inicia os neuronios nos espaço dos dads
		Latice latice= new Latice(8,8);// latice 8x8
		int raio = 3;
		int nepocas=150;
		double tx=0.4;
		Decaimento decaimento = new DecaimentoLinear(150);
		Vizinhanca vizinhanca= Vizinhanca.BUBBLE;
		treinar(dados, neuronios,raio, nepocas, tx, decaimento, latice, vizinhanca);
		
		//exibicao de pos pocessamento uma nova janela grafica ira aparecer para cada represetacao
		//invocada
		new MatrizU(neuronios, dados, latice);
		new HitMap(neuronios, dados, latice);
		associardadosneuronios(dados, neuronios);//associa dados as neuronios da rede treinada
		new associacaoDados(neuronios, dados, latice);
		
		
		//exemplo de execucao 2 para 20 ng
		
		
		dados= Inputdados.csvLeitor20ng("C:\\Users\\gusta\\Desktop\\dados para som\\tf.csv");// imporrtar dados
		neuronios= new Neuronio[100];
		iniciarNeuronios(neuronios, dados);// inicia os neuronios nos espaço dos dads
		latice= new Latice(10,10);// latice 8x8
		raio = 3;
		nepocas=15;
		tx=0.6;
		decaimento = new DecaimentoLinear(15);
		vizinhanca= Vizinhanca.GAUSS;
		treinar(dados, neuronios,raio, nepocas, tx, decaimento, latice, vizinhanca);
		
		//exibicao de pos pocessamento uma nova janela grafica ira aparecer para cada represetacao
		//invocada
		new MatrizU(neuronios, dados, latice);
		new HitMap(neuronios, dados, latice);
		associardadosneuronios(dados, neuronios);//associa dados as neuronios da rede treinada
		String[]palavras= Inputdados.getPalavras("C:\\Users\\gusta\\Desktop\\dados para som\\tf.csv");
		new Palavrachave(neuronios, dados, latice, palavras);
		
		

	}

}
