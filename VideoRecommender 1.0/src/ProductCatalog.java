import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {

	public List<String> getProductCategories(){
		List<String> categories = new ArrayList<>();
		categories.add("Books");
		categories.add("Music");
		categories.add("Movies");
		return categories;
	}

}
