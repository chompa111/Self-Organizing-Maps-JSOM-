package qualidade;




import distancia.Medir;
import som.Dado;
import som.Latice;
import som.Neuronio;


public class Erro {
	
	public static double distorcaoTopologica(Dado[] dados, Neuronio[] neuronios,Latice latice){
		
		/*A distorn��o topologica ser� calculada pelo metodo mostrado nos slides.
		 * seja um dado d computamos funcao u(d); 
		 * 
		 * u(d)=0 se para o dado d, o primeiro e o segundo BMU para d s�o adjacentes no espa�o 
		 * matricial,
		 *  por isso precisaremos encontrar o Primeiro e o segundo BMU de um dado.
		 *  A distro��o topologica m�dia � a media de u(di) para todo dado "di"
		 */
		
		double somaU=0;// variavel para acumular a soma de todos os u(d).
		
		for(Dado d: dados){
			
			
			double mindist=Double.MAX_VALUE;// minima distancia (para encontrar o BMU)
			Neuronio primeiroBmu = null;//candidato a primeiro BMU
			for(Neuronio n: neuronios){//determos que verificar todos os neuronios
				
				double distancia =Medir.distanciaEuclid(d.coordenadas,n.pesos);//distancia de n e d
				if(distancia<=mindist){
					// se a distancia � menor que a minima registrada
					
					mindist=distancia;//a minima distancia agora � esta
					primeiroBmu=n;// e o candidato a bmu � este
				}
			}
			// ao final de percorrer todos os neoronios a variavel primeiroBmu
			//guarda de fato o primeiroBmu.
			
			/*Precisamos encontrar o segundoBmu trata-se do mesmo processo de encontrar
			 * o primeiro bmu s� que ignorando ele desta vez
			 */
			mindist=Double.MAX_VALUE;
			Neuronio segundoBmu=null;
			
			for(Neuronio n: neuronios){
				
				double distancia = Medir.distanciaEuclid(d.coordenadas,n.pesos);
				
				if(distancia<=mindist && n!=primeiroBmu){
					/*se a distancia medida entre n e d � a menor que a minima registrada e 
					 * n n�o � o primeiro bmu
					 * */
					mindist=distancia;//minima distancia atualizada
					segundoBmu=n;// o segundoBmu � n por enquanto
				}
			}
			/*ao final de verificar todos os neoronios temos certeza
			 * que a variavel segundoBmu guarda defato o segundo BMU
			*/
			
			/*para calcular u(d) agora temos que verificar se o primeiro e o segundo s�o vizinhos 
			 * matriciais
			 */
			// considerando latice GRID
			
			double u=0;
			int px=latice.getColuna(primeiroBmu.id);//coluna onde o primeiro bmu esta
			int py=latice.getLinha(primeiroBmu.id);//linha onde o primeiro bmu esta
			int sx=latice.getColuna(segundoBmu.id);//coluna onde o segundo bmu esta
			int sy=latice.getLinha(segundoBmu.id);//coluna onde o segundo bmu esta
			
			int dx=Math.abs(px-sx);// varia��o em x
			int dy=Math.abs(py-sy);//varia��o em y
			
			if(dx>1)u=1;
			if(dy>1)u=1;
			if(dx==1 && dy==1)u=1;
			
			somaU+=u;
			
		}
		return somaU/dados.length;
		
	}


}
