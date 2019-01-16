import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class TestPanel extends JPanel {
	private List<Point> data;
	
	private int type;
	private TestFrame frame;
	private Map<String, Color> groupColorMap;
	private Boundary bound;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9132982378357189445L;
	
	public TestPanel( int type, TestFrame frame ) {
		this.data = null;
		this.frame = frame;
		this.type = type;
		
		this.groupColorMap = new LinkedHashMap<String, Color>();
		this.groupColorMap.put("Group1", Color.magenta);
		this.groupColorMap.put("Group2", Color.blue);
		this.groupColorMap.put("Group3", Color.cyan);
		this.groupColorMap.put("Group4", Color.ORANGE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		
		if( this.data != null && this.bound != null ) {
			long a = System.currentTimeMillis();
			int width = this.getWidth();
			int height = this.getHeight();

			for( Point point : this.data ) {
				int relativeX = (int) Math.floor( ((point.getX() - this.bound.getMinX()) / (this.bound.getMaxX() - this.bound.getMinX())) * width );
				int relativeY = (int) Math.floor( ((point.getY() - this.bound.getMinY()) / (this.bound.getMaxY() - this.bound.getMinY())) * height );
				
				g2.setColor( this.groupColorMap.get( point.getGroupName()) );
				g2.fillOval( relativeX, relativeY, 2, 2);
			}
			long b = System.currentTimeMillis();
			
			String result = ((float)(b-a) / 1000) + "sec";

			g2.setColor( Color.black );
			g2.drawString( "Result : " + result + "(n : "+NumberFormat.getInstance().format(this.data.size())+")" , 10, height - 20 );
		}
	}
	
	public void attachData( List<Point> data ) {
		this.data = data;
		this.bound = DataGenerator.getBoundary( this.data );
		
		this.repaint();
	}
}
