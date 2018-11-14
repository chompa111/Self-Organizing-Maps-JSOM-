package som;

public class Dado {
	
	//esta classe tem por objetivo armazenar as informacoes de um dado que sera 
	//manipulado durante o treinamento do SOM
	
	/*nesta implementacao de SOM os dados serao lidados em um vetor desta classe*/
	
	/*a inicializacao com dados que o usuario queira, depende da construcao de um 
	 * metodo que crie os objetos "Dado" e coloque as caracteristicas no vetor "coordendas"
	 * os metodos usados para criar importar e criar o vetor da classe dado estão no pacote 
	 * (input)
	 * */
	
	public String classe=""; //classificacao do dado (caso ele possua) "para pos process" 
	public int id; // identificação do dado
	public double[]coordenadas; // caracteristicas do dado
	
	public Dado(int id, int d){
		//construtor
		this.id=id;
		coordenadas=new double[d];
	}
	
}
