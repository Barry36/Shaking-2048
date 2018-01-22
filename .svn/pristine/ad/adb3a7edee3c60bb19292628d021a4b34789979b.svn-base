package lab4_204_03.uwaterloo.ca.lab4_204_03;

/**
 * Created by Josip on 2017-07-06.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class GameLoopTask extends TimerTask {
    Activity myActivity;
    Context myContext;
    RelativeLayout MyRL;
    RelativeLayout tvLayout;
    //initializing the enumeration for the gamedirections
    public enum gameDirections{UP, DOWN, LEFT, RIGHT, NO_MOVEMENT};
    String debug[] = new String[4];

    int[] coordinateMap = new int[]{-80, 278, 636, 995};

    private final int gridLength = 4;
    //1st dimension denotes the rows (top to bottom), 2nd dimension denotes each element of the row (left to right)
    private GameBlock[][] grid = new GameBlock[gridLength][gridLength];
    //Blocks to be deleted after the end of current move phase
    private ArrayList<GameBlock> deletedBlocks = new ArrayList<GameBlock>();

    //While blocks are still moving don't allow any gestures to come thru
    private boolean gestureLock = false;
    //Whether a block has been created for this gesture. 1st one is created manually
    private boolean blockCreated = true;

    //Constructor for the gameloop task
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public GameLoopTask(Activity myActivity, RelativeLayout MyRL, RelativeLayout textviewLayout, Context myContext) {
        this.myActivity = myActivity;
        this.MyRL = MyRL;
        this.tvLayout = textviewLayout;
        this.myContext = myContext;
        createBlock();
    }
    //Method for creating the new Gameblock
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void createBlock(){
        ArrayList<int[]> emptySpots = new ArrayList<int[]>();
        for (int y=0; y<4; y++){
            for (int x=0; x<4; x++){
                if (grid[y][x] == null) emptySpots.add(new int[]{y,x});
            }
        }
        Random rand = new Random();
        int random = rand.nextInt(emptySpots.size());
        int pair[] = emptySpots.get(random);
        int y = pair[0], x = pair[1];
        GameBlock newBlock = new GameBlock(myContext, coordinateMap[x], coordinateMap[y], tvLayout);
        MyRL.addView(newBlock);
        grid[y][x] = newBlock;

    }

    //Looks for 256 block
    public boolean winCondition(){
        for (int i=0; i<gridLength; i++){
            for (int j=0; j<gridLength; j++){
                if (grid[i][j] != null && grid[i][j].number == 256) return true;
            }
        }
        return false;
    }

    public boolean loseCondition(){
        //Check for empty slots. If so no lose, otherwise assume full board
        for (int i=0; i<gridLength; i++){
            for (int j=0; j<gridLength; j++){
                if (grid[i][j] == null) return false;
            }
        }
        //Checks all horizontal neighbours for matching numbers
        for (int i=0; i<gridLength; i++){
            for (int j=0; j<gridLength-1; j++){
                if (grid[i][j].number == grid[i][j+1].number){
                    return false;
                }
            }
        }
        //Checks all vertical neighbours for matching numbers
        for (int i=0; i<gridLength-1; i++){
            for (int j=0; j<gridLength; j++){
                if (grid[i][j].number == grid[i+1][j].number){
                    return false;
                }
            }
        }
        //If board is full and no adjacent pieces exist, game over
        return true;
    }

    //Timer runnable that moves all blocks each frame and determines whether all blocks have stopped or not
    //If so the gesture lock is released
    public void run() {
        myActivity.runOnUiThread(
                new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void run() {
                        boolean inMotion = false;
                        //Move phase
                        for (int i=0; i<gridLength; i++){
                            for (int j=0; j<gridLength; j++){
                                GameBlock block = grid[i][j];
                                if (block != null && block.velocity != 0){ //Might need different algorithm for float equality
                                    inMotion = true;
                                    block.move();
                                    //The 1st thing that happens after blocks stop moving is creation of new block
                                    blockCreated = false;
                                }
                            }
                        }
                        for (GameBlock block: deletedBlocks){
                            if (block != null && block.velocity != 0){ //Might need different algorithm for float equality
                                inMotion = true;
                                block.move();
                                //The 1st thing that happens after blocks stop moving is creation of new block
                                blockCreated = false;
                            }
                        }
                        //Block creation and deletion phase
                        if (!inMotion && !blockCreated){
                            if (winCondition()) {
                                Log.wtf("Win", "You win!");
                                //UI here
                            }
                            createBlock();
                            if (loseCondition()){
                                Log.wtf("Lose", "You lose!");
                                //UI here
                            }
                            for (GameBlock toDelete: deletedBlocks) toDelete.delete(MyRL, tvLayout);
                            deletedBlocks.clear();
                            for(int t = 0; t < 4; t++) {
                                for (int z = 0; z < 4; z++) {
                                    if(grid[t][z] == null){
                                        debug[z] = "0";
                                    }
                                    else{
                                        debug[z] = String.valueOf(grid[t][z].number);
                                    }
                                }
                                Log.wtf("App", debug[0] + " , " + debug[1] + " , " + debug[2] + " , " + debug[3]);
                            }
                            Log.wtf("App", "------------------------------------------");
                            //No more blocks until end of next gesture
                            blockCreated = true;
                        }
                        gestureLock = inMotion;
                    }
                }
        );
    }

    //Reacts to gestures by sending the direction to the gameblock
    public void setDirection(gameDirections newDirection){
        if (!gestureLock){
            moveBlocks(newDirection);
        }
    }

    private boolean moveBlocksHori(gameDirections direction){
        int dir = 1;
        int start = 0;
        int end = gridLength;
        if (direction == gameDirections.RIGHT){
            dir = -1;
            start = gridLength-1;
            end = -1;
        }

        boolean hasMoved = false;
        for (int i=0; i<gridLength; i++){
            int borderPos = start;
            for (int j=start; j!= end; j+=dir){
                GameBlock block = grid[i][j];
                if (block != null){
                    grid[i][j] = null;
                    grid[i][borderPos] = block;
                    if (borderPos != j){
                        hasMoved = true;
                    }
                    borderPos += dir;
                }
            }
            //Go thru every block in row/column except last one
            for (int j=start; j!= end-dir; j+=dir) {
                GameBlock block = grid[i][j];
                //Move each block on UI
                if (block != null && coordinateMap[j] != block.Xpos) block.setDestination(coordinateMap[j], direction, false);
                GameBlock nextBlock = grid[i][j+dir];
                if (nextBlock == null) break;
                //Merged
                if (block.number == nextBlock.number){
                    hasMoved = true;
                    block.setDestination(coordinateMap[j], direction, true);
                    nextBlock.setDestination(coordinateMap[j], direction, false);
                    grid[i][j+dir] = null;
                    deletedBlocks.add(nextBlock);
                    //shift all subsequent blocks backwards by 1
                    for (int k=j+dir*2; k != end; k+=dir){
                            grid[i][k - dir] = grid[i][k];
                            grid[i][k] = null;
                    }
                }
            }
        }
        return hasMoved;
    }
    private boolean moveBlocksVert(gameDirections direction){
        int dir = 1;
        int start = 0;
        int end = gridLength;
        if (direction == gameDirections.DOWN){
            dir = -1;
            start = gridLength-1;
            end = -1;
        }

        boolean hasMoved = false;
        for (int i=0; i<gridLength; i++){
            int borderPos = start;
            for (int j=start; j!= end; j+=dir){
                GameBlock block = grid[j][i];
                if (block != null){
                    grid[j][i] = null;
                    grid[borderPos][i] = block;
                    if (borderPos != j){
                        hasMoved = true;
                    }
                    borderPos += dir;
                }
            }

            //Go thru every block in row/column except last one
            for (int j=start; j!= end-dir; j+=dir) {
                GameBlock block = grid[j][i];
                //Move each block on UI
                if (block != null && coordinateMap[j] != block.Ypos) block.setDestination(coordinateMap[j], direction, false);
                GameBlock nextBlock = grid[j+dir][i];
                if (nextBlock == null) break;
                //merge
                if (block.number == nextBlock.number){
                    hasMoved = true;
                    block.setDestination(coordinateMap[j], direction, true);
                    nextBlock.setDestination(coordinateMap[j], direction, false);
                    grid[j+dir][i] = null;
                    deletedBlocks.add(nextBlock);
                    //shift all subsequent blocks backwards by 1
                    for (int k=j+dir*2; k != end; k+=dir){
                            grid[k - dir][i] = grid[k][i];
                            grid[k][i] = null;
                    }
                }
            }
        }
        return hasMoved;
    }

    public boolean moveBlocks(gameDirections direction){
        if (direction == gameDirections.DOWN || direction == gameDirections.UP){
            return moveBlocksVert(direction);
        }
        else{
            return moveBlocksHori(direction);
        }

    }
}
