package Levels;
//this class will store the data of the level that user is playing
public class Level {
    private int[][] lvlData;
    public Level(int[][] lvlData)
    {
        this.lvlData=lvlData;
    }
    public int getSpriteIndex(int x, int y)
    {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }
}
