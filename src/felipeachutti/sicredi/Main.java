package felipeachutti.sicredi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import felipeachutti.sicredi.directorylistener.DirectoryListenerFactory;

public class Main {

	public static void main(String[] args) {

		System.out.println("Algumas considerações");
		System.out.println("---------------------");
		
		System.out.println("- A IDE em que o código for executado, deve estar com encoding UTF-8.");
		System.out.println("- A Pasta (data\\in) e (data\\out) devem existir. Ex: C:\\Users\\meu_usuario\\data\\in e data\\out");		
		System.out.println("- A extensão dos arquivos dentro da pasta (data\\in) devem ser (.cvs).");	
		System.out.println("----------------------------------------------------------------");
		
		
		System.out.println("\nExecutando listener de entrada");

		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(DirectoryListenerFactory.getDirectoryListener(DirectoryListenerFactory.Listeners.IN_DIRECTORY_LISTENER));
		
		System.out.println("\nAgora é possível executar outras atividades em paralelo!");
	}

}
