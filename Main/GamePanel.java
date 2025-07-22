package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import Input.KeyboardInputs;
import Input.MouseInputs;
import static Main.Game.GAME_HEIGHT;
import static Main.Game.GAME_WIDTH;
public class GamePanel extends JPanel
{
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game)
        {
            mouseInputs = new MouseInputs(this);
            this.game=game;
            setPanelSize();
            addKeyListener(new KeyboardInputs(this));
            addMouseListener(mouseInputs);
            addMouseMotionListener(mouseInputs);
        }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size : "+GAME_WIDTH+" , "+GAME_HEIGHT);
    }


    public void updateGame()
    {

    }
    public void paintComponent(Graphics g)
    {
        //helps the rectangle box to change it's position
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
