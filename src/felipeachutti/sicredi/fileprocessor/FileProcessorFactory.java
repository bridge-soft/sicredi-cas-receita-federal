package felipeachutti.sicredi.fileprocessor;


import java.nio.file.Path;

public class FileProcessorFactory {
	
	public static FileProcessor getFileProcessor(Path path){
		if(path.toFile().isFile()){
			String fileExtension = path.toString().substring(path.toString().lastIndexOf('.') + 1).toLowerCase();
			
			switch(fileExtension){
				case "cvs": return new DatFileProcessor(path);
				default: return null;
			}
		}
		return null;
	}

}
