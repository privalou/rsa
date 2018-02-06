package rsa;

import java.math.BigInteger;
import java.util.Random;

public class KeyPair {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger fi;
    private static final int bitLength = 1024;
    private int pqBigLength;

    public KeyPair(){
        pqBigLength = bitLength/2;
        do {
            //generate p
            p = BigInteger.probablePrime(pqBigLength, new Random());
            //generate q
            q = BigInteger.probablePrime(pqBigLength, new Random());
            //multiply q and p
            n = p.multiply(q);
        } while (n.bitLength()!= bitLength);
        fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
}
