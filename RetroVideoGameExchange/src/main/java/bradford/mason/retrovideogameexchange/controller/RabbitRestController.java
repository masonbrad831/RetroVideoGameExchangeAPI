package bradford.mason.retrovideogameexchange.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/email")
public class RabbitRestController {
    private final static String QUEUE_NAME = "hello";

    @PostMapping("/event")
    public void createMessage(@RequestBody Map map) throws IOException, TimeoutException {
        String body = (map.get("body")).toString();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(3455);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, body.getBytes());
            System.out.println(" [x] Sent '" + body + "'");
        }
    }
}
