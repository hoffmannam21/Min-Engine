package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class KeyInput extends KeyAdapter implements MouseListener, MouseWheelListener {
	public double mouseX = -1;
	public double mouseY = -1;

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_E){
			handler.camera.forward = true;
		}
		if(key == KeyEvent.VK_D){
			handler.camera.backward = true;
		}
		if(key == KeyEvent.VK_F){
			handler.camera.right = true;
		}
		if(key == KeyEvent.VK_S){
			handler.camera.left = true;
		}
		if(key == KeyEvent.VK_SPACE){
			handler.camera.up = true;
		}
		if(key == KeyEvent.VK_CONTROL){
			handler.camera.down = true;
		}
		if(key == KeyEvent.VK_SHIFT){
			handler.camera.shift = true;
		}
		if(key == KeyEvent.VK_A){
			handler.camera.rotateXZNeg = true;
		}
		if(key == KeyEvent.VK_G){
			handler.camera.rotateXZPos = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_E) handler.camera.forward = false;
		if(key == KeyEvent.VK_D) handler.camera.backward = false;
		if(key == KeyEvent.VK_F) handler.camera.right = false;
		if(key == KeyEvent.VK_S) handler.camera.left = false;
		if(key == KeyEvent.VK_SPACE) handler.camera.up = false;
		if(key == KeyEvent.VK_CONTROL) handler.camera.down = false;
		if(key == KeyEvent.VK_SHIFT) handler.camera.shift = false;
		if(key == KeyEvent.VK_G) handler.camera.rotateXZPos = false;
		if(key == KeyEvent.VK_A) handler.camera.rotateXZNeg = false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			handler.camera.rightClick = true;
			System.out.println("Right");
		}
		if(SwingUtilities.isLeftMouseButton(arg0)){
			if(handler.camera.forward == true ||
					handler.camera.backward == true ||
					handler.camera.down == true ||
					handler.camera.left == true ||
					handler.camera.up == true ||
					handler.camera.right == true){
				handler.camera.speedUp = true;
				handler.camera.speedUp();
			}
		}
		if(SwingUtilities.isMiddleMouseButton(arg0)){
			handler.camera.slowDown = true;
			handler.camera.slowDown();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			handler.camera.rightClick = false;
			mouseX = -1;
			mouseY = -1;
		}
		if(SwingUtilities.isLeftMouseButton(arg0)){
			handler.camera.speedUp = false;
		}
		if(SwingUtilities.isMiddleMouseButton(arg0)){
			handler.camera.slowDown = false;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

		if(e.getWheelRotation() < 0){
			handler.camera.mouseWheelForward = true;
			handler.camera.mouseWheelForwardClicks++;
		}else{
			handler.camera.mouseWheelBackward = true;
			handler.camera.mouseWheelBackwardClicks++;
		}

	}

}
