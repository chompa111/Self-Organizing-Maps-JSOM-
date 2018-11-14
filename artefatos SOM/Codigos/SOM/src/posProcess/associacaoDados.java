package posProcess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import som.Dado;
import som.Latice;
import som.Neuronio;
import som.Aprendizado;

public class associacaoDados extends JFrame {
	
	/*essa vizualizacao tem por objetivo mostrar a quantos dados um neronio esta associado
	 * depois que a rede estiver treinada para cada neoronio veremos quantos dados para os quais
	 * ele é bmu.
	 * */
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Neuronio[] neuronios;// prcisamos dos neoronios
	Dado[] dados;// precisamos dos dados
	Latice latice;// precisamos do latice
	
	public associacaoDados(Neuronio[] neuronios,Dado[] dados,Latice latice){
		this.neuronios=neuronios;
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
		
		
		Aprendizado.associardadosneuronios(dados, neuronios);
		/*o metodo assima coloca para cada neoronio numa lista interna do neoronio
		 * todos os dados para os quais ele é bmu.
		 * */
		
		
		int maxDados=0;// variavel para registrar o neoronio com mais dados associdados a ele
		//para construir a escala de cores
		
		
		
		//percorreremos os neoronios buscando pelo que tem o maior numero de dados associados
		for(int i=0; i<neuronios.length;i++){
			if(neuronios[i].dadosProximos.size()>maxDados)maxDados=neuronios[i].dadosProximos.size();
		}
		
		// componente serve para fazer a escala de cores se mutiplicarmos o valor maximo com a compomnente
		// obteremos 255 que é o valor maximo pra intencidade de cor
		double componente= 255.0/maxDados;
		
		
		
		double tamx=1000/((latice.colunas));// largura do quadrado que representara o neoronio no grafico
		double tamy=1000/((latice.linhas));// altura do quadrado que represntara o neoronio do grafico
		
		// percorreremos os neoronios pelas suas posicoes matricias 
		for(int i=0;i<latice.colunas;i++){
			for(int j=0;j<latice.linhas;j++){
				
				Color c= new Color(0,(int)(componente*neuronios[latice.getIndice(i, j)].dadosProximos.size()),0);
				//cor é criada baseada na quantidade de dados que o neoronio na posicao ij tem associado 
				g.setColor(c);//cor atribuida
				
				g.fillRect((int)(i*tamx),(int)(j*tamy), (int)tamx, (int)tamy);//quadrado que representa o neoronio
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
				g.drawString(neuronios[latice.getIndice(i, j)].dadosProximos.size()+"",(int)(i*tamx)+30,(int)(j*tamy)+50);
				
				/*
				 * também escrevemos no neoronio a quantidade de dados associados a ele*/
			}
		}
		
	}

}
