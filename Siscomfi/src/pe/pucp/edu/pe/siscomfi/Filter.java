package pe.pucp.edu.pe.siscomfi;

import java.io.*;
import java.awt.image.*;

public abstract class Filter {

	public abstract BufferedImage filter(BufferedImage image, BufferedImage output);

	public BufferedImage filter(BufferedImage image) {
		return filter(image, null);
	}

	public BufferedImage verifyOutput(BufferedImage output, BufferedImage input) {
		return verifyOutput(output, input.getWidth(), input.getHeight(), input.getType());
	}

	public BufferedImage verifyOutput(BufferedImage output, BufferedImage input, int type) {
		return verifyOutput(output, input.getWidth(), input.getHeight(), type);
	}

	public BufferedImage verifyOutput(BufferedImage output, int width, int height, int type) {

		if (output != null && output.getWidth() == width && output.getHeight() == height && output.getType() == type)
			return output;

		return new BufferedImage(width, height, type);
	}
}
