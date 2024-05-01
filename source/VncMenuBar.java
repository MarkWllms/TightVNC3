import java.awt.*;
import java.awt.event.*;
import java.io.*;



public class VncMenuBar extends MenuBar  {

private VncViewer viewer;
    

    public VncMenuBar(VncViewer v) {
     viewer = v;

     Menu file = new Menu("File");
     this.add(file);

     MenuItem quit;

     file.add(quit = new MenuItem("Quit"));
     quit.addActionListener(new ActionListener () {
	     public void actionPerformed(ActionEvent e) {System.exit(0);}
	 });


     Menu tools = new Menu("Tools");
     this.add(tools);
     MenuItem options;
     tools.add(options = new MenuItem("Options"));
     options.addActionListener(new ActionListener () {
	     public void actionPerformed(ActionEvent e) {
		 viewer.options.setVisible(!viewer.options.isVisible());
	     }
	 });

     
     MenuItem clipboard;
     tools.add(clipboard = new MenuItem("Clipboard"));
     clipboard.addActionListener(new ActionListener () {
	     public void actionPerformed(ActionEvent e) {
		 viewer.clipboard.setVisible(!viewer.clipboard.isVisible());
	     }
	 });

     

     MenuItem ctrlAltDel;
     tools.add(ctrlAltDel = new MenuItem("Send Ctrl-Alt-Del"));
     ctrlAltDel.addActionListener(new ActionListener () {
	     public void actionPerformed(ActionEvent e) {
		 try {
		     final int modifiers = InputEvent.CTRL_MASK | InputEvent.ALT_MASK;

		     KeyEvent ctrlAltDelEvent =
			 new KeyEvent(viewer, KeyEvent.KEY_PRESSED, 0, modifiers, 127);
		     viewer.rfb.writeKeyEvent(ctrlAltDelEvent);

		     ctrlAltDelEvent =
			 new KeyEvent(viewer, KeyEvent.KEY_RELEASED, 0, modifiers, 127);
		     viewer.rfb.writeKeyEvent(ctrlAltDelEvent);

		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
     
	     }
	 });

     MenuItem refresh;
     tools.add(refresh = new MenuItem("Refresh screen"));
     refresh.addActionListener(new ActionListener () {
	     public void actionPerformed(ActionEvent e) {
		 try {
		     RfbProto rfb = viewer.rfb;
		     rfb.writeFramebufferUpdateRequest(0, 0, rfb.framebufferWidth,
						       rfb.framebufferHeight, false);
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
	   
	     }
	 });




     
    }
}
