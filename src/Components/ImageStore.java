package Components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.image.Image;

public class ImageStore {

    public static Image cardBackImage;
    public static HashMap<String, Image> cardFaceImages;

    private static Image loadImage(File file) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(file));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return image;
    }

    public static void loadImages() {
        cardFaceImages = new HashMap();
        cardBackImage = null;
        
        File path = new File("C:\\Users\\janne\\OneDrive\\Asiakirjat\\NetBeansProjects\\KorttipelitFX\\src\\Images\\");
        File[] files = path.listFiles();
        Image[] images = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                images[i] = loadImage(files[i]);
            }
        }
        int i = 0;
        for (Suit suit : Suit.values()) {
            cardFaceImages.put("10_of_" + suit.toString(), images[i]);
            i++;
        }
        for (int value = 2; value <= 9; value++) {
            for (Suit suit : Suit.values()) {
                cardFaceImages.put(value + "_of_" + suit.toString(), images[i]);
                i++;
            }
        }
        //Aces
        for (Suit suit : Suit.values()) {
            cardFaceImages.put("A_of_" + suit.toString(), images[i]);
            i++;
        }
        //Backside-image
        cardBackImage = images[i];
        i++;
        //Jacks, Kings and Queens
        String[] faceCards = {"J","K","Q"};
        for (String name: faceCards) {
            for (Suit suit : Suit.values()) {
                cardFaceImages.put(name + "_of_" + suit.toString(), images[i]);
                i++;
            } 
        }
    }
}
