package de.hsrm.mi.web.bratenbank.bratservice;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class BratChatController {

    @MessageMapping("/topic/bratchat/toserver")
    @SendTo("/topic/bratchat/fromserver")
    public String chat(String msg) {
        return LocalDateTime.now() + " " + msg;
    }

}
