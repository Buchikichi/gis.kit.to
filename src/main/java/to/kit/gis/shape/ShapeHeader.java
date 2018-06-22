package to.kit.gis.shape;

public class ShapeHeader {
	private final byte[] codes = new byte[24];
	private int fileLength;
	private int version;
	private int shapeType;

	public byte[] getCodes() {
		return this.codes;
	}
	public int getFileLength() {
		return this.fileLength;
	}
	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}
	public int getVersion() {
		return this.version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getShapeType() {
		return this.shapeType;
	}
	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}
}
