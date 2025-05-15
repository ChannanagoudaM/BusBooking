package com.example.UserService;

import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private  UserRepository userRepository;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UserServiceApplication.class, args);
//
//		objectToJson();
//		jsonToObject();
//		writeValue();
//		convertJavaObjects();
//		formatting();
	}


	@Override
	public void run(String... args) throws Exception {
//		Optional<User> user = userRepository.findByEmail("john.doe@example.com");
//		user.ifPresent(System.out::println);
//		userRepository.findById(1);
	}

	//Object mapper is present in jackson databind
//	public static void objectToJson() throws JsonProcessingException {
//		User user=new User(1,"Mahesh","mahesh123@gmail.com","mahesh123", ADMIN,"1234567890");
//		ObjectMapper objectMapper=new ObjectMapper();
//		String string = objectMapper.writeValueAsString(user);
//		System.out.println(string);
//	}
//
//	public static void jsonToObject() throws JsonProcessingException {
//		String json="{\"id\":1,\"name\":\"Mahesh\",\"email\":\"mahesh123@gmail.com\",\"password\":\"mahesh123\",\"role\":\"ADMIN\",\"phone\":\"1234567890\"}";
//		ObjectMapper objectMapper=new ObjectMapper();
//		User user = objectMapper.readValue(json, User.class);
//		System.out.println(user.getEmail());
//	}
//
//	public static void writeValue() throws IOException {
//		File file=new File("C:\\Users\\Sreenivas Bandaru\\Desktop\\Channanagouda\\Student\\Student.json");
//		User user=new User(1,"Mahesh","mahesh123@gmail.com","mahesh123", ADMIN,"1234567890");
//		ObjectMapper objectMapper=new ObjectMapper();
//		objectMapper.writeValue(file,user);
//	}
//
//	public static void convertJavaObjects()
//	{
//		User user=new User(1,"Mahesh","mahesh123@gmail.com","mahesh123", ADMIN,"1234567890");
//		ObjectMapper objectMapper=new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//		UserDto userDto = objectMapper.convertValue(user, UserDto.class);
//		System.out.println(userDto);
//	}

	public static void formatting()
	{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd-yyyy");
		LocalDate localDate=LocalDate.now();
		System.out.println(localDate);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String format = simpleDateFormat.format(date);
		System.out.println(format);
	}


}
