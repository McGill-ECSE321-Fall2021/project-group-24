package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Item;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {
  List<Item> findItemsByType(Item.Type type);
  Item findItemByItemNumber(String itemNumber);
}
