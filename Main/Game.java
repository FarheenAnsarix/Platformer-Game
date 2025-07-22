package Main;

import Entities.Player;
import Levels.LevelManager;

import java.awt.*;

public class Game implements Runnable
{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE=32; //default size of tiles of window(square 32/32)
    public final static float SCALE=2f; //how much should it scale everything(eg. player,enemies,the level etc
    public final static int TILES_IN_WIDTH = 26 ;//determines how many tiles in what width are needed
    public final static int TILES_IN_HEIGHT = 14 ;//determines how many tiles in what height are needed
    public final static int TILES_SIZE=(int)(TILES_DEFAULT_SIZE*SCALE);//actual size of the tile(s) in the window
    public final static int GAME_WIDTH = TILES_SIZE*TILES_IN_WIDTH;//Calculates width of the tile
    public final static int GAME_HEIGHT = TILES_SIZE*TILES_IN_HEIGHT;//Calculates height of the tile

    public Game()
    {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(200,200,(int) (64 * SCALE), (int) (40 * SCALE));
        player.loadLvlDeta(levelManager.getCurrentLevel().getLvlData());
    }

    private void startGameLoop()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update()
    {
        levelManager.update();
        player.update();
    }
    public void render(Graphics g)
    {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {
        //GameThread to separately run the thread
        double timePerFrame = 1000000000/FPS_SET; //duration of how long will each frame lasts
        double timePerUpdate = 1000000000/UPS_SET; //duration in between them(frequency)

        long previousTime = System.nanoTime();

        int frames=0;
        int updates=0;
        long lastCheck = System.currentTimeMillis();
        //Game-Loop
        double deltaU = 0;//never gets reset
        double deltaF = 0;
        while(true)
        {
            //to keep a difference b/w the render and updates we named it current time
            long currentTime = System.nanoTime();
            //to check the time to update is if deltaU is more or equal to 1
            //to get deltaU incresed we minus previous time from current time and divide it by timePerUpdate
            deltaU += (currentTime-previousTime)/timePerUpdate;//deltaU will be 1.0 more when the duration since last update is equal or more than timePerUpdate
            deltaF+=(currentTime-previousTime)/timePerFrame;
            previousTime=currentTime;
            if(deltaU>=1)
            {
                update();
                updates++;
                deltaU--;//removes 1(descreses with every iteration to indicate the time for an update of frame)
            }

            if(deltaF>=1)
            {
                gamePanel.repaint();
                frames++;//counts the frames change per second(fpm)
                deltaF--;
            }
            /*The if-clause below checks how many fpm were in last second(time in milli-second).
             * if one second have passed since the last fps check, we do a new fps check,
             * Save the newFps check as the lastFps check and repeat.
             * The frame stays axactly where we have left it*/
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS : "+frames+" | UPS : "+updates);
                frames=0; //reset frame to avoid miscalculation(if we do not it will thw total sum of it.)
                updates=0;
            }

        }
    }
    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer() {
        return player;
    }
}
