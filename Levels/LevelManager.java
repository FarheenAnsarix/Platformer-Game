package Levels;

import Main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    public LevelManager(Game game)
    {
        //Background image of that level
        this.game=game;
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levelOne=new Level(LoadSave.getLavelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];//as we set width=12 and height=4 so 12*4=48
        for(int j=0;j<4;j++)
        {
            for(int i=0;i<12;i++)
            {
                int index = j*12 + i;
                levelSprite[index]= img.getSubimage(i*32,j*32,32,32);
            }
        }
    }

    public void draw(Graphics g)
    {
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
        {
            for(int i=0;i<Game.TILES_IN_WIDTH;i++)
            {
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],Game.TILES_SIZE*i,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE,null);
            }
        }
    }
    public void update()
    {

    }
    public Level getCurrentLevel()
    {
        //returns all the levels in the code
        return levelOne;
    }
}
