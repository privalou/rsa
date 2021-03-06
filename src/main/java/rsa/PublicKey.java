package rsa;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.Key;

public class PublicKey implements Serializable {
    private BigInteger e;
    private BigInteger n;

    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return n;
    }

    public byte[] encrypt(byte[] plainText) {
        byte[] cipherText = RSAUtils.modPowByte(plainText, e, n);
        return cipherText;
    }

    public byte[] decrypt(byte[] cipherText) {
        return RSAUtils.modPowByte(cipherText, e, n);
    }
}
