package lab4_204_03.uwaterloo.ca.lab4_204_03;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.widget.TextView;

import java.util.LinkedList;


public class AccelerometerListener implements SensorEventListener {
    private LinkedList<Triplet> _filteredQ;
    private TextView _dirView;
    private final float c = 10;
    private GestureFsm fsmx = new GestureFsm(), fsmy = new GestureFsm();
    private GameLoopTask _myGL;

    public AccelerometerListener(LinkedList<Triplet> filtered, TextView dirView, GameLoopTask myGL){

        _filteredQ = filtered;
        _filteredQ.add(new Triplet(0,0,0));
        _dirView = dirView;
        _myGL = myGL;
    }

    public void onAccuracyChanged(Sensor s, int i){}

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onSensorChanged(SensorEvent se){
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            Triplet raw = new Triplet(se.values[0], se.values[1], se.values[2]);
            Triplet filtered = filter(raw);

            //Recognize gestures for x and y
            int yGesture = fsmy.runFsm(filtered.y);
            int xGesture = fsmx.runFsm(filtered.x);
            if (yGesture == 1){
                _dirView.setText("Up");
                _myGL.setDirection(GameLoopTask.gameDirections.UP);
            }
            else if (yGesture == -1){
                _dirView.setText("Down");
                _myGL.setDirection(GameLoopTask.gameDirections.DOWN);
            }
            else if (xGesture == 1){
                _dirView.setText("Right");
                _myGL.setDirection(GameLoopTask.gameDirections.RIGHT);
            }
            else if (xGesture == -1){
                _dirView.setText("Left");
                _myGL.setDirection(GameLoopTask.gameDirections.LEFT);
            }

            float[] arr = {filtered.x, filtered.y, filtered.z};
            _filteredQ.add(filtered);
            if (_filteredQ.size() > 100){
                _filteredQ.remove();
            }
        }
    }

    private Triplet filter(Triplet raw){
        Triplet prev = _filteredQ.peekLast();
        return new Triplet(prev.x+(raw.x-prev.x)/c, prev.y+(raw.y-prev.y)/c, prev.z+(raw.z-prev.z)/c);
    }
}
