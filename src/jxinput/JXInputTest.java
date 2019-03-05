package jxinput;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.JXInputAxisEventListener;
import de.hardcode.jxinput.test.AxisListener;
import de.hardcode.jxinput.test.JXInputDevicePanel;

import javax.swing.JFrame;

public class JXInputTest {
	
	//static {
		//System.loadLibrary("JXInput");
	//}
	
	public static void main(String args[]) {
		DirectInputDevice controller = new DirectInputDevice(0);
		
		System.out.println(controller.toString() + "\n" +
						   controller.getName()
				);         // + "\n" +
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JXInputDevicePanel panel = new JXInputDevicePanel(controller);
		frame.add(panel);
		frame.setSize(new Dimension(800,600));
		//frame.pack();
		frame.setVisible(true);
		
		Axis[] axes = new Axis[] {controller.getAxis(Axis.ID_ROTX), controller.getAxis(Axis.ID_ROTY), controller.getAxis(Axis.ID_ROTZ), 
				//d.getAxis(Axis.ID_SLIDER0), d.getAxis(Axis.ID_SLIDER1), 
				controller.getAxis(Axis.ID_X), controller.getAxis(Axis.ID_Y), controller.getAxis(Axis.ID_Z)
		}; // d.getAxis(Axis.NUMBER_OF_ID),
		
		while(true) {
			DirectInputDevice.update();
			panel.update();
			
			for(int i=0; i<axes.length; i++) {
				if(axes[i] != null)
				System.out.println(axes[i].getName() + ": " + axes[i].getValue());
			}
			
			Button b;
			for(int i=0; i<15; i++) {
				b = controller.getButton(i); if(b != null) System.out.println(b.getName() + ": " + b.getState());
			}
			
			Directional dd;
			for(int i=0; i<4; i++) {
				dd = controller.getDirectional(i); if(dd != null) System.out.println(dd.getName() + ": " + dd.getDirection());
			}
			
			System.out.println("");
			
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		if(1 == 3/2)
		return;
		//System.exit(0);
		
		Axis[] axes = new Axis[] {d.getAxis(Axis.ID_ROTX), d.getAxis(Axis.ID_ROTY), d.getAxis(Axis.ID_ROTZ), 
				//d.getAxis(Axis.ID_SLIDER0), d.getAxis(Axis.ID_SLIDER1), 
				//d.getAxis(Axis.ID_X), d.getAxis(Axis.ID_Y), d.getAxis(Axis.ID_Z) }; 
				d.getAxis(Axis.ROTATION), d.getAxis(Axis.SLIDER), 
				d.getAxis(Axis.TRANSLATION)
		}; // d.getAxis(Axis.NUMBER_OF_ID),
		
		JXInputAxisEventListener[] jxListeners = new AxisListener[axes.length];
		for(int i=0; i<axes.length; i++) {
			jxListeners[i] = new AxisListener(axes[i]);
		}
		
		boolean hi = true;
		while(hi) {
			for(int i=0; i<axes.length; i++) {
				if(axes[i] != null) {
					System.out.println(i + ": " + axes[i].getName() + ": " + axes[i].getValue());
				}else {
					System.out.println(i + ": null axis");
				}
				
			}
			System.out.println();
		}
		
		while(1 == (3/2)) {
			for(int i=0; i<axes.length; i++) {
				System.out.println(jxListeners[i].toString());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
}
