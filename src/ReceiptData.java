import java.util.ArrayList;
import java.util.List;

public class ReceiptData {
	public String receiptNumber, date, VATNumber, shopName, paidBy, total;
    public List<Detail> details = new ArrayList<>();
	
	public class Detail {
		String cost, name;		
		public Detail(String cost, String name) {
			this.cost = cost;
			this.name = name;
		}
	}
}
