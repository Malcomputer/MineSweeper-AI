package AI.Robybot.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GameIcons {
    public final int[] pixels;
    public final int width;
    public final int height;

    public GameIcons(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        pixels = new int[width * height];

        SampleModel sm = image.getRaster().getSampleModel();
        if (image.getType() == BufferedImage.TYPE_INT_RGB &&
                sm.getDataType() == DataBuffer.TYPE_INT &&
                Arrays.equals(sm.getSampleSize(), new int[]{8, 8, 8})) {  // Fast path

            int[] temp = image.getRaster().getPixels(0, 0, width, height, (int[]) null);
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = temp[i * 3 + 0] << 16
                        | temp[i * 3 + 1] << 8
                        | temp[i * 3 + 2] << 0;
            }

        } else {  // General path
            image.getRGB(0, 0, width, height, pixels, 0, width);
            for (int i = 0; i < pixels.length; i++)
                pixels[i] &= 0xFFFFFF;  // Get rid of alpha channel
        }
    }

    public GameIcons(String filename) {
        this(readFile("src/resources/auto/" + filename));
    }


    public boolean equals(GameIcons other, int offX, int offY) {
        if (other.width < width || other.height < height)
            throw new IllegalArgumentException();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (other.pixels[(offY + y) * other.width + offX + x] != pixels[y * width + x])
                    return false;
            }
        }
        return true;
    }


    private static BufferedImage readFile(String filename) {
        File file = new File(filename);
        if (!file.isFile()) {
            System.err.println("File does not exist: " + file);
            System.exit(1);
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}