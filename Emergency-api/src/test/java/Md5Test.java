import cn.anselyuki.system.util.MD5Utils;
import org.junit.jupiter.api.Test;

/**
 * @author AnselYuki
 * @date 2022/10/16 20:21
 */
public class Md5Test {
    @Test
    public void testMd5() {
        String salt = "cfbf6d34-d3e4-4653-86f0-e33d4595d52b";
        String password = "123456";
        String encoded = MD5Utils.md5Encryption(password, salt);
        System.out.println("encoded = " + encoded);
    }
}
