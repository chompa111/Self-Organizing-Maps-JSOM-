package posProcess;

import java.awt.Color;

import java.awt.Graphics;

import javax.swing.JFrame;

import som.Dado;
import som.Latice;
import som.Neuronio;


public class DadosProximos extends JFrame {
	
	/* esta vizualizacao usa dados externos ao SOM para mostrar a quais classes um 
	 * neuronio esta associado
	 * */
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Neuronio[] neuronios;//precisaremos dos neuronios
	Dado[] dados;// dos dados
	Latice latice;//e de informacoes do latice
	
	public DadosProximos(Neuronio[] neoronios,Dado[] dados,Latice latice){
		
		//contruimos esta vizualizacao
		
		this.neuronios=neoronios;
		this.dados=dados;
		this.latice=latice;
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
		
		/*para esta vizulizacao funcionar o metodo de associar neuronio ao dado tem que ter
		 * sido invocado anteriormente */
	
		
		double tamx=1000/((latice.colunas));// tamanho do quadrado na matriz que representa o neuronio no(grafico)
		double tamy=1000/((latice.linhas));// tamanho do quadrado na matriz que representa o neuronio no(grafico)
		
		// percorreremos as posicoes matriciais para exibir um quadrado 
		//repsentando um neuronio com uma cor e a classe escrita,nas posicoes matriciais 
		
		for(int i=0;i<latice.colunas;i++){
			for(int j=0;j<latice.linhas;j++){
				
				if(neuronios[latice.getIndice(i, j)].dadosProximos.size()==0){
					/*se o neuronio da posicao ij não tem nenhum dado associado
					 * o quadrado que o representa sera preto*/
					
					g.setColor(Color.black);
					g.fillRect((int)(i*tamx),(int)(j*tamy), (int)tamx, (int)tamy);//desenha um quadrado naposicao matricial do neuronio
				}else{
				
					Dado queridinho = neuronios[latice.getIndice(i, j)].dadosProximos.get(0);
					
					/*pegamos o primeiro dado da lista de dados associados*/
				
				String s=queridinho.classe+"";
				String s2="";
				for(int k=0; k<s.length()-1;k++){
					s2+=s.charAt(k);
				}
				
				//dependend da classe do dado uma cor diferente e escolhida
				
				if(s2.equals("setosa"))g.setColor(Color.red);
				if(s2.equals("virginica"))g.setColor(Color.blue);
				if(s2.equals("versicolor"))g.setColor(Color.green);
				
				if(s.equals("sportbaseball"))g.setColor(Color.green);
				if(s.equals("sporthockey"))g.setColor(Color.green);
				
				if(s.equals("altatheism"))g.setColor(Color.YELLOW);
				if(s.equals("religionmisc"))g.setColor(Color.YELLOW);
				if(s.equals("religionchristian"))g.setColor(Color.YELLOW);
				
				if(s.equals("scimed"))g.setColor(Color.red);
				if(s.equals("scielectronics"))g.setColor(Color.red);
				if(s.equals("scicrypt"))g.setColor(Color.red);
				
		    		
		    		
				g.fillRect((int)(i*tamx),(int)(j*tamy), (int)tamx, (int)tamy);//desenhamos o quadrado que represnta o neuronio colorido
				g.setColor(Color.black);
				g.drawString(neuronios[latice.getIndice(i, j)].dadosProximos.get(0).classe+"",(int)(i*tamx)+30,(int)(j*tamy)+50);//escrevemos a classe no quadrado
				}

				
				
			}
		}
		
	}


}
