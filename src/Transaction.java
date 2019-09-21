import java.util.Date;
import java.util.*;
import java.security.*;

public class Transaction {
    public String transactionId; // this is also the hash of the transaction.
    public PublicKey sender; // senders address/public key.
    public PublicKey reciepient; // Recipients address/public key.
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.
    
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    
    private static int sequence = 0; // a rough count of how many transactions have been generated. 
    
    // Constructor: 
    public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
      this.sender = from;
      this.reciepient = to;
      this.value = value;
      this.inputs = inputs;
    }
    
    // This Calculates the transaction hash (which will be used as its Id)
    private String calulateHash() {
      sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
      return StringUtil.applySha256(
          StringUtil.getStringFromKey(sender) +
          StringUtil.getStringFromKey(reciepient) +
          Float.toString(value) + sequence
          );
    }

    //Signs all the data we don't wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
      String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
      signature = StringUtil.applyECDSASig(privateKey,data);		
    }

    //Verifies the data we signed hasn't been tampered with
    public boolean verifiySignature() {
      String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
      return StringUtil.verifyECDSASig(sender, data, signature);
    }


    /* Hayden's code
    public String hash; //Hash ID of transaction
    public String from_address; //Sending wallet address
    public String to_address; //Receiving wallet address
    public long time; //Time of transaction

	//Transaction Constructor
	public Transaction(String from_address, String to_address, long time) {
        this.from_address = from_address;
        this.to_address = to_address;
        this.time = time;
        this.hash = StringUtil.applySha256( 
	  		from_address +
            to_address +
	  		Long.toString(time)
	  		);
	}
    */
}
