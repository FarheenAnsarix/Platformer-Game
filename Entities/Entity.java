package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//common class used by both enemy and player
public abstract class Entity {
    protected float x,y;//can also be used by classes that extend this class
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    /*rectangle stores x,y,width,height it also checks
    if a certain position is inside this rectangle or
    not w/o coding long lines of code*/
     public Entity(float x, float y,int width,int height)
     {
         this.x=x;
         this.y=y;
         this.width=width;
         this.height=height;

     }

    protected void drawHitbox(Graphics g) {
        //for debugging the hitbox
        g.setColor(Color.PINK);//good color to be used
        //want to draw a rectangle
        g.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }
    private void initHitbox() {
        hitbox=new Rectangle2D.Float((int)x,(int)y,width,height);
    }

    //only the class that extends entity to be able to access/update this method
    protected void updateHitbox()
    {
        hitbox.x=(int)x;
        hitbox.y=(int)y;//width and height always stays the same that's why we don't have to set it

    }
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
