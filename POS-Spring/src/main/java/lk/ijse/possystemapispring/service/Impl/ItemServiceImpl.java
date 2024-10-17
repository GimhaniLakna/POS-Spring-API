package lk.ijse.possystemapispring.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.possystemapispring.CustomStatusCode.SelectCustomerAndItemAndOrderErrorStatus;
import lk.ijse.possystemapispring.dao.ItemDao;
import lk.ijse.possystemapispring.dto.Impl.ItemDTO;
import lk.ijse.possystemapispring.dto.ItemStatus;
import lk.ijse.possystemapispring.entity.Impl.ItemEntity;
import lk.ijse.possystemapispring.exception.DataPersistException;
import lk.ijse.possystemapispring.exception.ItemNotFoundException;
import lk.ijse.possystemapispring.service.ItemService;
import lk.ijse.possystemapispring.util.AppUtil;
import lk.ijse.possystemapispring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private Mapping itemMapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setId(AppUtil.generateItemId());
        ItemEntity savedItem=
                itemDao.save(itemMapping.toItemEntity(itemDTO));
        if(savedItem == null){
            throw new DataPersistException("Item not saved");
        }
    }

    @Override
    public ItemStatus searchItem(String itemId) {
        if(itemDao.existsById(itemId)){
            var selectedItem = itemDao.getReferenceById(itemId);
            return itemMapping.toItemDTO(selectedItem);
        }else {
            return new SelectCustomerAndItemAndOrderErrorStatus(2,"Search Item not found");
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemEntity>allItems=itemDao.findAll();
        return itemMapping.asItemDtoLIst(allItems);
    }

    @Override
    public void updateItem(String itemId, ItemDTO updateItemDto) {
        Optional<ItemEntity> findItem =itemDao.findById(itemId);
        if (!findItem.isPresent()){
            throw new ItemNotFoundException("This id "+itemId+" has Item Not Found");
        }else {
            findItem.get().setItemName(updateItemDto.getName());
            findItem.get().setQty(updateItemDto.getQty());
            findItem.get().setUnitPrice(updateItemDto.getUnitPrice());
        }

    }

    @Override
    public void deleteItem(String itemId) {
        Optional<ItemEntity> findItem=itemDao.findById(itemId);
        if (!findItem.isPresent()){
            throw new ItemNotFoundException("This id "+itemId+" has Item Not Found");
        }else {
            itemDao.deleteById(itemId);
        }

    }
}
