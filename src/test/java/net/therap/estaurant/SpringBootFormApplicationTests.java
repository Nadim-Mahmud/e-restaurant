package net.therap.estaurant;

import net.therap.estaurant.entity.Item;
import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.entity.UserType;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootFormApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() throws Exception {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/12");
		User user = new User(
				"Nadim",
				"Mahmud",
				date,
				"nadim@gamil.com",
				"123",
				UserType.ADMIN,
				date,
				new ArrayList<RestaurantTable>(Arrays.asList(new RestaurantTable())),
				new ArrayList<Item>(Arrays.asList(new Item()))
		);

		userService.saveOrUpdate(user);

	}

}
