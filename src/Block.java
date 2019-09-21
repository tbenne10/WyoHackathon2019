import java.util.Date;
import java.util.Vector;
import java.util.Random;

public class Block {
    //Creating a Merkle Tree: https://keyholesoftware.com/2018/04/10/blockchain-with-java/
    
	public String hash; //Merkle root of current block
	public String previousHash; //Merkle root of previous block
	private String data; //Our data will be a simple message.
	private long timeStamp; //Number of milliseconds since 1/1/1970.
    private int nonce; //32 bit hash to solve (proof of work)
    private float difficulty; //difficulty of proof of work
    Vector transaction_list = new Vector(); //List of all block transactions

	//Block Constructor
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.

        /* Hayden's transaction code
        //Create Transaction list
        int num_transactions = 10; //Number of transactions to include
        //Create transactions
        for (int i = 0; i < num_transactions; i++) {
            Random rand = new Random();
            //Get random from_address
            int seed1 = rand.nextInt(2147483647);
            String from_rand = StringUtil.applySha256(Long.toString(seed1));
            //Get random to_address
            int seed2 = rand.nextInt(2147483647);
            String to_rand = StringUtil.applySha256(Long.toString(seed2));       
            //Get current time
            long temp_time = new Date().getTime();
            //Add to transaction_list
            Transaction t = new Transaction(from_rand, to_rand, temp_time);
            transaction_list.add(t); //Add to transaction_list
        }
        //Print transaction_list
        System.out.println(transaction_list.size());
        System.out.println(transaction_list.get(1));
        for (int j = 0; j < transaction_list.size(); j++) {
            //System.out.println(transaction_list.get(j));
            //System.out.println(transaction_list.get(j).from_address);
            //System.out.println(transaction_list.get(j).to_address);
        }
        */
	}

    //Hash Calculation
    public String calculateHash() {
	    String calculatedhash = StringUtil.applySha256( 
	  		previousHash +
	  		Long.toString(timeStamp) +
	  		data 
	  		);
        return calculatedhash;
    }

    //Block mining algorithm
    public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
}
