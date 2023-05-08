public class MusicTrack {
	private String fileName;
	private String description;
	
	public MusicTrack(String fileName, String description) {
		this.fileName = fileName;
		this.description = description;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getDescription() {
		return this.description;
	}
}