package rabbit.mq;

import domain.Product;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Application {
    @Autowired
    private ConnectionFactory connectionFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Queue myQueue() {
        return new Queue("TEST12345", true);
    }

    @RabbitListener(queues = "TEST12345")
    public void list(Product product) {
        System.out.println("Product from my queue:" + product);
    }

    @Autowired
    private RabbitTemplate template;

    @Scheduled(fixedRate = 5000)
    private void sendMessage() {

        Product product = new Product();
        product.setId(1L);
        product.setTitle("Tea");

        template.convertAndSend("example-2", "", product);

    }

    /*@Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(
                Amqp.inboundAdapter(connectionFactory, "TEST12345"))
                .log("До получения сообщения из очереди")
                .handle("integration", "print")
                .log("После получения сообщения из очереди")
                .get();
    }*/
}
