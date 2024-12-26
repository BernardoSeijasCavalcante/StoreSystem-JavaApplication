package resources;

public class TaxException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public TaxException() {
		super("Erro: Espécie de Imposto não identificada!");
	}
}
