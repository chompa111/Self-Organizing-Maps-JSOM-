package distancia;



public class Medir {
public static double distanciaEuclid(double p1[], double p2[]){
		
		/*recebe dois vetors p1 e p2 e retorna a distancia 
		a distancia euclidiana entre dois vetores é a raiz quadrada
		da soma de cada difereça entre as componentes dos vetores ao quadrado
		
		EX v1{1,2} e v2{3,4}
		distancia euclidiana(v1,v2)= sqrt((1-3)^2+(2-4)^2)
		*/
		
		//os tamanhos entre os vetores tem que ser iguais
		
		
			double soma=0;// para acumular a soma das diferencas das caracteristicas dos vetores
			
			for(int i=0; i<p1.length;i++){
				// para cada posicao i dos vetores
				soma+=(p1[i]-p2[i])*(p1[i]-p2[i]);//icrementamos a soma com a diferenca das posicoes ao quadrado
			}
			return Math.sqrt(soma);//retornamos a raiz quadrada desta soma
		
	}



}
