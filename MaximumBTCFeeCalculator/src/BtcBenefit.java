import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtcBenefit {

	public static int count=1;
	public static int maxBlockSize = 1000000;
	public static double btcReward = 12.5;
	public static int[] transactions={57247,98732,134928,77275,29240,15440,70820,139603,63718,143807,190457,40572};
	public static double[] fees={0.0887,0.1856,0.2307,0.1522,0.0532,0.0250,0.1409,0.2541,0.1147,0.2660,0.2933,0.0686};
	public static List<Double> feesTotal= new ArrayList<Double>();	


	/**
	 * Main program for calculating max btc fees
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Calculation for maximum BTC fees benefit");
		//Calculate possible sub sets of the transactions.
		printSubSet(transactions);

		//sort on total fees list
		Collections.sort(feesTotal);

		System.out.println("Total possible transaction combinations in  building the block of size 1000000 bytes = " + count);
		//Max BTC fees for the transactions upto block size of 1000000
		double maxBtcFees = feesTotal.get(feesTotal.size()-1);
		//String str= transactionMap.get(arg0)
		System.out.println("Max BTC Fees on transactions upto 1000000 bytes = " + maxBtcFees);
		//Max BTC including reward fees
		maxBtcFees += btcReward;
		System.out.println("Max BTC Fees on transactions upto 1000000 bytes including reward fees = " + maxBtcFees);

	}


	/**
	 * Function to find transaction combinations.
	 * ifPrint will check whether to include the element in the combination or not
	 * @param transactions input array
	 */
	public static void printSubSet(int[] transactions){
		for(int index=0; index<transactions.length; index++){
			boolean[] ifPrint = new boolean[transactions.length];
			//find combintions from 0 to index+1
			printSubSet(transactions, ifPrint, 0, index+1);
		}
	}


	/**
	 * Function to find transaction combinations and total btc fees 
	 * @param transactions
	 * @param ifPrint boolean check to include transaction or not in combination
	 * @param start index
	 * @param remain index
	 */
	public static void printSubSet(int[] transactions, boolean[] ifPrint, int start, int remain){
		//if remain ==0 means all combinations are identified against ifPrint array
		if(remain ==0){
			int totalTransactionBytes=0;
			double totalBtcFees=0.0;
			StringBuilder totalTransactionString = new StringBuilder(); 

			for(int i=0; i<ifPrint.length; i++){
				if(ifPrint[i]){
					//sum of totalTransactionBytes and totalBtcFees of the identified combination using ifPrint array
					totalTransactionBytes += transactions[i];										
					totalBtcFees += fees[i];
				}
			}
			if(totalTransactionBytes <= maxBlockSize){
				//count transaction combinations 
				count++;
				//add totalBtcFees of all transaction combinations into the static list
				feesTotal.add(totalBtcFees);				

				for(int i=0; i<ifPrint.length; i++){
					if(ifPrint[i])
						totalTransactionString.append(Integer.toString(transactions[i])).append(" , ");						
				}

				System.out.println("{"+totalTransactionString.substring(0, totalTransactionString.length()-2) +"} -> Total Transaction Bytes = "+ totalTransactionBytes + " and BTCFees = "+ totalBtcFees +"}\n");
			}

		}
		//else identify transactions from start to remain to include in combination 
		else{
			if(start+remain > transactions.length);
			else{
				for(int i=start; i <transactions.length; i ++){
					if(!ifPrint[i]){
						ifPrint[i]=true;
						printSubSet(transactions, ifPrint, i+1, remain-1);
						ifPrint[i]=false;
					}
				}
			}

		}
	}

}
