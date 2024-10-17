package lk.ijse.possystemapispring.service;

import lk.ijse.possystemapispring.dto.Impl.ItemDTO;
import lk.ijse.possystemapispring.dto.ItemStatus;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    ItemStatus searchItem(String itemId);

    List<ItemDTO> getAllItems();

    void updateItem(String itemId, ItemDTO updateItemDto);

    void deleteItem(String itemId);
}
