package net.therap.estaurant.propertyEditor;

import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.service.RestaurantTableService;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class RestaurantTableEditor extends PropertyEditorSupport {

    @Autowired
    RestaurantTableService restaurantTableService;

    @Override
    public String getAsText() {
        RestaurantTable restaurantTable = (RestaurantTable) getValue();

        return restaurantTable == null ? "" : restaurantTable.getName();
    }

    @Override
    public void setAsText(String resTableId){

        if(Objects.isNull(resTableId) || resTableId.isEmpty()){
            setValue(null);
        }

        setValue(restaurantTableService.findById(Integer.parseInt(resTableId)));
    }
}
