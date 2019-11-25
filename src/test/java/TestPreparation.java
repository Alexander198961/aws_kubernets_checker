import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Before;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.AppsV1Api;
import java.io.InputStream;
import api.*;





public abstract class TestPreparation {

    protected   ExtendedCoreApi api = new ExtendedCoreApi();

    @Before
    public void setUpTest(){
    }

    @BeforeClass
    public static void prepareAuth() throws Exception
    {
        KubeConfig.registerAuthenticator(new EksAuthenticator());
        String content = new String(Files.readAllBytes(Paths.get(System.getProperty("user.home") +"/.kube/config")));
        KubeConfig kc = KubeConfig.loadKubeConfig(new StringReader(content));
        ApiClient client = ClientBuilder.kubeconfig(kc).build();
        Configuration.setDefaultApiClient(client);

    }



}