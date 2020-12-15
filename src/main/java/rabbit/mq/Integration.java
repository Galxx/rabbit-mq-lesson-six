package rabbit.mq;

import org.springframework.stereotype.Component;

@Component
public class Integration {

    public void print(String text) {
        System.out.println(text);
    }

}
