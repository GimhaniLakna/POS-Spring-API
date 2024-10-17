package lk.ijse.possystemapispring.controller;

import lk.ijse.possystemapispring.CustomStatusCode.SelectCustomerAndItemAndOrderErrorStatus;
import lk.ijse.possystemapispring.dto.Impl.ItemDTO;
import lk.ijse.possystemapispring.dto.ItemStatus;
import lk.ijse.possystemapispring.exception.DataPersistException;
import lk.ijse.possystemapispring.exception.ItemNotFoundException;
import lk.ijse.possystemapispring.service.ItemService;
import lk.ijse.possystemapispring.util.RegexProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    private static Logger logger= LoggerFactory.getLogger(ItemController.class);
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){
        try {
            itemService.saveItem(itemDTO);
            logger.info("Item saved successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.error("Item saved fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Item saved fail!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{itemID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemStatus searchItem(@PathVariable ("itemID") String itemId){
        if (!RegexProcess.itemIdMatcher(itemId)) {
            logger.error("Item search fail!");
            return new SelectCustomerAndItemAndOrderErrorStatus(1,"Item ID is not valid");
        }
        return itemService.searchItem(itemId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(){
        return itemService.getAllItems();
    }

    @PutMapping(value = "/{itemID}")
    public ResponseEntity<Void> updateItem(@PathVariable ("itemID") String itemId,
                                           @RequestBody ItemDTO updateItemDto){
        try{
            if (!RegexProcess.itemIdMatcher(itemId) || updateItemDto==null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            itemService.updateItem(itemId,updateItemDto);
            logger.info("Item updated successfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            logger.error("Item not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Item not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping(value = "/{itemID}")
    public ResponseEntity<Void> deleteItem(@PathVariable ("itemID") String itemId){
        try {
            if (!RegexProcess.itemIdMatcher(itemId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            itemService.deleteItem(itemId);
            logger.info("Item Deleted successfully!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            logger.error("Item not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Item not found!"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
