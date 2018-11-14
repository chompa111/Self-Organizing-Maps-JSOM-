package Input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import som.Dado;

public class Inputdados {
	
	/*esta classe tem por objetivo importar dados em csv e criar vetores da Classe Dado
	 * com as informações do cvs para os algoritmos da classe aprendizado poderem trabalhar
	 * 
	 * dois leitores de cvs foram feitos um deles para extrair as infomrações do iris
	 * e o outro para o 20 news group
	 * */
	
	
	

public static	Dado[] csvLeitorIris(String path){
	
	/*le os dados do documento passao pelo seu path*/
	
	/*no documento iris.csv , cada dado esta em uma linha e as caracteristicas
	 * do dado estão separadas por virgula
	 * mas uma das caracteristicas a ultima na sequencia de cada dado e a classe da flor
	 * e isso sera levado em consideracao
	 * */
	
	ArrayList <Dado>auxdados= new ArrayList<Dado>();//lista temporaria de dados
	
	try(BufferedReader br = new BufferedReader(new FileReader(path))) {
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();// todo documento de texto esta nessa string
	    int cont=0;// variavel para atribuir um id ao dado 
	    for(String s:everything.split("\n")){//separamos as linhas do bloco fonte
	    	/*cada string s corresponde a um dado*/
	    	
	    	int ncord=s.split(",").length;// numero de dimensos do dado
	    	
	    	Dado d=new Dado(cont,ncord-1);//construimos um objeto dado com o numero de dimensos corretas
	    	
	    	d.classe=s.split(",")[ncord-1];
	    	
	    	/* com o objeto dado criado agora temos que colocar valores nas suas caracteristicas
	    	 * 
	    	 * */
	    	for(int h=0;h<ncord-1;h++){
	    		//para cada uma das cordenadas coloca no objeto dado o valor retirado do csv
	    		d.coordenadas[h]=Double.parseDouble(s.split(",")[h]);
	    	}
	    	auxdados.add(d);// adiciona o dado na lista temporaria de dados
	    	cont++;// incrementa o contador de id´s
		}
	    
	    /*
	     * como a nossa implementacao trabalha com vetor de dados
	     * converteremos o arraylist de dados para poder retornar um vetor
	     * */
	    Dado[]saida=new Dado[auxdados.size()];//vetor criado com a mesma quantidade de itens que a lista temporaria
	    int i=0;
	    for(Dado d:auxdados){
	    	saida[i]=d;
	    	i++;
	    }
	    return saida; //retorna vetor de dados
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null;
}
	

public static	Dado[] csvLeitor20ng(String path){

		/*Um pouco diferente do iris os dados do 20ng estao na seguinte configuracao:
		 * primeiro, a lista de todas as palavras,depois para cada linha temos um dado
		 * nesta linha separado por virgulas estão as caracteristicas do dado sendo que a primeira
		 * caracteristica na verdade é o id e a classe do texto separado por hifem, as demais caracteristicas 
		 * são numericas  
		 */
	
		ArrayList <Dado>auxdados= new ArrayList<Dado>();//lista temporaria de dados a serem retornados
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();// todo texto esta nessa string
		   
		    String [] linha=everything.split("\n");// separa em dados(as linhas)
		    
		    
		    for(int i=1;i<linha.length;i++){//pula a primeira linha que são (as palavras)
		    	
		    	String[]coords=linha[i].split(",");//separa as caracteristicas do dado
		    	
		    	int ncord=coords.length-1;// descontamos o id
		    	
		    	String[] identificação=coords[0].split("-");
		    	
		    	Dado d=new Dado(Integer.parseInt(identificação[0]),ncord);//construi um dado
		    	d.classe=identificação[1];//coloca sua calssificação
		    	for(int j=1;j<coords.length;j++){
		    		d.coordenadas[j-1]=Float.parseFloat(coords[j]);//passa as coordenadas para o dado
		    	}
				auxdados.add(d);// adciciona a lista de dados
		    }
		    /*a seguir o arrayList de dados é convertido para vetor*/
		    
		    Dado[]saida=new Dado[auxdados.size()];
		    int i=0;
		    for(Dado d:auxdados){
		    	saida[i]=d;
		    	i++;
		    }
		    return saida;//retorna o vetor
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	
public static String[] getPalavras(String path){
	
		/*para fazer um pos processamento interessante precisamos das palavras que foram usadas
		 * na representacao dos textos 
		 * este metodo tem por objetivo retornar um vetor de string com estas palavras
		 * com base no path do documento utilizado.
		 * */
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();//todo csv em testo esta nessa string
		   
		    String [] linha=everything.split("\n");
		    String[]auxPalavras =linha[0].split(",");//as palavras da representacao estao na primeira linha
		    String resp[]=new String[auxPalavras.length-1];
		    
		    /*andamos uma posicao para tras por que a primeira palavra nao é valida
		     * e apenas um ideitificador 
		     * */
		    
		    for(int i=1;i<auxPalavras.length;i++){
		    	resp[i-1]=auxPalavras[i];
		    }
		    return resp;// retornamos o conjunto de palavras
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
