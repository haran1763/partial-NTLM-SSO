import java.util.HashMap;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public class LoginConfig extends Configuration {

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        // TODO Auto-generated method stub
        AppConfigurationEntry[] configs = new AppConfigurationEntry[1];
        configs[0] = new AppConfigurationEntry(DaLoginModule.class.getName(), AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap<String, Object>());
        // configs[1] = new AppConfigurationEntry(SmsAuthLoginModule.class.getName(), AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap<String, Object>());
        return configs;
    }
    
}
