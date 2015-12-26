import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Utils {
	
	public static List<ReceiptData> openFile(File file) {
		StringBuilder data = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "BIG5"))) {
			while (reader.ready())
				data.append(reader.readLine());
			reader.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return (data.length()<12) ? null : parse(data.toString());	// 一筆發票最少的length為12
	}
	
	private static List<ReceiptData> parse(String data) {
		ReceiptData receiptData = new ReceiptData();
		List<ReceiptData> dataList = new ArrayList<>();
		
		int index = 0;
		String[] tokens = data.split("\\|");				// 以"|"來分隔tokens
		index = setReceiptData(receiptData, tokens, index);	// 新增第一筆發票資料 (避免下面的迴圈馬上就碰到M，但卻沒有資料可以push)
		
		while (index < tokens.length) {
			// 遇到M，則把之前的發票資料push進list，並再新增一筆發票資料
			if (tokens[index].equals("M")) {
				dataList.add(receiptData);
				receiptData = new ReceiptData();
				index = setReceiptData(receiptData, tokens, index);
			}
			// 遇到D，代表新增發票的明細
			if (tokens[index].equals("D"))
				receiptData.details.add(receiptData.new Detail(setCost(tokens[index+=2]), tokens[index+=1]));
			index++;
		}
		dataList.add(receiptData);	// 離開迴圈後，把最後一筆資料push進list (因為不會再碰到M了)
		
		return dataList;
	}
	
	private static int setReceiptData(ReceiptData receiptData, String[] tokens, int index) {
		receiptData.receiptNumber = tokens[index+2];
		receiptData.date = new StringBuilder(tokens[index+3]).insert(4,"/").insert(7,"/").toString();
		receiptData.VATNumber = tokens[index+4];
		receiptData.shopName = tokens[index+5];
		receiptData.paidBy = String.format("%s (#%s)", tokens[index+6], tokens[index+7]);
		receiptData.total = setCost((tokens[index+8]));
		return index+8;
	}
	
	// 設定每筆明細的金額 (用split("[.]")[0]去除小數點後數字)
	private static String setCost(String cost) {
		return NumberFormat.getCurrencyInstance(Locale.TAIWAN).format(Double.parseDouble(cost)).split("[.]")[0];
	}
	
}
