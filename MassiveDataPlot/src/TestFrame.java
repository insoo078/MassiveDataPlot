import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TestFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6687192929481066964L;
	private TestPanel originalDataPanel;
	private TestPanel collpaseDataPanel;
	
	private JLabel originalDataResult;
	private JLabel collapsedDataResult;
	private JLabel lblN;
	private JTextField txtSampleSize;
	
	private TestFrame remote;
	private JLabel lblRatio;

	public TestFrame(String title) {
		super(title);
		
		this.remote = this;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize( 1024, 768 );
		
		this.originalDataPanel = new TestPanel(1, this);
		this.originalDataPanel.setBackground(Color.WHITE);
		
		this.collpaseDataPanel = new TestPanel(2, this);
		this.collpaseDataPanel.setBackground(Color.WHITE);
		
		JLabel lblOriginalData = new JLabel("Original Data");
		JLabel lblCollapsedData = new JLabel("Shrinked Data");
		
		this.originalDataResult = new JLabel("Result : ");
		this.collapsedDataResult = new JLabel("Result : ");
		
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int n = Integer.valueOf( txtSampleSize.getText() );
					
					lblRatio.setText("");
					
					// TODO Auto-generated method stub
					int PANEL_WIDTH = originalDataPanel.getWidth();
					int PANEL_HEIGHT = originalDataPanel.getHeight();
					int axisLen = 300;

					List<Point> data = DataGenerator.makeData( n );
					List<Point> plotData = DataGenerator.compactDataToPlot(data, PANEL_WIDTH, PANEL_HEIGHT, axisLen);

					System.out.println( "Original data : " + data.size() );
					System.out.println( "Compacted data : " + plotData.size() );
					System.out.println( ((1 - ((float)plotData.size() / data.size())) * 100) + "% points are collapsed");
					
					lblRatio.setText( ((1 - ((float)plotData.size() / data.size())) * 100) + "% points are shrinked" );

					Thread t1 = new Thread(new Runnable() {
						@Override
						public void run() {
							originalDataResult.setText("Result : ");
							// TODO Auto-generated method stub
							originalDataPanel.attachData( data );							
						}
					});
					
					Thread t2 = new Thread(new Runnable() {
						@Override
						public void run() {
							collapsedDataResult.setText("Result : ");
							// TODO Auto-generated method stub
							collpaseDataPanel.attachData( plotData );							
						}
					});
					
					t1.start();
					t2.start();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog( remote,
					        "Sample size is only number type",
					        "Sample size error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblN = new JLabel("n");
		
		txtSampleSize = new JTextField();
		txtSampleSize.setColumns(10);
		txtSampleSize.setText( "1000000" );
		
		lblRatio = new JLabel("");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOriginalData)
								.addComponent(originalDataResult)
								.addComponent(originalDataPanel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCollapsedData)
								.addComponent(collapsedDataResult)
								.addComponent(collpaseDataPanel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(174)
							.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(128)
							.addComponent(lblN)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtSampleSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addComponent(lblRatio)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOriginalData)
						.addComponent(lblCollapsedData))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(collpaseDataPanel, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
						.addComponent(originalDataPanel, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(originalDataResult)
						.addComponent(collapsedDataResult))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblN)
						.addComponent(txtSampleSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRatio))
					.addGap(25)
					.addComponent(btnTest)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		this.setVisible(true);
	}

	public JLabel getOriginalDataResult() {
		return originalDataResult;
	}

	public void setOriginalDataResult(JLabel originalDataResult) {
		this.originalDataResult = originalDataResult;
	}

	public JLabel getCollapsedDataResult() {
		return collapsedDataResult;
	}

	public void setCollapsedDataResult(JLabel collapsedDataResult) {
		this.collapsedDataResult = collapsedDataResult;
	}
}
