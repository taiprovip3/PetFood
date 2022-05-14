package springboot.petfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetfoodApplication {

	
//	ĐỔI LẠI ĐƯỜNG DẪN HÌNH CỦA PRODUCT
//  AI GIỮ HÌNH THÌ BỎ HÌNH VÀO SRC/MAIN/RESOURCES/STATIC/IMG NHA!!! 
//	BỎ VÀO SQL, RỒI NHẤN F5 ĐỂ CHẠY
	
//	delete from cart
//	delete from products
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Royal Canin','DOG','The best of popular pet food bought and used for dogs and cats', 185.0, 4.5, 3600,'/img/royal_canin.jpg',1);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Smart Heart','CAT','Strongest label in labor economic buisiness, very heath impact to cats and dogs heart', 222.0, 5.1, 2100,'/img/smart_heart.jfif',2);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Classic Pets','ALL','Very light and smuff delicious food for both dogs and cats. This was produced in 1988 factory until now!', 159.0, 6.3, 1360,'/img/classic_pets.jfif',3);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Pedigree','DOG','Pedigree dog food is a brand of Mars corporation (USA). Products include all types of dog food. Pedigree is a ready-to-eat wet food, so it is suitable for small pet dogs such as chihuahuas, foxes, poodles, etc.', 168.0, 5.1, 2390,'/img/pedigree.png',4);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Winner','CAT','This is a food exclusively for large dogs, especially good for becgie, malinois, pitbull, rottweiler, doberman', 129.0, 4.6, 3600,'/img/winner.png',5);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('ANF','ALL','ANF ​​dog food is a soft food line specializing in lamb, so it is suitable for 6-month-old puppies or nursing dogs.', 200.0, 4.8, 1560,'/img/anf.png',6);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Fib','DOG','Fibs is a product line that is suitable for all dog breeds and for all sizes from small to large.', 176.0, 4.9, 1862,'/img/fib.png',1);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Fitmin','CAT','This is a brand of dry food for large breeds and adult dogs like the Puppy. Food is made entirely from fresh meat and then dried, so it retains maximum nutrients.', 159.0, 6.0, 2874,'/img/fitmin.png',2);
//	insert into products (product_name,product_type,product_description,product_price,product_weight,product_quantity,product_image,category_id)
//		values ('Zenith','ALL','Providing soft grain food for puppies, Zeniths products contain many nutrients such as protein, starch, protein', 182.0, 5.5, 2235,'/img/zenith.png',3);
//	select * from products
	
	public static void main(String[] args) {
		SpringApplication.run(PetfoodApplication.class, args);
	}
}
