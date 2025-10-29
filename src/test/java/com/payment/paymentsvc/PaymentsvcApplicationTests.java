package com.payment.paymentsvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@ActiveProfiles("test")
class PaymentsvcApplicationTests {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Test
    void contextLoads() {
        assertThat(activeProfile).isEqualTo("test");
    }

}
