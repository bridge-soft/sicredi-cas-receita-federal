package felipeachutti.sicredi.directorylistener;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import felipeachutti.sicredi.fileprocessor.FileProcessor;
import felipeachutti.sicredi.fileprocessor.FileProcessorFactory;

public class InDirectoryListener implements DirectoryListener {

	private final String filePath;

	InDirectoryListener(String filePath) {
		this.filePath = filePath;
	}

	public void run() {
		try {

			WatchService inPathWatcher = FileSystems.getDefault().newWatchService();
			Path inPath = Paths.get(System.getenv("HOMEPATH") + filePath);

			processExistingFiles(inPath);

			inPath.register(inPathWatcher, StandardWatchEventKinds.ENTRY_CREATE);

			WatchKey inPathWatchkey;

			while ((inPathWatchkey = inPathWatcher.take()) != null) {
				for (WatchEvent<?> event : inPathWatchkey.pollEvents()) {
					if (event.context() instanceof Path) {
						Path fileFullPath = inPath.resolve((Path) event.context());
						processFile(fileFullPath);
					}
				}
				inPathWatchkey.reset();
			}
		} catch (IOException e) {
			System.out.println(String.format("Erro no monitoramento de entrada dos dados: %s", e.getMessage()));
		} catch (Exception e) {
			System.out.println(String.format("Erro desconhecido: %s", e.getMessage()));
		}
	}

	private void processFile(Path fileFullPath) {
		FileProcessor fileProcessor = FileProcessorFactory.getFileProcessor(fileFullPath);

		if (fileProcessor != null) {
			ExecutorService executor = Executors.newCachedThreadPool();
			executor.submit(fileProcessor);
		}
	}

	private void processExistingFiles(Path inPath) {
		if (inPath.toFile() != null && inPath.toFile().exists()) {
			for (File file : inPath.toFile().listFiles()) {
				processFile(Paths.get(file.getAbsolutePath()));
			}
		}
	}
}