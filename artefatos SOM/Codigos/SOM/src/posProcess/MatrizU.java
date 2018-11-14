package posProcess;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;


import distancia.Medir;
import som.Dado;
import som.Latice;
import som.Neuronio;


public class MatrizU extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	/*esta implementação da geração da matriz U é baseada nos slides da aula
	 * de Clustering
	 * */
	
	
	//dados necessarios para computar a matriz U
	Neuronio[] neuronios;
	Dado[] dados;
	Latice latice;
	
	public MatrizU(Neuronio[] neuronios,Dado[] dados,Latice latice){
		//contruimos a vizualizacao da matriz U
		
		this.neuronios=neuronios;
		this.dados=dados;
		this.latice=latice;
		setSize(1000, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		revalidate();
	}
	
	/*a seguir cada uma das funcoes necessarias para implementar a matriz U
	 * todas elas dependem de infomrações do (epaco matricial)*/
	
	double dx(int x,int y){
		// a partir de um neuronio da posicao xy
		// calcula o coeficiente dx que é a distancia para o visinho da direita do espaço
		//matricial
		
		
		Neuronio aux= neuronios[latice.getIndice(x, y)];//pega o neoronio xy
		Neuronio auxdireita=neuronios[latice.getIndice(x+1, y)];
		
		return Medir.distanciaEuclid(aux.pesos,auxdireita.pesos);
		
	}
	
	double dy(int x,int y){
		// a partir de um neuronio da posicao xy
		// calcula o coeficiente dx que é a distancia para o visinho de baixo do espaço
		//matricial
		
		Neuronio aux= neuronios[latice.getIndice(x, y)];//pega o neoronio xy
		Neuronio auxbaixo=neuronios[latice.getIndice(x, y+1)];// pega o neoronio debaixo
		
		return Medir.distanciaEuclid(aux.pesos,auxbaixo.pesos);
	}
	
	double dz(int x,int y){
		// a partir de um neuronio da posicao xy
		// dz é a media das distancias entre os vizinhos debaixo e da direita e os vizinhos
		// e do neoronio xy com o da diagonal
		
		Neuronio aux= neuronios[latice.getIndice(x, y)];//pega o neoronio xy
		Neuronio auxdirbaixo= neuronios[latice.getIndice(x+1, y+1)];//pega o neoronio a direita e ebaixo 
		// do neoronio xy
		double dxy=Medir.distanciaEuclid(aux.pesos,auxdirbaixo.pesos);
		
		Neuronio auxbaixo=neuronios[latice.getIndice(x, y+1)];// pega o neoronio debaixo
		Neuronio auxdireita=neuronios[latice.getIndice(x+1, y)];// pega o neorio a direita
		
		double dyx=Medir.distanciaEuclid(auxbaixo.pesos,auxdireita.pesos);
		
		return (dxy+dyx)/2;
	}
	double du(int x, int y){
		return -1;
		/* o retorno -1 é utilizado para identificar na matriz a posiçã com du e substitui-la 
		 * pela media dos valores adjacentes 
		 */
		 
	}
	
	
	
	
	@Override
	public void paint(Graphics g){
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		
		// preenchendo a matrizU utilizando a regra par-par par-impar,impar-par,impar-impar
		//vista nos slides em aula
		
		double[][]matriz=new double[2*(latice.linhas)-1][2*(latice.colunas)-1];	
		for(int x=0;x<2*(latice.colunas)-1;x++){
			for(int y=0;y<2*(latice.linhas)-1;y++){
				
				if(x%2==0 && y%2==0){
					matriz[x][y]=du(x/2,y/2);
				}
				if(x%2==0 && y%2!=0){
					matriz[x][y]=dy(x/2,(y-1)/2);
				}
				if(x%2!=0 && y%2==0){
					matriz[x][y]=dx((x-1)/2,y/2);
				}
				if(x%2!=0 && y%2!=0){
					matriz[x][y]=dz((x-1)/2,(y-1)/2);
				}
			}
		}
		
		//definindo du como a media dos adjacentes
		
		for(int x=0;x<2*(latice.colunas)-1;x++){
			for(int y=0;y<2*(latice.linhas)-1;y++){
				if(matriz[x][y]==-1){// celula marcada com -1
					double somatoria=0;// variavel para fazer a media
					int n=0;
					/*abaixo temos que conferir se existem posições adjacentes para 
					 * constituie a media
					 * */
					
					if(x+1<2*(latice.colunas)-1){
						n++;
						somatoria+=matriz[x+1][y];
					}
					if(x-1>0){
						n++;
						somatoria+=matriz[x-1][y];
					}
					if(y+1<2*(latice.linhas)-1){
						n++;
						somatoria+=matriz[x][y+1];
					}
					if(y-1>0){
						n++;
						somatoria+=matriz[x][y-1];
					}
					matriz[x][y]=somatoria/n;// media dos termos
					
				}
			}
		}
		
		/*para construir a escala de cor buscaremos pelo valor da maior distancia
		 * a fim de faze-la o maximo da escala e colocar esse valor na variavel maxdist
		 * */
		double maxdist=Double.MIN_VALUE; 
		
		for(int x=0;x<2*(latice.colunas)-1;x++){
			for(int y=0;y<2*(latice.linhas)-1;y++){
				if(matriz[x][y]>maxdist)maxdist=matriz[x][y];
			}
		}
		
		double proporcao=255/maxdist;// proporçãoo para construir a escala de cores
		
		double tamx=1000/(2*(latice.colunas)-1);// tamanho do quadrado na matriz(grafico)
		double tamy=1000/(2*(latice.linhas)-1);//tamanho do quadrado na matriz (grafico)
		
		for(int x=0;x<2*(latice.colunas)-1;x++){
			for(int y=0;y<2*(latice.linhas)-1;y++){
				
				Color c= new Color(255-(int)(matriz[x][y]*proporcao),0,0);// quanto mais distante mais esccuro
				g.setColor(c);
				g.fillRect((int)(x*tamx),(int) (y*tamy), (int)tamx,(int) tamy);//desenha o quadrado
			}
		}
		
	}

}
