package mahi.labs.monkeystore.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class EnvVariableController {


    @Value("${your.env.variable.name}")
    private String envVariableValue;


    @GetMapping("/check-env")
    public String checksEnvironmentVariable() {
        return "Environment variable value: " + envVariableValue;
    }
}
