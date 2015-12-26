import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class ReceiptReader extends JFrame {

    public static void main(String[] args)  {
        // 設定look-and-feel為目前系統的外觀
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 執行GUI程式
        EventQueue.invokeLater(() -> {
            try {
                ReceiptReader frame = new ReceiptReader();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



	JPanel contentPane, panel_content, panel_receiptData, panel_date, panel_shopName, panel_total, panel_summary, panel_paidBy, panel_openFile, panel_VATNumber;
	JLabel label_total, label_shopName, label_date, label_paidBy, label_VATNumber;	
	JScrollPane scrollPane_receiptNumbers, scrollPane_details;	
	JTextField textField_date, textField_shopName, textField_total, textField_paidBy, textField_VATNumber;
	JList<String> list_receiptNumbers;
	JFileChooser fileChooser = new JFileChooser();
	DefaultTableModel tableModel_details;
	JTable table_details;
	JButton button_openFile;
	
	List<ReceiptData> dataList;

	public ReceiptReader() {
		setTitle("財政部電子發票檢視器");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_content = new JPanel();
		panel_content.setBounds(0, 0, 582, 295);
		contentPane.add(panel_content);
		panel_content.setLayout(null);
		
		scrollPane_receiptNumbers = new JScrollPane();
		scrollPane_receiptNumbers.setBounds(10, 30, 95, 255);
		panel_content.add(scrollPane_receiptNumbers);
		
		list_receiptNumbers = new JList<>();
		list_receiptNumbers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_receiptNumbers.setViewportView(list_receiptNumbers);
		
		panel_receiptData = new JPanel();
		panel_receiptData.setBounds(114, 5, 468, 280);
		panel_content.add(panel_receiptData);
		panel_receiptData.setLayout(null);
		
		panel_date = new JPanel();
		panel_date.setBounds(0, 0, 142, 33);
		panel_receiptData.add(panel_date);
		panel_date.setLayout(null);
		
		label_date = new JLabel("日期:");
		label_date.setBounds(10, 8, 31, 19);
		label_date.setHorizontalAlignment(SwingConstants.LEFT);
		panel_date.add(label_date);
		label_date.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		
		textField_date = new JTextField();
		textField_date.setBounds(52, 6, 85, 23);
		textField_date.setHorizontalAlignment(SwingConstants.LEFT);
		panel_date.add(textField_date);
		textField_date.setBackground(Color.WHITE);
		textField_date.setEditable(false);
		textField_date.setColumns(10);
		
		panel_shopName = new JPanel();
		panel_shopName.setBounds(0, 29, 468, 33);
		panel_receiptData.add(panel_shopName);
		
		label_shopName = new JLabel("店家名稱:");
		label_shopName.setBounds(10, 9, 59, 19);
		label_shopName.setHorizontalAlignment(SwingConstants.LEFT);
		label_shopName.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		
		textField_shopName = new JTextField();
		textField_shopName.setBounds(83, 6, 378, 22);
		textField_shopName.setHorizontalAlignment(SwingConstants.LEFT);
		textField_shopName.setEditable(false);
		textField_shopName.setColumns(10);
		textField_shopName.setBackground(Color.WHITE);
		panel_shopName.setLayout(null);
		panel_shopName.add(label_shopName);
		panel_shopName.add(textField_shopName);
		
		scrollPane_details = new JScrollPane();
		scrollPane_details.setBounds(0, 66, 460, 183);
		panel_receiptData.add(scrollPane_details);
		
		tableModel_details = new DefaultTableModel(
                new Object[][] { },
                new String[] {"金額", "明細"} ) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table_details = new JTable();
		table_details.setModel(tableModel_details);
		table_details.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_details.getColumnModel().getColumn(0).setPreferredWidth(66);
		table_details.getColumnModel().getColumn(1).setPreferredWidth(390);
		table_details.setRowHeight(20);
		table_details.setRowMargin(3);
		
		scrollPane_details.setViewportView(table_details);
		
		panel_summary = new JPanel();
		panel_summary.setBounds(0, 247, 468, 33);
		panel_receiptData.add(panel_summary);
		panel_summary.setLayout(null);
		
		panel_total = new JPanel();
		panel_total.setLayout(null);
		panel_total.setBounds(0, 0, 148, 33);
		panel_summary.add(panel_total);
		
		label_total = new JLabel("總金額:");
		label_total.setHorizontalAlignment(SwingConstants.LEFT);
		label_total.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		label_total.setBounds(9, 7, 51, 19);
		panel_total.add(label_total);
		
		textField_total = new JTextField();
		textField_total.setHorizontalAlignment(SwingConstants.LEFT);
		textField_total.setEditable(false);
		textField_total.setColumns(10);
		textField_total.setBackground(Color.WHITE);
		textField_total.setBounds(62, 7, 78, 23);
		panel_total.add(textField_total);
		
		panel_paidBy = new JPanel();
		panel_paidBy.setLayout(null);
		panel_paidBy.setBounds(152, 0, 316, 33);
		panel_summary.add(panel_paidBy);
		
		label_paidBy = new JLabel("付款方式:");
		label_paidBy.setHorizontalAlignment(SwingConstants.LEFT);
		label_paidBy.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		label_paidBy.setBounds(9, 7, 78, 19);
		panel_paidBy.add(label_paidBy);
		
		textField_paidBy = new JTextField();
		textField_paidBy.setHorizontalAlignment(SwingConstants.LEFT);
		textField_paidBy.setEditable(false);
		textField_paidBy.setColumns(10);
		textField_paidBy.setBackground(Color.WHITE);
		textField_paidBy.setBounds(75, 7, 235, 23);
		panel_paidBy.add(textField_paidBy);
		
		panel_VATNumber = new JPanel();
		panel_VATNumber.setLayout(null);
		panel_VATNumber.setBounds(152, 0, 316, 33);
		panel_receiptData.add(panel_VATNumber);
		
		label_VATNumber = new JLabel("店家統一編號: ");
		label_VATNumber.setHorizontalAlignment(SwingConstants.LEFT);
		label_VATNumber.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		label_VATNumber.setBounds(12, 6, 101, 19);
		panel_VATNumber.add(label_VATNumber);
		
		textField_VATNumber = new JTextField();
		textField_VATNumber.setHorizontalAlignment(SwingConstants.LEFT);
		textField_VATNumber.setEditable(false);
		textField_VATNumber.setColumns(10);
		textField_VATNumber.setBackground(Color.WHITE);
		textField_VATNumber.setBounds(112, 7, 197, 22);
		panel_VATNumber.add(textField_VATNumber);
		
		panel_openFile = new JPanel();
		panel_openFile.setLayout(null);
		panel_openFile.setBounds(10, 5, 95, 24);
		panel_content.add(panel_openFile);
		
		button_openFile = new JButton("開啟檔案");
		button_openFile.setBounds(5, 0, 87, 23);
		panel_openFile.add(button_openFile);
		
		registerEvent();
	}

    private void registerEvent() {
		// 按下"開啟檔案"按鈕
		button_openFile.addActionListener(e -> {
            // 開檔前先清除上次的list與table內容
            String[] receiptNumbers = new String[0];
            list_receiptNumbers.setListData(receiptNumbers);
            tableModel_details.setRowCount(0);	// 清除之前的table後再重新畫新的table
            // 開檔案
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();
            if (file != null)
                dataList = Utils.openFile(file);
            else // 使用者沒開啟檔案 (按下取消)
                return;
            // 設定發票號碼清單
            receiptNumbers = new String[dataList.size()];
            for (int i=0; i<receiptNumbers.length; i++)
                receiptNumbers[i] = dataList.get(i).receiptNumber;
            list_receiptNumbers.setListData(receiptNumbers);
        });
		
		// 發票號碼清單改變
		list_receiptNumbers.addListSelectionListener(e -> {
            if (list_receiptNumbers.getLastVisibleIndex()>0) {
                textField_date.setText(dataList.get(list_receiptNumbers.getSelectedIndex()).date);
                textField_VATNumber.setText(dataList.get(list_receiptNumbers.getSelectedIndex()).VATNumber);
                textField_shopName.setText(dataList.get(list_receiptNumbers.getSelectedIndex()).shopName);
                textField_total.setText(dataList.get(list_receiptNumbers.getSelectedIndex()).total);
                textField_paidBy.setText(dataList.get(list_receiptNumbers.getSelectedIndex()).paidBy);
                drawTable(list_receiptNumbers.getSelectedIndex());
            }
        });
	}
	
	private void drawTable(int index) {
		tableModel_details.setRowCount(0);	// 清除之前的table後再重新畫新的table
		for (int i=0; i<dataList.get(index).details.size(); i++)
			tableModel_details.addRow(new Object[]{dataList.get(index).details.get(i).cost, dataList.get(index).details.get(i).name});
	}
}
