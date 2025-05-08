package util.exceptions;

public class NoFileMetadataException extends Exception {
	private static final long serialVersionUID = 8804065463391387806L;
	
	public NoFileMetadataException() {}
	
	public NoFileMetadataException(String s) {
		super(s);
	}
	
}
