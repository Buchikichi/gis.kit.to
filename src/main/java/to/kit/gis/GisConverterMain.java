package to.kit.gis;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GisConverterMain {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(GisConverterMain.class);

	private static final int STEPS = 4;
	private static final int COLUMNS = 10800;
	private static final int ROWS = 6000;
	private static final int WIDTH = COLUMNS / STEPS;
	private static final int HEIGHT = ROWS / STEPS;

	private short[] loadGIS() throws IOException {
		short[] array = new short[COLUMNS * ROWS];
		int ix = 0;

		try (InputStream in = GisConverterMain.class.getResourceAsStream("/h10g")) {
			while (0 < in.available()) {
				int ch1 = in.read();
				int ch2 = in.read();

				array[ix] = (short) ((ch2 << 8) + (ch1 << 0));
				ix++;
			}
		}
		return array;
	}

	private Color getColor(int elevation) {
		Color c;

		if (elevation == - 500) {
			c = new Color(0, 0, 128);
		} else if (elevation < 0) {
			c = new Color(0, 0, 255 + elevation);
		} else if (elevation == 0) {
			c = Color.BLACK;
		} else {
			int g = 255 - (int) (elevation * 200.0 / 7491.0);
			c = new Color(0, g, 0);
		}
		return c;
	}

	public void execute() throws IOException {
		LOG.debug("load");
		short[] array = loadGIS();
		LOG.debug("load:" + array.length);
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		int y = 0;

		for (int row = 0; row < ROWS; row += STEPS) {
			int x = 0;
			for (int col = 0; col < COLUMNS; col += STEPS) {
				int ix = row * COLUMNS + col;
				int elevation = array[ix];
				Color c = getColor(elevation);
				g.setColor(c);
				g.drawLine(x, y, x, y);
				x++;
			}
			y++;
		}
		ImageIO.write(image, "png", new File("gis.png"));
		LOG.debug("done.");
	}

	public static void main(String[] args) throws Exception {
		GisConverterMain app = new GisConverterMain();

		app.execute();
	}
}
