package lab4_204_03.uwaterloo.ca.lab4_204_03;

/**
 * Created by Josip on 2017-07-06.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import static android.graphics.Color.BLACK;
import static lab4_204_03.uwaterloo.ca.lab4_204_03.GameLoopTask.gameDirections.DOWN;
import static lab4_204_03.uwaterloo.ca.lab4_204_03.GameLoopTask.gameDirections.LEFT;
import static lab4_204_03.uwaterloo.ca.lab4_204_03.GameLoopTask.gameDirections.RIGHT;
import static lab4_204_03.uwaterloo.ca.lab4_204_03.GameLoopTask.gameDirections.UP;

//Creating class GameBlock that extends an imageview class
public class GameBlock extends GameBlockTemplate{
    private float IMAGE_SCALE = 0.6f;
    public int Xpos = -80;
    public int Ypos =-80;
    public int textx;
    public int texty;
    public int destination;
    public int velocity = 0;
    public int acceleration = 10;
    public GameLoopTask.gameDirections newDir;
    private int offSetx = 215;
    private int offSety = 63;
    private TextView tv;

    public int number;
    public int numOfMerges;
    private boolean mergeAfterMove = false;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public GameBlock(Context context, int coordX, int coordY, RelativeLayout textviewLayout) {//Constructor for the GameBlock
        super(context);
        Xpos = coordX;
        Ypos = coordY;
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        this.setX(coordX);
        this.setY(coordY);
        createtv(textviewLayout, context, coordX, coordY);
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void createtv(RelativeLayout textviewLayout, Context context, int x, int y){
        number = randGenerator();

        tv = new TextView(context);

        textx = x + offSetx;
        texty = y + offSety;
        tv.setX(textx);
        tv.setY(texty);
        tv.setText(Integer.toString(number));
        tv.setTextSize(70);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(BLACK);
        textviewLayout.addView(tv);
        tv.bringToFront();
    }

    //TODO: change so that block stops at specific destination
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void move() {//Move function for changing the blocks coordinates according to the direction

        if (newDir == UP) {//Checking for up gesture
            slideUp(destination);

        }
        //Repeat the first block
        else if (newDir == DOWN) {
            slideDown(destination);

        }
        //Repeat the first block
        else if (newDir == RIGHT) {
            slideRight(destination);

        }
        //Repeat the first movement block
        else if (newDir == LEFT) {
            slideLeft(destination);

        }

    }

    //Double the block number
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void merge(){
        number *= 2;
        numOfMerges += 1;
        setText(number);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void setText(int num){
        if (number > 10 && number < 20){
            tv.setTextSize(50);
            textx -= 40;
            texty += 60;
            tv.setX(textx);
            tv.setY(texty);
        }
        else if(number > 100 && number < 150){
            tv.setTextSize(30);
            textx += 10;
            texty += 40;
            tv.setX(textx);
            tv.setY(texty);
        }
        tv.setText(Integer.toString(number));
    }

    public void setDestination (int dest, GameLoopTask.gameDirections direction, boolean toMerge) {
        newDir = direction;
        destination = dest;
        velocity = 1;
        mergeAfterMove = toMerge;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void slideLeft(int endDestination){
        if ((Xpos - velocity) > endDestination) {
            Xpos -= (velocity);
            textx -= (velocity);
            this.setX(Xpos);
            tv.setX(textx);
            velocity += acceleration;
        }
        else if((Xpos - velocity) <= endDestination){
            Xpos = endDestination;
            textx= endDestination + offSetx;
            this.setX(Xpos);
            tv.setX(textx);
            if (mergeAfterMove) merge();
            velocity = 0;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void slideRight(int endDestination){
        if ((Xpos + velocity) < endDestination) {
            Xpos += (velocity);
            textx += (velocity);
            this.setX(Xpos);
            tv.setX(textx);
            velocity += acceleration;
            this.setX(Xpos);
        }
        else if((Xpos + velocity) >= endDestination){
            Xpos = endDestination;
            textx= endDestination + offSetx;
            this.setX(Xpos);
            tv.setX(textx);
            if (mergeAfterMove) merge();
            velocity = 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void slideUp(int endDestination){
        if ((Ypos - velocity) >  endDestination) {//Repeat the movement if the block is before the boundary
            Ypos -= (velocity);//Adding the velocity to the y position
            texty -= (velocity);
            this.setY(Ypos);//Setting the y position of the block
            tv.setY(texty);
            velocity += acceleration;//Adding the acceleration to the velocity
        }
        else if((Ypos - velocity) <= endDestination){//Checking for if the block will leave the boundary
            Ypos = endDestination;//Setting the y position to the boundary
            texty= endDestination + offSety;
            this.setY(Ypos);
            tv.setY(texty);
            if (mergeAfterMove) merge();
            velocity = 0;//setting velocity to zero since the movement is complete
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void slideDown(int endDestination){
        if ((Ypos + velocity) < endDestination) {
            Ypos += (velocity);
            texty += (velocity);
            this.setY(Ypos);
            tv.setY(texty);
            velocity += acceleration;
        }
        else if((Ypos + velocity) >= endDestination){
            Ypos = endDestination;
            texty= endDestination + offSety;
            this.setY(Ypos);
            tv.setY(texty);
            if (mergeAfterMove) merge();
            velocity = 0;
        }
    }
    private int randGenerator(){
        int select[] = {2, 4};

        Random rand = new Random();
        int  random = rand.nextInt(2) + 1;
        return select[random-1];
    }

    public void delete(RelativeLayout blklayout, RelativeLayout tvlayout){
        tvlayout.removeView(this.tv);
        blklayout.removeView(this);
    }
}
