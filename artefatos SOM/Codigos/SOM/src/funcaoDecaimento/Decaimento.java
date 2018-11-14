package funcaoDecaimento;

public interface Decaimento {
	
	// uma função de decaimento de aprendizado tem que ter o metodo de obter decaimento
	// da taxa de aprendizado baseado no numero de epoxas
	// para isso essa interface foi criada
	
	double getfatorDecaimento(int epocas);
	

}
