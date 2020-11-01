import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ApplicationConfig {

    public static final Config DBLP = ConfigFactory.load("dblp.conf");
    public static final Config complete_appconfig = ConfigFactory.load();
}
