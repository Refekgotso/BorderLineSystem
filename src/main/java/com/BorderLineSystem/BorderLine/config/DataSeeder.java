package com.BorderLineSystem.BorderLine.config;

import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(ImmigrantRepository immigrantRepository) {
        return args -> {
            if (immigrantRepository.count() == 0) {
                Immigrant i1 = new Immigrant();
                i1.setFullName("Tendai Moyo");
                i1.setPassportNumber("ZW123456");
                i1.setNationality("Zimbabwe");
                i1.setDateOfBirth(LocalDate.of(1995, 4, 12));
                i1.setGender("Male");
                immigrantRepository.save(i1);

                Immigrant i2 = new Immigrant();
                i2.setFullName("Amara Nkosi");
                i2.setPassportNumber("MZ987654");
                i2.setNationality("Mozambique");
                i2.setDateOfBirth(LocalDate.of(1998, 9, 3));
                i2.setGender("Female");
                immigrantRepository.save(i2);

                Immigrant i3 = new Immigrant();
                i3.setFullName("Lesedi Khumalo");
                i3.setPassportNumber("BW555222");
                i3.setNationality("Botswana");
                i3.setDateOfBirth(LocalDate.of(2000, 1, 20));
                i3.setGender("Female");
                immigrantRepository.save(i3);
            }
        };
    }
}