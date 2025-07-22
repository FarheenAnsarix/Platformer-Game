package utilz;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

//to load and save any images received
//no constructors as we will have only static methods in this class
public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static BufferedImage GetSpriteAtlas(String fileName)
    {
        BufferedImage img=null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);//importing the desired image
        //try and catch will take img into the res folder and load it into the buffered img
        try {
            img = ImageIO.read(is);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try
            {
                is.close();//free up resources and avoid problems
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return img;
    }
    /*it will returm an int to the array, size of this
    array will match the size of the game window in terms
    of tiles per width and height
     */
    public static int[][] getLavelData()
    {
        int[][] lvlDeta = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA); //level image where the pixel represent the tile

        for(int j=0;j<img.getHeight();j++)
        {
            for(int i=0;i<img.getWidth();i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value>=48)//we have an index of 48
                {
                    value=0;
                }
                lvlDeta[j][i] = value;
                /*whatever value red is in that second will be the index
                later on for that sprite array when its drawn*/
            }
        }
        return lvlDeta;
    }
}
