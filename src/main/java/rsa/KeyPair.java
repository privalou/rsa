package rsa;

import java.math.BigInteger;
import java.util.Random;

public class KeyPair {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger fi;
    private BigInteger e;
    private BigInteger d;
    private static final int bitLength = 1024;
    private int pqBigLength;
    private PublicKey publicKey;
    private PrivateKey privateKey;

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
        e=generatePublicExponent();
        d = e.modInverse(fi);
        publicKey = new PublicKey(e,n);
        privateKey = new PrivateKey(d,n);
    }

    private BigInteger generatePublicExponent() {
        while (true) {
            Random random = new Random();
            int length = pqBigLength + random.nextInt(fi.bitLength() - pqBigLength);
            BigInteger exponent = new BigInteger(length, new Random());
            if (exponent.compareTo(BigInteger.ONE) != 0
                  && exponent.compareTo(fi) == -1
                  && exponent.gcd(fi).compareTo(BigInteger.ONE) == 0) return exponent;
        }
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getFi() {
        return fi;
    }

    public void setFi(BigInteger fi) {
        this.fi = fi;
    }

    public static int getBitLength() {
        return bitLength;
    }

    public int getPqBigLength() {
        return pqBigLength;
    }

    public void setPqBigLength(int pqBigLength) {
        this.pqBigLength = pqBigLength;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
