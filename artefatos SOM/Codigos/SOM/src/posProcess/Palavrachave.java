package posProcess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;


import som.Dado;
import som.Latice;
import som.Neuronio;

public class Palavrachave extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// o intuito deste grafico e vizualizar para cada neuronio as palavras mais em comuns
	// entre os textos que estão associadas aos neuronios
	/*ou seja espera-se que se um neuronio em questão é bmu para 60 textos de religiao crista,
	 * o  esperado e que vejamos palavras como jesus , deus cristo algo assim..
	 * isso sera realizado com todos os neoronios e a vizualizacao vai respeitar a posicao do neuronio
	 * no (espaco matricial), os neoronios que não tiverem textos associados ficarao em palavras
	 * */
	

		Neuronio[] neuronios;//precisaremos dos neuronios
		Dado[] dados;//precisaremos dos dados
		Latice latice;//precisaremos de informacoes do latice
		String []palavras;//precisaremos de um vetor contendo todas as palavras
		
		public Palavrachave(Neuronio[] neuronios,Dado[] dados,Latice latice,String palavras[]){
			// contruimos a vizualizacao 
			
			this.neuronios=neuronios;
			this.dados=dados;
			this.latice=latice;
			this.palavras=palavras;
			setSize(1000, 1000);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true);
			revalidate();
		}
		
		

		@Override
		public void paint(Graphics g){
			//primeiro pintar o fundo de branco
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1000, 1000);
			
			
			double tamx=1000/((latice.colunas));// largura do quadrado que representara o neuronio no (grafico)
			double tamy=1000/((latice.linhas));//  altura do quadrado que representara o neuronio no (grafico)
			
			int ncord=dados[0].coordenadas.length;// numero de coordenadas das cacteristicas do dado
			
			//a seguir percorreremos as posicoes matriciais
			for(int i=0;i<latice.colunas;i++){
				for(int j=0;j<latice.linhas;j++){
					
					Neuronio n=neuronios[latice.getIndice(i, j)];// neuronio que reside na posicao matricial ij
					
					// para cada neuronio pegaremos a lista de dados asociados
					ArrayList<Dado> dadosAssociados = n.dadosProximos;
					
					if(dadosAssociados.size()==0)continue;// se o neuronio nao tem dados assciados pulamos
					
					double[]intersecInterna= new double[ncord];// vetor para aculumar a soma das caracteriticas 
					// de cada dado associado
					for(Dado d: dadosAssociados){
						for(int cordenada=0; cordenada<ncord;cordenada++){
							intersecInterna[cordenada]+=d.coordenadas[cordenada]+(Math.random()/1000);
						}
					}
					
					double[] clone=intersecInterna.clone();
					
					/*ordena uma copia da soma em ordem decrescente
					 * */
					for(int aux=0;aux<clone.length;aux++)clone[aux]*=-1;
					Arrays.sort(clone);
					for(int aux=0;aux<clone.length;aux++)clone[aux]*=-1;
					
					// depois disso temos o vetor "clone" organizado em ordem decrescente
					
					// agora exibiremos na posicao ij do neuronio as palavras com maior ponderação do vetor clone
					
					//5 das palavras com maior soma serao mostradas
					for(int p=0;p<5;p++){
						double chave=clone[p];
						
						for(int h=0;h<ncord;h++){
							if(chave==intersecInterna[h]){
								
								g.setColor(Color.BLACK);
								g.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
								g.drawString(palavras[h],(int)(i*tamx)+20,(int)(j*tamy)+p*8+50);//escrevemos a palavra na posicao matricial do neuronio
								break;
							}
						}
					}
					
					
				}//for das colunas	
			}// for das linhas
			
			
		}//paint
		
}
		