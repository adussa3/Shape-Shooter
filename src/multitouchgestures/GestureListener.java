/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitouchgestures;

import multitouchgestures.event.MagnifyGestureEvent;
import multitouchgestures.event.RotateGestureEvent;
import multitouchgestures.event.ScrollGestureEvent;

/**
 *
 * @author martijn
 */
public interface GestureListener
{
    public void magnify(MagnifyGestureEvent e);
    
    public void rotate(RotateGestureEvent e);
    
    public void scroll(ScrollGestureEvent e);
}
