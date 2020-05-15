package bitcoinHomework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class bitcoinClustering {
	
	public static long maxTransactionId=0l;
	public static HashMap<Long,Long> addrBalance=new HashMap<>();
	
	public static void trimming() {
		try {
			FileReader inputTx=new FileReader("\\resource\\tx.dat");
			BufferedReader bufTx=new BufferedReader(inputTx);
			String lineJustFetched=null;
			String arr[];
			long coinBaseTx=0l;
			long transaction=0l;
			
			while(true) {
				lineJustFetched=bufTx.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					long blockId=Long.parseLong(arr[1]);
					long coin=Long.parseLong(arr[2]);
					if(blockId>212575) {
						maxTransactionId=txId;
						break;
					}
					if(txId!=-1)
						transaction++;
					if(coin==0)
						coinBaseTx++;
				}
			}
			bufTx.close();
			
			System.out.println("the number of transactions : "+(maxTransactionId-1));
			System.out.println("The number of Coinbase transactions : "+coinBaseTx);
			System.out.println("Average transaction per block : "+transaction/212575);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void balanceMapping() {
		try {
			FileReader txOutFile=new FileReader("\\resource\\txout.dat");
			BufferedReader bufTxOut=new BufferedReader(txOutFile);
			String lineJustFetched=null;
			String [] arr;
			HashMap<Long,Long> addrOutBalance=new HashMap();
			HashSet<Long> addressIn=new HashSet();
			long txInCount=0l;
			long txOutCount=0l;
			long toatalOutTransaction=0l;
			 
			while(true) {
				lineJustFetched=bufTxOut.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=maxTransactionId)
						break;
					if(txId!=-1)
						txOutCount++;
					long addId=Long.parseLong(arr[2]);
					long sum=Long.parseLong(arr[3]);
					toatalOutTransaction+=sum;
					addrOutBalance.put(addId,addrOutBalance.getOrDefault(addId,0l)+sum);
				}
			}
			bufTxOut.close();
			System.out.println("the number of Unique out address : "+addrOutBalance.size());
			
			FileReader inputFile=new FileReader("\\resource\\txin.dat");
			BufferedReader bufIn=new BufferedReader(inputFile);
			long totalBalance=0l;
			
			while(true) {
				lineJustFetched=bufIn.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=maxTransactionId)
						break;
					if(txId!=-1)
						txInCount++;
					long adrId=Long.parseLong(arr[4]);
					addressIn.add(adrId);
					long sum=Long.parseLong(arr[5]);
					if(addrOutBalance.containsKey(adrId))
						addrOutBalance.put(adrId,addrOutBalance.get(adrId)-sum);
				}
			}
			bufIn.close();
			
			
			
			
			long maxBalance=0l;
			long maxBalanceAddressId=0l;
			for(long addr:addrOutBalance.keySet()) {
				totalBalance+=addrOutBalance.get(addr);
				if(addrOutBalance.get(addr)>maxBalance) {
					maxBalance=addrOutBalance.get(addr);
					maxBalanceAddressId=addr;
				}
			}
			
			System.out.println("the max balance Id : "+maxBalanceAddressId+" with maxbalance : "+maxBalance);
			
			FileReader addressesFile=new FileReader("\\resource\\addresses.dat");
			BufferedReader bufAddress=new BufferedReader(addressesFile);
			String maxAddress="Invalid Address";
			
			while(true) {
				lineJustFetched=bufAddress.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long addId=Long.parseLong(arr[0]);
					if(addId==maxBalanceAddressId) {
						maxAddress=arr[1];
						break;
					}
					
				}
			}
			bufAddress.close();
			System.out.println("The Address with maximum Balance : "+maxAddress);
			System.out.println("The average balance per Address : "+totalBalance);
			System.out.println("The average balance per Address : "+totalBalance/addrOutBalance.size());
			
			System.out.println("*************************************************************");
			
			System.out.println("Toatal unique input Address :"+addressIn.size());
			System.out.println("Toatal input transactions : "+txInCount);
			System.out.println("Averagee input transactions per Address : "+txInCount/addressIn.size());
			System.out.println("*************************************************************");
			System.out.println("Toatal unique out Address :"+addrOutBalance.size());
			System.out.println("Toatal out transactions : "+txOutCount);
			System.out.println("Averagee out transactions per Address : "+txOutCount/addrOutBalance.size());
			System.out.println("*************************************************************");
			long average=((txInCount/addressIn.size())+(txOutCount/addrOutBalance.size()))/2;
			System.out.println("Toatal average transaction per Address: "+average);
			System.out.println("*************************************************************");
			System.out.println("Averagee out transaction value : "+toatalOutTransaction/txOutCount);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void largestInputTransactionHash() {
		try {
			FileReader inputTx=new FileReader("\\resource\\tx.dat");
			BufferedReader bufTx=new BufferedReader(inputTx);
			String lineJustFetched=null;
			String arr[];
			HashSet<Long> largeInTx=new HashSet();
			long maxInput=0l;
			
			while(true) {
				lineJustFetched=bufTx.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=maxTransactionId)
						break;
					long nIn=Long.parseLong(arr[2]);
					if(nIn>maxInput)
						maxInput=nIn;
				}
			}
			bufTx.close();
			
			FileReader inputTx2=new FileReader("\\resource\\tx.dat");
			BufferedReader bufTx2=new BufferedReader(inputTx2);
			
			while(true) {
				lineJustFetched=bufTx2.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=maxTransactionId)
						break;
					long nIn=Long.parseLong(arr[2]);
					if(nIn==maxInput)
						largeInTx.add(txId);
				}
			}
			bufTx2.close();
			
			FileReader inputHash=new FileReader("\\resource\\txh.dat");
			BufferedReader bufHash=new BufferedReader(inputHash);
			ArrayList<String> listHash=new ArrayList();
			
			while(true) {
				lineJustFetched=bufHash.readLine();
				if(lineJustFetched==null)
					break;
				else {
					arr=lineJustFetched.split("\t");
					long txId=Long.parseLong(arr[0]);
					if(txId>=maxTransactionId)
						break;
					if(largeInTx.contains(txId))
						listHash.add(arr[1]);
				}
			}
			
			bufHash.close();
			long count=0l;
			System.out.print("The transaction id transactions with largest number of inputs");
			for(long e:largeInTx)
				System.out.println(e);
				
			System.out.println("The hash values of the transactions with largest number of inputs");
			for(String h:listHash) {
				count++;
				System.out.println("("+count+")  : "+h);
			}
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		trimming();
		balanceMapping();
		largestInputTransactionHash();
		
		
	}

}
