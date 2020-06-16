package felipeachutti.sicredi.directorylistener;

import felipeachutti.sicredi.config.ApplicationProperties;

public class DirectoryListenerFactory {

	public enum Listeners {
		IN_DIRECTORY_LISTENER
	}

	public static DirectoryListener getDirectoryListener(Listeners value) {
		switch (value) {
		case IN_DIRECTORY_LISTENER:{
			return new InDirectoryListener(ApplicationProperties.get("data.input.directory"));
		}
		default:
			throw new IllegalArgumentException();
		}

	}

}
