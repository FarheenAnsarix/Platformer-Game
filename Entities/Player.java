package Entities;

import static utilz.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import Main.Game;

public class Player extends Entity{
    private BufferedImage[][] animations ;
    private int aniTick,aniIndex,aniSpeed=25;//120FPS and 4 animations per second
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, down, right;
    private float playerSpeed=2.0f;
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    public Player(float x, float y,int width,int height) {
        super(x, y,width,height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update()
    {
        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();//determines what type of animation we have for this frame
    }
    public void render(Graphics g)
    {
        g.drawImage( animations[playerAction][aniIndex], (int)x, (int)y ,256, 160, null);
        //hitbox to be drawn on top of the player
        drawHitbox(g);
    }



    private void updateAnimationTick() {
        aniTick++;
        if(aniTick>=aniSpeed)
        {
            aniTick = 0;
            aniIndex++;
            if(aniIndex>= GetSpriteAmount(playerAction))//more than or equal to the size of animation array
            // depending on the player action we get the diff amount of index action in that animation
            {
                aniIndex=0;//if ecxceeds the limit it will go back to zero
                attacking=false;//end the attacking animations here
            }
        }
    }

    private void setAnimation() {

        int startAni = playerAction;

        if(moving)
        {
            playerAction=RUNNING;
        }
        else {
            playerAction=IDLE;
        }
        if(attacking)
        {
            playerAction = ATTACK_1;
        }
        if(startAni != playerAction)
        {
            resetAniTick();//to get a new and full duration sprite when we change it's position i.e.attacking position
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }

    private void updatePos() {
        moving=false;//to reset moving
        //helps in diagonal moving aswell as we are not cancelling the up+right, down+left etc moving
        if(left&&!right)//meaning when we press left it prevents us from moving right
        {
            x-=playerSpeed;
            moving=true;
        }
        else if(right&&!left) //and vise versa
        {
            x+=playerSpeed;
            moving=true;
        }

        if(up&&!down)//meaning when we press up it prevents us from moving right
        {
            y-=playerSpeed;
            moving=true;
        }
        else if(down&&!up) //and vise versa
        {
            y+=playerSpeed;
            moving=true;
        }
    }



    private void loadAnimations() {
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[9][6];//vertically and horizontal characters in the player img

            for(int j=0;j<animations.length;j++)
            {
                for(int i=0;i<animations[j].length;i++)
                {
                    animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
                }
            }
    }

    public void loadLvlDeta(int[][] lvlDeta)
    {
        this.lvlData=lvlDeta;
    }
    public void resetDirBooleans()
    {
        left=false;
        right=false;
        up=false;
        down=false;
    }

    public void setAttacking(boolean attacking) {
        //start the attacking animations here
        this.attacking = attacking;
    }


    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
