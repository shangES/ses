package icbc.cmis.util;
import java.security.*;
import java.security.spec.*;
import icbc.cmis.util.SignatureDAO;
import icbc.cmis.base.TranFailException;
import icbc.cmis.util.Encode;

public class Signature  implements java.io.Serializable{

  public Signature() {
  }

  public String signature(String source) throws TranFailException {
    byte[] originalMessage = source.getBytes();
		byte[] encKey = null;
    String ret = null;

    try{
      SignatureDAO dao = new SignatureDAO(new icbc.cmis.operation.DummyOperation());
      encKey = Encode.tobytes(dao.getPrivateKey());


      KeyPairGenerator RSAKeyPairGen;     // the key pair generator
      KeyPair RSAKeyPair;                 // the key pair
      PublicKey RSAPub;                // the public key
      PrivateKey RSAPriv;              // the private key

      PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA", "IBMJCE");
      RSAPriv = keyFactory.generatePrivate(privKeySpec);

    /***************************************
     * Get a MD5withRSA signature instance
     ***************************************/

        java.security.Signature sig = null;
        byte[] sign = null;

        // Now, get an instance of the MD5iwithRSA signature
        try {
            sig = java.security.Signature.getInstance("MD5withRSA", "IBMJCE");

        } catch (Exception ex) {
          throw new TranFailException("signature004","icbc.cmis.util.Signature",ex.getMessage(),ex.getMessage());
        }

    /*******************
     * Sign the message
     *******************/

        try {

            /*
              1. Initialize (signing) with the private key  [Signature.initSign(PrivateKey)]
              2. Update with the original message           [Signature.update(byte[])]
              3. Generate a signature                       [Signature.sign()]
             */

            sig.initSign(RSAPriv);
            sig.update(originalMessage);
            sign = sig.sign();

            ret = Encode.toHexString(sign);

        } catch (Exception ex) {
          throw new TranFailException("signature005","icbc.cmis.util.Signature",ex.getMessage(),ex.getMessage());
        }
    } catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("signature003","icbc.cmis.util.Signature",ex.getMessage(),ex.getMessage());
    }
    return ret;
  }
}