import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by akjohri on 7/28/2017.
 */
public class ServiceApplication extends Application<ServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "Advertiser service";
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {

    }

    @Override
    public void run(ServiceConfiguration configuration, Environment environment) {
        final ServiceResource resource = new ServiceResource();
        environment.jersey().register(resource);

        final String rpcuser ="multichainrpc";
        final String rpcpassword ="7H3pMurCfk3jJKpUUmcqZDd74KkPzMJQffvXSBZ19v6U";

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (rpcuser, rpcpassword.toCharArray());
            }
        });
    }
}
