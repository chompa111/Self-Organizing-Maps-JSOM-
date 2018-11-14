package posProcess;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;


import som.Dado;
import som.Latice;
import som.Neuronio;

public class HitMap extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*essa classe tem por objetivo construir o hitmap dos neoronios
	 * quando um dado enontra seu  neoronio BMU uma variavel "hit" desse neoronio é incrementada
	 *  então podemos contruir uma vizulização onde os neoronios que foram mais vezes BMU
	 *  ficam com uma cor mais clara enquanto os que foram menos vezes ficam com uma cor escura
	 * */
	
	Neuronio[] neuronios;// precisamos dos neoronios
	Dado[] dados;//precisamos dos dados
	Latice latice;// precisasmos da iformação do latice
	
	public HitMap(Neuronio[] neoronios,Dado[] dados,Latice latice){
		
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
		
		int maxhits=0;// variavel para registrar o neoronio com mais hits para contruir
		//a escala de cores
		
		//percorreremos os neoronios buscando pelo que tem o maior numero de hits
		for(int i=0; i<neuronios.length;i++){
			if(neuronios[i].nhits>maxhits)maxhits=neuronios[i].nhits;
		}
		
		// componente serve para fazer a escala de cores se mutiplicarmos o valor maximo com a compomnente
		// obteremos 255 que é o valor maximo pra intencidade de cor
		double componente= 255.0/maxhits;
		
		
		
		double tamx=1000/((latice.colunas));// largura do quadrado que representara o neoronio no (grafico)
		double tamy=1000/((latice.linhas));//  altura do quadrado que representara o neoronio no (grafico)
		
		//percorreremos as posicoes do latice atribuindo uma cor baseada na quantidade de hits desse neoronio
		
		for(int i=0;i<latice.colunas;i++){
			for(int j=0;j<latice.linhas;j++){
				
				Color c= new Color((int)(componente*neuronios[latice.getIndice(i, j)].nhits));
				g.setColor(c);//cor atribuida com base na quantidade de hits
				
				
				g.fillRect((int)(i*tamx),(int)(j*tamy), (int)tamx, (int)tamy);// desenha o quadrado que representa o neoronio
				
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
				g.drawString(neuronios[latice.getIndice(i, j)].nhits+"",(int)(i*tamx)+30,(int)(j*tamy)+50);//escrevemos o numero de hits no quadrado
			}
		}
		
	}

}
