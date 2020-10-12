import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {

    public static void main(String[] args) throws IOException {

//        Path s = Path;
//        Files.readAllBytes(s);
/*

        byte[] a = {0,0,0,0,0,0,0,0};
        byte[] b = {1,1,1,1,1,1,1,1};
        byte[] c = new byte[8];

        //a = 00000000;
       // b = 11111111;
        for (int i = 0; i < a.length; i++) {
            c[i] = (byte) (a[i] ^ b[i]);

            System.out.println(a[i] + "^" + b[i] + "=" + c[i]);
        }
*/


        Path cs = Paths.get("C:\\Users\\USER\\Desktop\\test files\\cipher_short");
        byte[] cipher_short = Files.readAllBytes(cs);
        Path cl = Paths.get("C:\\Users\\USER\\Desktop\\test files\\cipher_long");
        byte[] cipher_long = Files.readAllBytes(cl);

        Path Couts = Paths.get("C:\\Users\\USER\\Desktop\\test files\\Cout_short");
        byte[] Cout_short = Files.readAllBytes(Couts);
        Path Coutl = Paths.get("C:\\Users\\USER\\Desktop\\test files\\Cout_long");
        byte[] Cout_long = Files.readAllBytes(Coutl);

        Path ms = Paths.get("C:\\Users\\USER\\Desktop\\test files\\message_short");
        byte[] message_short = Files.readAllBytes(ms);
        Path ml = Paths.get("C:\\Users\\USER\\Desktop\\test files\\message_long");
        byte[] message_long = Files.readAllBytes(ml);

        Path Mouts = Paths.get("C:\\Users\\USER\\Desktop\\test files\\Mout_short");
        byte[] Mout_short = Files.readAllBytes(Mouts);
        Path Moutl = Paths.get("C:\\Users\\USER\\Desktop\\test files\\Mout_long");
        byte[] Mout_long = Files.readAllBytes(Moutl);


        for (int i = 0; i < cipher_short.length ; i++) {
            if(message_short[i]!= Mout_short[i]){
                System.out.println("false");
            }

        }
        for (int i = 0; i < cipher_short.length ; i++) {
            if(cipher_short[i]!= Cout_short[i]){
                System.out.println("false");
            }

        }
        for (int i = 0; i < cipher_long.length ; i++) {
            if(cipher_long[i]!= Cout_long[i]){
                System.out.println("false");
            }

        }
        for (int i = 0; i < cipher_long.length ; i++) {
            if(message_long[i]!= Mout_long[i]){
                System.out.println("false");
            }

        }

    }
}
