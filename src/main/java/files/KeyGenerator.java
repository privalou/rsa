package files;

import rsa.KeyPair;
import rsa.PrivateKey;
import rsa.PublicKey;

import java.io.*;
import java.math.BigInteger;

public class KeyGenerator {
    public static void generate(String name) {
        KeyPair keyPair = new KeyPair();
        try (PrintWriter publicPrintWriter = new PrintWriter(name + ".public");
             PrintWriter privatePrintWrite = new PrintWriter(name + ".private")) {

            PublicKey publicKey = keyPair.getPublicKey();
            publicPrintWriter.println(publicKey.getN());
            publicPrintWriter.println(publicKey.getE());

            PrivateKey privateKey = keyPair.getPrivateKey();
            privatePrintWrite.println(privateKey.getN());
            privatePrintWrite.println(privateKey.getD());

            publicPrintWriter.close();
            privatePrintWrite.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PublicKey loadPublicKey(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            BigInteger n = new BigInteger(reader.readLine());
            BigInteger e = new BigInteger(reader.readLine());
            reader.close();
            return new PublicKey(e, n);
        }
    }

    public static PrivateKey loadPrivateKey(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            BigInteger n = new BigInteger(reader.readLine());
            BigInteger d = new BigInteger(reader.readLine());
            reader.close();
            return new PrivateKey(d, n);
        }
    }
}
