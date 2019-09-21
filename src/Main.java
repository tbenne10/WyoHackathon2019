import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Date;
import java.util.*;
import java.security.Security;
import java.util.Base64;
//import com.google.gson.GsonBuilder;

class Main {

  private static ArrayList<Block> the_blockchain = new ArrayList<Block>();
  public static int difficulty = 1;
  public static Wallet walletA;
	public static Wallet walletB;

  public static void main(String[] args) {

    //Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());

        //System.out.println("Hello world!");
        //HashSet<String> blockchain = new Hashset<String>();

        //Get current Epoch time for block
        //Date test = new Date();
        //System.out.println(test.getTime());    

		// Block genesisBlock = new Block("Hi im the first block", "0");
		// System.out.println("Hash for block 1 : " + genesisBlock.hash);
		
		// Block secondBlock = new Block("Yo im the second block",genesisBlock.hash);
		// System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		// Block thirdBlock = new Block("Hey im the third block",secondBlock.hash);
		// System.out.println("Hash for block 3 : " + thirdBlock.hash);


   /**
   * Blockchain implementation
   */

    Block prevBlock = new Block("Hi im the first block", "0");
    int num_exec = 2; 
    long total_time = 0; 

    for(int i = 0; i < num_exec; i++){
        
        long startTime = System.nanoTime();

        for(int j = 0; j < 3; j++){
            Block b = new Block("Block "+ j, prevBlock.hash);
            //System.out.println("Hash at " + j + ": " + b.hash);
            the_blockchain.add(b); 
            prevBlock = b;  
        }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            total_time += duration; 
            System.out.println ("TIME NANO.> " + duration);
            //System.out.println(the_blockchain); 
    }
    long average_time = total_time/num_exec;
    System.out.println("AVG TIME .> " + average_time);

    //Test mine a few blocks
    System.out.println("Starting mining process");
    System.out.println("Trying to Mine block 1... ");
    the_blockchain.get(0).mineBlock(difficulty);
    System.out.println("Trying to Mine block 2... ");
    the_blockchain.get(1).mineBlock(difficulty);
    System.out.println("Trying to Mine block 3... ");
    the_blockchain.get(2).mineBlock(difficulty);

    //Validate blockchain
    System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		// String blockchainJson = new GsonBuilder().setPrettyPrinting()      .create().toJson(the_blockchain);
		// System.out.println("\nThe block chain: ");
		// System.out.println(blockchainJson);

    //System.out.println(the_blockchain);
    }


    public static Boolean isChainValid() {
        Block currentBlock; 
        Block previousBlock;
    
        //Loop through blockchain to check hashes:
        for(int i=1; i < the_blockchain.size(); i++) {
            currentBlock = the_blockchain.get(i);
            previousBlock = the_blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");		
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }
}
