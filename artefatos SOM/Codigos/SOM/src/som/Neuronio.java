package som;
import java.util.ArrayList;

public class Neuronio {
	
	public int id;// indice muito importante para fazer opea��es com latice 
	public double[] pesos;// pesos do neoronio
	public int nhits=0;// numero de vezes que foi bmu(usado nas an�lises)
	
	/*para fazer um pos processamento mais interrentante seria bom termos
	 * uma lista de dados associdada ao seu BMU e essa lista ser� armazenada 
	 * no array list a seguir, em outras palavras dados que s�o representados pelo BMU*/
	public ArrayList<Dado>dadosProximos= new ArrayList<Dado>();
	
	
	public Neuronio(int id,double[]min,double[]max){
		
		/*os vetores min e max servem para iniciar os neoronios em posi��es
		 * aleatoria, por�m dentro do espa�o onde est�o os dados
		 * eles registram os valores maximos e minimos de cada cordenada
		 * do conjunto de dados
		 * */
		
		this.id=id;//recebe um id
		pesos= new double[min.length];// criando o vetor de pesos no mesmo espa�o que os dados"variavel numcord"
		for(int i=0;i<min.length;i++){
			
			double range =max[i]-min[i];//registra o rande da coordenada i
			
			pesos[i]=min[i]+Math.random()*range;
			
		}
		
	}
}
