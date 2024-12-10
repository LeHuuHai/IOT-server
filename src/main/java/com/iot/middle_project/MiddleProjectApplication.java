package com.iot.middle_project;

import com.iot.middle_project.repository.QuerySoilMoistureRepository;
import com.iot.middle_project.service.SoilMoistureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class MiddleProjectApplication implements CommandLineRunner {
public class MiddleProjectApplication{

//	@Autowired
//	private SoilMoistureService soilMoistureService;

	public static void main(String[] args) {
		SpringApplication.run(MiddleProjectApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		// Gọi phương thức để thực thi sau khi ứng dụng đã khởi động
//		soilMoistureService.run();
//	}
}
