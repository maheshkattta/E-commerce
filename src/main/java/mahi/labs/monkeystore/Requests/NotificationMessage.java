package mahi.labs.monkeystore.Requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@Data
public class NotificationMessage {

    private String recipientToken;
    private String title;
    private String body;
    private Map<String,String> data;

}