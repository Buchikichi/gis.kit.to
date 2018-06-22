package to.kit.gis;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.EndianUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import to.kit.gis.shape.ShapeHeader;

public class ShapeTestMain {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ShapeTestMain.class);

	private static final String RES_NAME = "/PL_hiroshima_01.shp";

	private void load() throws IOException {
		ShapeHeader header = new ShapeHeader();

		try (InputStream in = ShapeTestMain.class.getResourceAsStream(RES_NAME);
				DataInputStream data = new DataInputStream(in)) {
			data.read(header.getCodes());

			header.setFileLength(data.readInt());
//			LOG.debug("fileLength:" + fileLength + "(" + String.format("%06x", Integer.valueOf(fileLength)) + ")");
			header.setVersion(EndianUtils.readSwappedInteger(in));
			header.setShapeType(EndianUtils.readSwappedInteger(in));
			double xMin = EndianUtils.readSwappedDouble(in);
			double yMin = EndianUtils.readSwappedDouble(in);
			double xMax = EndianUtils.readSwappedDouble(in);
			double yMax = EndianUtils.readSwappedDouble(in);
			double zMin = EndianUtils.readSwappedDouble(in);
			double zMax = EndianUtils.readSwappedDouble(in);
			double mMin = EndianUtils.readSwappedDouble(in);
			double mMax = EndianUtils.readSwappedDouble(in);

			// レコード・ヘッダ
			int recNum = data.readInt();
			LOG.debug("recNum:" + recNum);
			int contentLength = data.readInt();
			LOG.debug("contentLength:" + contentLength);
		}
	}

	public void execute() throws IOException {
		load();
	}

	public static void main(String[] args) throws Exception {
		ShapeTestMain app = new ShapeTestMain();

		app.execute();
	}
}
