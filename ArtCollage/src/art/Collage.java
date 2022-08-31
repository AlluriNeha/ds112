package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color
    // of the pixel on the screen.
    private int tileDimension;

    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension
     * x tileDimension*collageDimension,
     * where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling
     * filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage(String filename) {

        this(filename, 150, 4);
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension
     * x tileDimension*collageDimension,
     * where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling
     * filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage(String filename, int td, int cd) {

        this.collageDimension = cd;
        this.tileDimension = td;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture(tileDimension * collageDimension, tileDimension * collageDimension);
        Color black = Color.black;
        int x = collagePicture.width();
        int y = collagePicture.height();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                collagePicture.set(i, j, black);
            }
        }
        scale (originalPicture,collagePicture);
    }

    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * 
     * @target. Do not update @source.
     * 
     * @param source is the image to be scaled.
     * 
     * @param target is the
     */
    public static void scale(Picture source, Picture target) {

        int sourceH = source.height();
        int sourceW = source.width();
        int targetH = target.height();
        int targetW = target.width();

        for (int i = 0; i < targetW; i++) {
            for (int j = 0; j < targetH; j++) {
                int k = i * sourceW / targetW;
                int l = j * sourceH / targetH;
                Color color = source.get(k, l);
                target.set(i, j, color);
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */

    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */

    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {
        collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles,
     * where each tile has tileDimension X tileDimension pixels.
     */
    public void makeCollage() {

        Picture picture2 = new Picture(tileDimension, tileDimension);
        scale(originalPicture, picture2);

        for (int i = 0; i < tileDimension; i++) {
            for (int j = 0; j < tileDimension; j++) {
                Color color = picture2.get(i, j);
                for (int k = 0; k < collageDimension; k++) {
                    for (int l = 0; l < collageDimension; l++) {
                        collagePicture.set(k * tileDimension + i, l * tileDimension + j, color);
                    }
                }
            }
        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component
     * (see Week 9 slides, the code for color separation is at the
     * book's website)
     *
     * @param component is either red, blue or green
     * 
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void colorizeTile(String component, int collageCol, int collageRow) {

        int c = -1;
        if (component.equalsIgnoreCase("red"))
            c = 0;
        if (component.equalsIgnoreCase("blue"))
            c = 1;
        if (component.equalsIgnoreCase("green"))
            c = 2;
        if (c < 0 || collageCol < 0 || collageRow < 0) {
            return;
        }

        for (int i = 0; i < tileDimension; i++) {
            for (int j = 0; j < tileDimension; j++) {
                int row = collageRow * tileDimension + j;
                int col = collageCol * tileDimension + i;
                Color color = collagePicture.get(col, row);
                Color colour = null;
                if (c == 0) {
                    colour = new Color(color.getRed(), 0, 0);
                } else if (c == 1) {
                    colour = new Color(0, color.getBlue(), 0);
                } else {
                    colour = new Color(0, 0, color.getGreen());
                }
                collagePicture.set(col, row, colour);
            }
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * 
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void replaceTile(String filename, int collageCol, int collageRow) {

        Picture picture = new Picture(filename);
        Picture next = new Picture(tileDimension, tileDimension);
        scale(picture, next);
        for (int i = 0; i < tileDimension; i++) {
            for (int j = 0; j < tileDimension; j++) {
                int row = collageRow * tileDimension + j;
                int col = collageCol * tileDimension + i;
                collagePicture.set(col, row, next.get(i, j));
            }
        }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * 
     * @param collageRow tile row
     */
    public void grayscaleTile(int collageCol, int collageRow) {
        for (int i = 0; i < tileDimension; i++) {
            for (int j = 0; j < tileDimension; j++) {
                int row = collageRow * tileDimension + j;
                int col = collageCol * tileDimension + i;
                collagePicture.set(col, row, toGray(collagePicture.get(col, row)));
            }
        }

    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b)
            return r; // to avoid floating-point issues
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color))); // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow() {
        if (originalPicture != null) {
            originalPicture.closeWindow();
        }
        if (collagePicture != null) {
            collagePicture.closeWindow();
        }
    }
}
